
"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";

import { Reserva } from "@/app/types/reserva";
import { AppDispatch, RootState } from "@/app/redux/store";

import {
  setReservas,
  removerReserva,
  atualizarReserva as atualizarReservaRedux
} from "@/app/redux/slices/reservaSlice";

import {
  buscarListaReservas,
  checkoutReserva,
  deletarReserva
} from "@/app/services/reservaService";

type ReservaDaApi = Reserva & {
  hospedeId?: number | null;
  quartoId?: number | null;
};

function mensagemErro(error: unknown): string {
  if (axios.isAxiosError(error)) {
    if (error.response?.status === 401) {
      return "Sessão expirada. Faça login novamente.";
    }

    const dados = error.response?.data;

    if (typeof dados === "string") {
      return dados;
    }

    if (typeof dados?.message === "string") {
      return dados.message;
    }

    if (typeof dados?.detail === "string") {
      return dados.detail;
    }
  }

  return "Não foi possível concluir a operação.";
}

export default function Reservas() {
  const dispatch = useDispatch<AppDispatch>();

  const reservas = useSelector(
    (state: RootState) => state.reserva.reservas
  );

  const [carregando, setCarregando] = useState(true);
  const [processando, setProcessando] = useState<number | null>(null);
  const [erro, setErro] = useState("");

  // Atualização manual da lista
  const carregarDados = async () => {
    setCarregando(true);
    setErro("");

    try {
      const lista = await buscarListaReservas();
      dispatch(setReservas(lista));
    } catch (error) {
      dispatch(setReservas([]));
      setErro(mensagemErro(error));
    } finally {
      setCarregando(false);
    }
  };

  // Carregamento inicial
  useEffect(() => {
    let ativo = true;

    async function carregarReservasIniciais() {
      try {
        const lista = await buscarListaReservas();

        if (ativo) {
          dispatch(setReservas(lista));
          setErro("");
        }
      } catch (error) {
        if (ativo) {
          dispatch(setReservas([]));
          setErro(mensagemErro(error));
        }
      } finally {
        if (ativo) {
          setCarregando(false);
        }
      }
    }

    void carregarReservasIniciais();

    return () => {
      ativo = false;
    };
  }, [dispatch]);

  // Realizar checkout
  const handleCheckout = async (reserva: Reserva) => {
    if (reserva.id === null) return;

    const confirmado = window.confirm(
      `Deseja realizar o checkout da reserva #${reserva.id}?`
    );

    if (!confirmado) return;

    setProcessando(reserva.id);

    try {
      const status = await checkoutReserva(reserva.id);

      if (status === 200) {
        dispatch(
          atualizarReservaRedux({
            ...reserva,
            status: "FINALIZADA"
          })
        );

        alert("Checkout realizado com sucesso!");
        await carregarDados();
      }
    } catch (error) {
      alert(mensagemErro(error));
    } finally {
      setProcessando(null);
    }
  };

  // Excluir reserva
  const handleDeletarReserva = async (reserva: Reserva) => {
    if (reserva.id === null) return;

    const confirmado = window.confirm(
      `Deseja realmente excluir a reserva #${reserva.id}?`
    );

    if (!confirmado) return;

    setProcessando(reserva.id);

    try {
      const status = await deletarReserva(reserva.id);

      if (status === 200 || status === 204) {
        dispatch(removerReserva(reserva.id));
        alert("Reserva excluída com sucesso!");
      }
    } catch (error) {
      alert(mensagemErro(error));
    } finally {
      setProcessando(null);
    }
  };

  // Exibição do hóspede
  const mostrarHospede = (reserva: ReservaDaApi) => {
    if (reserva.hospede?.nome) {
      return reserva.hospede.nome;
    }

    if (reserva.hospedeId != null) {
      return `Hóspede #${reserva.hospedeId}`;
    }

    return "Não informado";
  };

  // Exibição do quarto
  const mostrarQuarto = (reserva: ReservaDaApi) => {
    if (reserva.quarto?.numero != null) {
      return `Quarto Nº ${reserva.quarto.numero}`;
    }

    if (reserva.quartoId != null) {
      return `Quarto ID ${reserva.quartoId}`;
    }

    return "Não informado";
  };

  // Estilo visual do status
  const estiloStatus = (status: string) => {
    switch (status) {
      case "CONFIRMADA":
        return "bg-emerald-950/60 text-emerald-400 border-emerald-500/30";

      case "PENDENTE":
        return "bg-amber-950/60 text-amber-400 border-amber-500/30";

      case "FINALIZADA":
        return "bg-sky-950/60 text-sky-400 border-sky-500/30";

      default:
        return "bg-rose-950/60 text-rose-400 border-rose-500/30";
    }
  };

  return (
    <div className="bg-slate-950 text-slate-100 p-6 md:p-10 space-y-8 font-sans min-h-screen">

      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-6 border-b border-slate-800">
        <div>
          <h1 className="text-2xl sm:text-3xl font-bold tracking-tight text-slate-50">
            Gestão de{" "}
            <span className="text-teal-400">
              Reservas
            </span>
          </h1>

          <p className="text-slate-400 text-sm mt-1">
            Gerencie as reservas e os checkouts da pousada.
          </p>
        </div>

        <div className="flex gap-3">
          <button
            type="button"
            onClick={carregarDados}
            disabled={carregando}
            className="px-5 py-2.5 rounded-xl bg-slate-800 text-slate-200 text-sm font-semibold hover:bg-slate-700 disabled:opacity-50"
          >
            Atualizar lista
          </button>

          <Link
            href="/reserva/novo"
            className="inline-flex items-center justify-center px-5 py-2.5 rounded-xl bg-linear-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold text-sm"
          >
            Nova Reserva
          </Link>
        </div>
      </div>

      {erro && (
        <div className="p-4 border border-red-500/30 bg-red-950/30 rounded-xl text-red-400">
          {erro}
        </div>
      )}

      <div className="bg-slate-900/80 border border-slate-800/80 rounded-2xl shadow-xl overflow-hidden">

        <div className="p-4 border-b border-slate-800 flex justify-between items-center">
          <span className="text-xs text-slate-400">
            Total:{" "}
            <strong className="text-slate-200">
              {reservas.length}
            </strong>{" "}
            reservas
          </span>

          <span className="text-xs text-teal-400">
            Gerenciado pelo Redux Toolkit
          </span>
        </div>

        <div className="overflow-x-auto w-full">
          <table className="w-full text-left border-collapse">

            <thead>
              <tr className="bg-slate-950/60 border-b border-slate-800 text-xs uppercase tracking-wider text-slate-400">
                <th className="py-3.5 px-6">Código</th>
                <th className="py-3.5 px-6">Hóspede</th>
                <th className="py-3.5 px-6">Quarto</th>
                <th className="py-3.5 px-6">Entrada</th>
                <th className="py-3.5 px-6">Saída</th>
                <th className="py-3.5 px-6">Valor Total</th>
                <th className="py-3.5 px-6">Status</th>
                <th className="py-3.5 px-6 text-right">Ações</th>
              </tr>
            </thead>

            <tbody className="divide-y divide-slate-800/60 text-sm">

              {carregando ? (
                <tr>
                  <td
                    colSpan={8}
                    className="py-12 text-center text-slate-400"
                  >
                    Carregando reservas...
                  </td>
                </tr>
              ) : (
                <>
                  {reservas.map((reserva) => {
                    const dados = reserva as ReservaDaApi;

                    return (
                      <tr
                        key={reserva.id}
                        className="hover:bg-slate-800/40 transition-colors"
                      >
                        <td className="py-4 px-6 font-mono text-xs text-teal-400">
                          #{reserva.id}
                        </td>

                        <td className="py-4 px-6 font-medium text-slate-200">
                          {mostrarHospede(dados)}
                        </td>

                        <td className="py-4 px-6 text-slate-300">
                          {mostrarQuarto(dados)}
                        </td>

                        <td className="py-4 px-6 font-mono text-xs text-slate-400">
                          {reserva.dataEntrada}
                        </td>

                        <td className="py-4 px-6 font-mono text-xs text-slate-400">
                          {reserva.dataSaida}
                        </td>

                        <td className="py-4 px-6 font-mono text-xs text-slate-400">
                          {reserva.valorTotal != null
                            ? `R$ ${Number(reserva.valorTotal).toFixed(2)}`
                            : "-"}
                        </td>

                        <td className="py-4 px-6">
                          <span
                            className={`inline-flex px-2.5 py-1 rounded-full text-xs font-medium border ${estiloStatus(reserva.status)}`}
                          >
                            {reserva.status}
                          </span>
                        </td>

                        <td className="py-4 px-6 text-right">
                          <div className="inline-flex items-center gap-3">

                            {reserva.status !== "FINALIZADA" &&
                              reserva.status !== "CANCELADA" && (
                                <button
                                  type="button"
                                  disabled={processando !== null}
                                  onClick={() => handleCheckout(reserva)}
                                  className="text-xs font-medium text-emerald-400 hover:text-emerald-300 disabled:opacity-50"
                                >
                                  Checkout
                                </button>
                              )}

                            <Link
                              href={`/reserva/${reserva.id}/editar`}
                              className="text-xs font-medium text-slate-300 hover:text-teal-400"
                            >
                              Editar
                            </Link>

                            <button
                              type="button"
                              disabled={processando !== null}
                              onClick={() => handleDeletarReserva(reserva)}
                              className="text-xs font-medium text-rose-500 hover:text-rose-400 disabled:opacity-50"
                            >
                              Excluir
                            </button>

                          </div>
                        </td>
                      </tr>
                    );
                  })}

                  {reservas.length === 0 && (
                    <tr>
                      <td
                        colSpan={8}
                        className="py-12 text-center text-slate-500 italic"
                      >
                        Nenhuma reserva encontrada!
                      </td>
                    </tr>
                  )}
                </>
              )}

            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
