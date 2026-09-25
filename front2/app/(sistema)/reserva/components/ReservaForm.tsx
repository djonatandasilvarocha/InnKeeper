"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import axios from "axios";
import { Reserva, ReservaFormProps, EnumStatusReserva } from "@/app/types/reserva";
import { Hospede } from "@/app/types/hospede";
import { Quarto } from "@/app/types/quarto";

export default function ReservaForm({ reservaExistente }: ReservaFormProps) {
  const router = useRouter();

  const [hospedes, setHospedes] = useState<Hospede[]>([]);
  const [quartos, setQuartos] = useState<Quarto[]>([]);

  const [reserva, setReserva] = useState<Reserva>(
    reservaExistente ||
      new Reserva(null, null, null, "", "", null, "CONFIRMADA")
  );

  useEffect(() => {
    carregarOpcoes();
  }, []);

  const carregarOpcoes = async () => {
    try {
      const [resHospedes, resQuartos] = await Promise.all([
        axios.get<Hospede[]>("http://localhost:8080/hospede"),
        axios.get<Quarto[]>("http://localhost:8080/quarto"),
      ]);

      setHospedes(resHospedes.data);
      setQuartos(resQuartos.data);
    } catch (error) {
      alert("Erro ao carregar lista de hóspedes e quartos!");
    }
  };

  const handlerSalvar = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (reservaExistente) {
        const dadosRetorno = await axios.put(
          `http://localhost:8080/reserva/${reserva.id}`,
          reserva
        );

        if (dadosRetorno.status === 200) {
          alert("Reserva atualizada com sucesso!");
        } else {
          alert(dadosRetorno.data);
          return;
        }
      } else {
        const dadosRetorno = await axios.post(
          "http://localhost:8080/reserva",
          reserva
        );

        if (dadosRetorno.status === 200 || dadosRetorno.status === 201) {
          alert("Reserva realizada com sucesso!");
        } else {
          alert(dadosRetorno.data);
          return;
        }
      }

      router.push("/reserva");
    } catch (error) {
      alert("Erro ao salvar reserva!");
    }
  };

  return (
    <form
      onSubmit={handlerSalvar}
      className="w-full max-w-2xl mx-auto bg-[#0b1120] border border-slate-800 rounded-xl p-8 shadow-2xl space-y-6"
    >
      <div>
        <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
          Hóspede:
        </label>
        <select
          value={reserva.hospede?.id || ""}
          required
          onChange={(e) => {
            const h = hospedes.find((item) => item.id === Number(e.target.value));
            setReserva({ ...reserva, hospede: h || null });
          }}
          className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
        >
          <option value="">Selecione um Hóspede</option>
          {hospedes.map((h) => (
            <option key={h.id} value={h.id || ""}>
              {h.nome} - CPF: {h.cpf}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
          Quarto:
        </label>
        <select
          value={reserva.quarto?.id || ""}
          required
          onChange={(e) => {
            const q = quartos.find((item) => item.id === Number(e.target.value));
            setReserva({ ...reserva, quarto: q || null });
          }}
          className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
        >
          <option value="">Selecione um Quarto</option>
          {quartos.map((q) => (
            <option key={q.id} value={q.id || ""}>
              Nº {q.numero} - {q.tipo} (R$ {Number(q.diaria).toFixed(2)}/dia)
            </option>
          ))}
        </select>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <div>
          <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
            Data de Check-in:
          </label>
          <input
            type="date"
            value={reserva.dataEntrada}
            required
            onChange={(e) =>
              setReserva({ ...reserva, dataEntrada: e.target.value })
            }
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
            Data de Check-out:
          </label>
          <input
            type="date"
            value={reserva.dataSaida}
            required
            onChange={(e) =>
              setReserva({ ...reserva, dataSaida: e.target.value })
            }
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          />
        </div>
      </div>

      <div>
        <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
          Status da Reserva:
        </label>
        <select
          value={reserva.status}
          onChange={(e) =>
            setReserva({ ...reserva, status: e.target.value as EnumStatusReserva })
          }
          className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
        >
          <option value="CONFIRMADA">CONFIRMADA</option>
          <option value="PENDENTE">PENDENTE</option>
          <option value="FINALIZADA">FINALIZADA</option>
          <option value="CANCELADA">CANCELADA</option>
        </select>
      </div>

      <div className="flex items-center justify-end gap-4 pt-4 border-t border-slate-800/80">
        <Link
          href="/reserva"
          className="px-5 py-2.5 text-sm font-medium text-slate-400 hover:text-slate-200 hover:bg-slate-800/60 rounded-lg transition-all duration-200"
        >
          Cancelar
        </Link>
        <button
          type="submit"
          className="px-6 py-2.5 text-sm font-semibold text-slate-950 bg-teal-400 hover:bg-teal-300 rounded-lg shadow-md hover:shadow-teal-500/10 active:scale-[0.98] transition-all duration-200"
        >
          Salvar
        </button>
      </div>
    </form>
  );
}