"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import axios from "axios";
import { Reserva } from "@/app/types/reserva";

export default function Reservas() {
  const [reservas, setReservas] = useState<Reserva[]>([]);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const dados = await axios.get<Reserva[]>("http://localhost:8080/reserva");
      setReservas(dados.data);
    } catch (error) {
      alert("Erro ao carregar lista de reservas!");
    }
  };

  const handleCheckout = async (reserva: Reserva) => {
    if (!confirm(`Deseja realizar o checkout da reserva #${reserva.id}?`)) return;

    try {
      const dadosRetorno = await axios.patch(
        `http://localhost:8080/reserva/${reserva.id}/checkout`
      );

      if (dadosRetorno.status === 200) {
        alert("Checkout realizado com sucesso!");
        carregarDados();
      } else {
        alert("Não foi possível realizar o checkout.");
      }
    } catch (error) {
      alert("Erro ao realizar checkout!");
    }
  };

  const handleDeletarReserva = async (reserva: Reserva) => {
    if (!confirm(`Deseja realmente excluir a reserva #${reserva.id}?`)) return;

    try {
      const dadosRetorno = await axios.delete(
        `http://localhost:8080/reserva/${reserva.id}`
      );

      if (dadosRetorno.status === 200 || dadosRetorno.status === 204) {
        alert("Reserva excluída com sucesso!");
        carregarDados();
      } else {
        alert(dadosRetorno.data);
      }
    } catch (error) {
      alert("Erro ao excluir reserva!");
    }
  };

  return (
    <div className="bg-slate-950 text-slate-100 p-6 md:p-10 space-y-8 font-sans min-h-screen">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-6 border-b border-slate-800">
        <div>
          <h1 className="text-2xl sm:text-3xl font-bold tracking-tight text-slate-50">
            Gestão de <span className="text-teal-400">Reservas</span>
          </h1>
          <p className="text-slate-400 text-sm mt-1">
            Acompanhe o check-in, check-out e status das acomodações.
          </p>
        </div>

        <Link
          href="/reserva/novo"
          className="inline-flex items-center justify-center gap-2 px-5 py-2.5 rounded-xl bg-gradient-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/10 active:scale-95 text-sm"
        >
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4" />
          </svg>
          Nova Reserva
        </Link>
      </div>

      <div className="bg-slate-900/80 border border-slate-800/80 rounded-2xl shadow-xl overflow-hidden backdrop-blur-md">
        <div className="p-4 border-b border-slate-800 flex justify-between items-center bg-slate-900/40">
          <span className="text-xs text-slate-400">
            Total: <strong className="text-slate-200">{reservas.length}</strong> reservas ativas
          </span>
        </div>

        <div className="overflow-x-auto w-full">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-slate-950/60 border-b border-slate-800 text-xs uppercase tracking-wider text-slate-400">
                <th className="py-3.5 px-6 font-semibold">Código</th>
                <th className="py-3.5 px-6 font-semibold">Hóspede</th>
                <th className="py-3.5 px-6 font-semibold">Quarto</th>
                <th className="py-3.5 px-6 font-semibold">Entrada</th>
                <th className="py-3.5 px-6 font-semibold">Saída</th>
                <th className="py-3.5 px-6 font-semibold">Valor Total</th>
                <th className="py-3.5 px-6 font-semibold">Status</th>
                <th className="py-3.5 px-6 font-semibold text-right">Ações</th>
              </tr>
            </thead>

            <tbody className="divide-y divide-slate-800/60 text-sm">
              {reservas.map((r) => (
                <tr key={r.id} className="hover:bg-slate-800/40 transition-colors">
                  <td className="py-4 px-6 font-mono text-xs text-teal-400">
                    #{r.id}
                  </td>
                  <td className="py-4 px-6 font-medium text-slate-200">
                    {r.hospede?.nome || "Não informado"}
                  </td>
                  <td className="py-4 px-6 text-slate-300">
                    Quarto Nº {r.quarto?.numero || "N/A"}
                  </td>
                  <td className="py-4 px-6 font-mono text-xs text-slate-400">
                    {r.dataEntrada}
                  </td>
                  <td className="py-4 px-6 font-mono text-xs text-slate-400">
                    {r.dataSaida}
                  </td>
                  <td className="py-4 px-6 font-mono text-xs text-emerald-400">
                    {r.valorTotal != null ? `R$ ${Number(r.valorTotal).toFixed(2)}` : "-"}
                  </td>
                  <td className="py-4 px-6">
                    <span
                      className={`inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-medium ${
                        r.status === "CONFIRMADA"
                          ? "bg-emerald-950/60 text-emerald-400 border border-emerald-500/30"
                          : r.status === "PENDENTE"
                          ? "bg-amber-950/60 text-amber-400 border border-amber-500/30"
                          : r.status === "FINALIZADA"
                          ? "bg-sky-950/60 text-sky-400 border border-sky-500/30"
                          : "bg-rose-950/60 text-rose-400 border border-rose-500/30"
                      }`}
                    >
                      {r.status}
                    </span>
                  </td>
                  <td className="py-4 px-6 text-right">
                    <div className="inline-flex items-center gap-3">
                      {r.status !== "FINALIZADA" && r.status !== "CANCELADA" && (
                        <button
                          onClick={() => handleCheckout(r)}
                          className="text-xs font-medium text-emerald-400 hover:text-emerald-300 transition-colors"
                        >
                          Checkout
                        </button>
                      )}
                      <Link
                        href={`/reserva/${r.id}/editar`}
                        className="text-xs font-medium text-slate-300 hover:text-teal-400 transition-colors"
                      >
                        Editar
                      </Link>
                      <button
                        onClick={() => handleDeletarReserva(r)}
                        className="text-xs font-medium text-rose-500 hover:text-rose-400 transition-colors"
                      >
                        Deletar
                      </button>
                    </div>
                  </td>
                </tr>
              ))}

              {reservas.length === 0 && (
                <tr>
                  <td colSpan={8} className="py-12 text-center text-slate-500 italic">
                    Nenhuma reserva encontrada!
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}