
"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

import {
  Reserva,
  ReservaFormProps,
  EnumStatusReserva
} from "@/app/types/reserva";

import { Hospede } from "@/app/types/hospede";
import { Quarto } from "@/app/types/quarto";

import { buscarListaHospedes } from "@/app/services/hospedeService";
import { buscarListaQuartos } from "@/app/services/quartoService";

import {
  salvarReserva,
  atualizarReserva
} from "@/app/services/reservaService";

type ReservaComIds = Reserva & {
  hospedeId?: number | null;
  quartoId?: number | null;
};

export default function ReservaForm({
  reservaExistente
}: ReservaFormProps) {

  const router = useRouter();

  const [hospedes, setHospedes] = useState<Hospede[]>([]);
  const [quartos, setQuartos] = useState<Quarto[]>([]);

  const [carregando, setCarregando] = useState(true);
  const [erroOpcoes, setErroOpcoes] = useState("");

  const dadosExistentes = reservaExistente as
    | ReservaComIds
    | undefined;

  const [reserva, setReserva] = useState<Reserva>(
    reservaExistente ||
      new Reserva(
        null,
        null,
        null,
        "",
        "",
        null,
        "CONFIRMADA"
      )
  );

  // IDs iniciais, inclusive quando a API retorna IDs separados
  const [hospedeId, setHospedeId] = useState<number | null>(
    dadosExistentes?.hospede?.id ??
    dadosExistentes?.hospedeId ??
    null
  );

  const [quartoId, setQuartoId] = useState<number | null>(
    dadosExistentes?.quarto?.id ??
    dadosExistentes?.quartoId ??
    null
  );

  // Carregar hóspedes e quartos para os selects
  useEffect(() => {
    let ativo = true;

    async function carregarOpcoes() {
      try {
        const [listaHospedes, listaQuartos] =
          await Promise.all([
            buscarListaHospedes(),
            buscarListaQuartos()
          ]);

        if (ativo) {
          setHospedes(listaHospedes);
          setQuartos(listaQuartos);
          setErroOpcoes("");
        }

      } catch {
        if (ativo) {
          setErroOpcoes(
            "Erro ao carregar a lista de hóspedes e quartos!"
          );
        }

      } finally {
        if (ativo) {
          setCarregando(false);
        }
      }
    }

    void carregarOpcoes();

    return () => {
      ativo = false;
    };

  }, []);

  // Atualizar hóspede selecionado
  const selecionarHospede = (valor: string) => {
    const id = Number(valor);

    const hospedeSelecionado = hospedes.find(
      (item) => item.id === id
    );

    setHospedeId(hospedeSelecionado?.id ?? null);

    setReserva((anterior) => ({
      ...anterior,
      hospede: hospedeSelecionado || null
    }));
  };

  // Atualizar quarto selecionado
  const selecionarQuarto = (valor: string) => {
    const id = Number(valor);

    const quartoSelecionado = quartos.find(
      (item) => item.id === id
    );

    setQuartoId(quartoSelecionado?.id ?? null);

    setReserva((anterior) => ({
      ...anterior,
      quarto: quartoSelecionado || null
    }));
  };

  // Cadastrar ou editar reserva
  const handlerSalvar = async (
    e: React.FormEvent<HTMLFormElement>
  ) => {
    e.preventDefault();

    const hospedeSelecionado = hospedes.find(
      (item) => item.id === hospedeId
    );

    const quartoSelecionado = quartos.find(
      (item) => item.id === quartoId
    );

    if (!hospedeSelecionado || !quartoSelecionado) {
      alert("Selecione um hóspede e um quarto válidos.");
      return;
    }

    if (!reserva.dataEntrada || !reserva.dataSaida) {
      alert("Informe as datas da reserva.");
      return;
    }

    if (reserva.dataSaida <= reserva.dataEntrada) {
      alert(
        "A data de check-out deve ser posterior à data de check-in."
      );
      return;
    }

    const reservaParaSalvar: Reserva = {
      ...reserva,
      hospede: hospedeSelecionado,
      quarto: quartoSelecionado
    };

    try {
      if (reservaExistente) {
        await atualizarReserva(reservaParaSalvar);

        alert("Reserva atualizada com sucesso!");

      } else {
        await salvarReserva(reservaParaSalvar);

        alert("Reserva realizada com sucesso!");
      }

      router.push("/reserva");
      router.refresh();

    } catch {
      alert(
        "Erro ao salvar reserva! Verifique os dados e a disponibilidade do quarto."
      );
    }
  };

  const estiloInput =
    "w-full bg-[#030712] border border-slate-800 " +
    "rounded-lg px-4 py-3 text-slate-100 " +
    "focus:outline-none focus:border-teal-500 " +
    "focus:ring-1 focus:ring-teal-500 " +
    "transition-all duration-200";

  const estiloLabel =
    "block text-xs font-semibold text-slate-300 " +
    "uppercase tracking-wider mb-2";

  return (
    <form
      onSubmit={handlerSalvar}
      className="w-full max-w-2xl mx-auto bg-[#0b1120] border border-slate-800 rounded-xl p-8 shadow-2xl space-y-6"
    >

      {/* HÓSPEDE */}

      <div>
        <label className={estiloLabel}>
          Hóspede:
        </label>

        <select
          value={hospedeId ?? ""}
          required
          disabled={carregando}
          onChange={(e) =>
            selecionarHospede(e.target.value)
          }
          className={estiloInput}
        >
          <option value="">
            Selecione um Hóspede
          </option>

          {hospedes.map((h) => (
            <option
              key={h.id}
              value={h.id ?? ""}
            >
              {h.nome} - CPF: {h.cpf}
            </option>
          ))}
        </select>
      </div>

      {/* QUARTO */}

      <div>
        <label className={estiloLabel}>
          Quarto:
        </label>

        <select
          value={quartoId ?? ""}
          required
          disabled={carregando}
          onChange={(e) =>
            selecionarQuarto(e.target.value)
          }
          className={estiloInput}
        >
          <option value="">
            Selecione um Quarto
          </option>

          {quartos.map((q) => (
            <option
              key={q.id}
              value={q.id ?? ""}
            >
              Nº {q.numero} - {q.tipo} (R$ {Number(q.diaria).toFixed(2)}/dia)
            </option>
          ))}
        </select>
      </div>

      {/* DATAS */}

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">

        <div>
          <label className={estiloLabel}>
            Data de Check-in:
          </label>

          <input
            type="date"
            value={reserva.dataEntrada}
            required
            onChange={(e) =>
              setReserva((anterior) => ({
                ...anterior,
                dataEntrada: e.target.value
              }))
            }
            className={estiloInput}
          />
        </div>

        <div>
          <label className={estiloLabel}>
            Data de Check-out:
          </label>

          <input
            type="date"
            value={reserva.dataSaida}
            required
            onChange={(e) =>
              setReserva((anterior) => ({
                ...anterior,
                dataSaida: e.target.value
              }))
            }
            className={estiloInput}
          />
        </div>

      </div>

      {/* STATUS */}

      <div>
        <label className={estiloLabel}>
          Status da Reserva:
        </label>

        <select
          value={reserva.status}
          onChange={(e) =>
            setReserva((anterior) => ({
              ...anterior,
              status: e.target.value as EnumStatusReserva
            }))
          }
          className={estiloInput}
        >
          <option value="CONFIRMADA">
            CONFIRMADA
          </option>

          <option value="PENDENTE">
            PENDENTE
          </option>

          {reserva.status === "FINALIZADA" && (
            <option value="FINALIZADA">
              FINALIZADA
            </option>
          )}

          <option value="CANCELADA">
            CANCELADA
          </option>
        </select>

        <p className="mt-2 text-xs text-slate-500">
          Para finalizar uma reserva, utilize a opção
          Checkout na listagem de reservas.
        </p>
      </div>

      {/* ERROS E CARREGAMENTO */}

      {carregando && (
        <p className="text-sm text-slate-400">
          Carregando hóspedes e quartos...
        </p>
      )}

      {erroOpcoes && (
        <p className="text-sm text-red-400">
          {erroOpcoes}
        </p>
      )}

      {/* BOTÕES */}

      <div className="flex items-center justify-end gap-4 pt-4 border-t border-slate-800/80">

        <Link
          href="/reserva"
          className="px-5 py-2.5 text-sm font-medium text-slate-400 hover:text-slate-200 hover:bg-slate-800/60 rounded-lg transition-all duration-200"
        >
          Cancelar
        </Link>

        <button
          type="submit"
          disabled={carregando || Boolean(erroOpcoes)}
          className="px-6 py-2.5 text-sm font-semibold text-slate-950 bg-teal-400 hover:bg-teal-300 rounded-lg shadow-md hover:shadow-teal-500/10 active:scale-[0.98] transition-all duration-200 disabled:opacity-50"
        >
          Salvar
        </button>

      </div>

    </form>
  );
}
