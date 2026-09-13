"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import Link from "next/link";
import { Reserva } from "@/app/types/reserva";

export default function ReservasPage() {
  const [reservas, setReservas] = useState<Reserva[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const resposta = await axios.get<Reserva[]>("http://localhost:8080/reservas");
      setReservas(resposta.data);
    } catch (error) {
      alert("Erro ao carregar a lista de reservas!");
    } finally {
      setLoading(false);
    }
  };

  // Função para aplicar estilos visuais baseados no status da reserva
  const getStatusBadge = (status?: string) => {
    const statusLower = status?.toLowerCase();
    switch (statusLower) {
      case "confirmado":
      case "confirmada":
        return "bg-teal-500/10 text-teal-400 border-teal-500/20";
      case "pendente":
        return "bg-amber-500/10 text-amber-400 border-amber-500/20";
      case "cancelado":
      case "cancelada":
        return "bg-rose-500/10 text-rose-400 border-rose-500/20";
      case "finalizado":
      case "finalizada":
      case "check-out":
        return "bg-slate-800 text-slate-400 border-slate-700";
      default:
        return "bg-slate-800 text-slate-400 border-slate-700";
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h2 className="text-2xl md:text-3xl font-bold text-slate-50 tracking-tight">
            Gestão de <span className="text-teal-400">Reservas</span>
          </h2>
          <p className="text-slate-400 text-sm mt-1">
            Acompanhe check-ins, check-outs e estadias ativas.
          </p>
        </div>

        <Link
          href="/reserva/novo"
          className="inline-flex items-center justify-center gap-2 px-4 py-2.5 rounded-xl bg-linear-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/10 active:scale-95 text-sm"
        >
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4" />
          </svg>
          Nova Reserva
        </Link>
      </div>

      {/* Tabela de Reservas */}
      <div className="bg-slate-900/50 border border-slate-800/80 rounded-2xl p-6 backdrop-blur-sm">
        <div className="overflow-x-auto">
          <table className="w-full text-left text-sm text-slate-300">
            <thead className="text-xs uppercase bg-slate-950/60 text-slate-400 border-b border-slate-800">
              <tr>
                <th className="px-4 py-3">Código</th>
                <th className="px-4 py-3">Hóspede</th>
                <th className="px-4 py-3">Quarto</th>
                <th className="px-4 py-3">Check-in</th>
                <th className="px-4 py-3">Check-out</th>
                <th className="px-4 py-3">Status</th>
                <th className="px-4 py-3 text-right">Ações</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-800/60">
              {reservas.map((reserva) => (
                <tr key={reserva.id} className="hover:bg-slate-800/30 transition-colors">
                  <td className="px-4 py-3.5 font-mono text-xs text-teal-400">
                    #{reserva.id}
                  </td>
                  <td className="px-4 py-3.5 font-medium text-slate-100">
                    {reserva.hospedeNome}
                  </td>
                  <td className="px-4 py-3.5 text-slate-300">
                    {reserva.quartoNumero}
                  </td>
                  <td className="px-4 py-3.5 text-slate-400 font-mono text-xs">
                    {reserva.dataCheckIn}
                  </td>
                  <td className="px-4 py-3.5 text-slate-400 font-mono text-xs">
                    {reserva.dataCheckOut}
                  </td>
                  <td className="px-4 py-3.5">
                    <span
                      className={`text-xs font-semibold px-2.5 py-1 rounded-md border ${getStatusBadge(
                        reserva.status
                      )}`}
                    >
                      {reserva.status || "Pendente"}
                    </span>
                  </td>
                  <td className="px-4 py-3.5 text-right">
                    <button className="text-teal-400 hover:text-teal-300 text-xs font-semibold mr-3">
                      Ver Detalhes
                    </button>
                  </td>
                </tr>
              ))}

              {reservas.length === 0 && !loading && (
                <tr>
                  <td colSpan={7} className="py-8 text-center text-slate-500 italic">
                    Nenhuma reserva encontrada!
                  </td>
                </tr>
              )}

              {loading && (
                <tr>
                  <td colSpan={7} className="py-8 text-center text-slate-400">
                    Carregando reservas...
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