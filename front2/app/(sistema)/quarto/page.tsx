"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import Link from "next/link";
import { Quarto } from "@/app/types/quarto";

export default function QuartosPage() {
  const [quartos, setQuartos] = useState<Quarto[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const resposta = await axios.get<Quarto[]>("http://localhost:8080/quarto");
      setQuartos(resposta.data);
    } catch (error) {
      alert("Erro ao carregar a lista de quartos!");
    } finally {
      setLoading(false);
    }
  };

  // Função auxiliar para definir a cor do status da acomodação
  const getStatusBadge = (status?: string) => {
    const statusLower = status?.toLowerCase();
    switch (statusLower) {
      case "disponivel":
      case "disponível":
        return "bg-emerald-500/10 text-emerald-400 border-emerald-500/20";
      case "ocupado":
        return "bg-rose-500/10 text-rose-400 border-rose-500/20";
      case "manutencao":
      case "manutenção":
        return "bg-amber-500/10 text-amber-400 border-amber-500/20";
      default:
        return "bg-slate-800 text-slate-400 border-slate-700";
    }
  };

  return (
    <div className="space-y-6">
      {/* Cabeçalho da Página */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h2 className="text-2xl md:text-3xl font-bold text-slate-50 tracking-tight">
            Acomodações e <span className="text-teal-400">Quartos</span>
          </h2>
          <p className="text-slate-400 text-sm mt-1">
            Controle o status de limpeza, ocupação e valores das acomodações.
          </p>
        </div>

        <Link
          href="/quarto/novo"
          className="inline-flex items-center justify-center gap-2 px-4 py-2.5 rounded-xl bg-linear-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/10 active:scale-95 text-sm"
        >
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4" />
          </svg>
          Adicionar Quarto
        </Link>
      </div>

      {/* Exibição enquanto carrega */}
      {loading && (
        <div className="text-center py-12 text-slate-400">
          Carregando acomodações...
        </div>
      )}

      {/* Caso não existam quartos cadastrados */}
      {!loading && quartos.length === 0 && (
        <div className="text-center py-12 text-slate-500 italic border border-slate-800/80 rounded-2xl bg-slate-900/50">
          Nenhum quarto encontrado!
        </div>
      )}

      {/* Grid de Cards de Quartos */}
      {!loading && quartos.length > 0 && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {quartos.map((quarto) => (
            <div
              key={quarto.id}
              className="bg-slate-900/50 border border-slate-800/80 rounded-2xl p-5 hover:border-slate-700/80 transition-all flex flex-col justify-between"
            >
              <div>
                <div className="flex items-center justify-between mb-3">
                  <span
                    className={`text-xs font-semibold px-2.5 py-1 rounded-lg border ${getStatusBadge(
                      quarto.status
                    )}`}
                  >
                    {quarto.status || "Indefinido"}
                  </span>
                  <span className="text-sm font-bold text-slate-200">
                    R$ {quarto.precoDiaria ? quarto.precoDiaria.toFixed(2) : "0,00"}/noite
                  </span>
                </div>
                
                <h3 className="text-lg font-bold text-slate-100">
                  {quarto.tipo} - {quarto.numero}
                </h3>
                
                <p className="text-xs text-slate-400 mt-1">
                  Capacidade: {quarto.capacidade} pessoa(s)
                </p>
              </div>

              <div className="mt-5 pt-4 border-t border-slate-800/80 flex justify-end gap-2">
                <button className="px-3 py-1.5 rounded-lg bg-slate-800 text-xs font-medium text-slate-300 hover:bg-slate-700 transition-colors">
                  Detalhes
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}