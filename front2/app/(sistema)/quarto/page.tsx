"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import axios from "axios";
import { Quarto } from "@/app/types/quarto";

export default function Quartos() {
  const [quartos, setQuartos] = useState<Quarto[]>([]);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const dados = await axios.get<Quarto[]>("http://localhost:8080/quarto");
      setQuartos(dados.data);
    } catch (error) {
      alert("Erro ao carregar lista de quartos!");
    }
  };

  const handleDeletarQuarto = async (quarto: Quarto) => {
    if (!confirm(`Deseja realmente excluir o quarto Nº ${quarto.numero}?`)) return;

    try {
      const dadosRetorno = await axios.delete(
        `http://localhost:8080/quarto/${quarto.id}`
      );

      if (dadosRetorno.status === 200 || dadosRetorno.status === 204) {
        alert("Excluído com sucesso!");
      } else {
        alert(dadosRetorno.data);
        return;
      }

      carregarDados();
    } catch (error) {
      alert("Erro ao excluir quarto!");
    }
  };

  return (
    <div className="bg-slate-950 text-slate-100 p-6 md:p-10 space-y-8 font-sans">
      {/* Cabeçalho */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-6 border-b border-slate-800">
        <div>
          <h1 className="text-2xl sm:text-3xl font-bold tracking-tight text-slate-50">
            Gestão de <span className="text-teal-400">Quartos</span>
          </h1>
          <p className="text-slate-400 text-sm mt-1">
            Gerencie os quartos disponíveis, capacidade e valor da diária.
          </p>
        </div>

        <Link
          href="/quarto/novo"
          className="inline-flex items-center justify-center gap-2 px-5 py-2.5 rounded-xl bg-gradient-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/10 active:scale-95 text-sm"
        >
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4" />
          </svg>
          Novo Quarto
        </Link>
      </div>

      {/* Tabela de Quartos */}
      <div className="bg-slate-900/80 border border-slate-800/80 rounded-2xl shadow-xl overflow-hidden backdrop-blur-md">
        <div className="p-4 border-b border-slate-800 flex justify-between items-center bg-slate-900/40">
          <span className="text-xs text-slate-400">
            Total: <strong className="text-slate-200">{quartos.length}</strong> quartos cadastrados
          </span>
        </div>

        <div className="overflow-x-auto w-full">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-slate-950/60 border-b border-slate-800 text-xs uppercase tracking-wider text-slate-400">
                <th className="py-3.5 px-6 font-semibold">Código</th>
                <th className="py-3.5 px-6 font-semibold">Número</th>
                <th className="py-3.5 px-6 font-semibold">Tipo</th>
                <th className="py-3.5 px-6 font-semibold">Capacidade</th>
                <th className="py-3.5 px-6 font-semibold">Diária</th>
                <th className="py-3.5 px-6 font-semibold">Status</th>
                <th className="py-3.5 px-6 font-semibold text-right">Ações</th>
              </tr>
            </thead>

            <tbody className="divide-y divide-slate-800/60 text-sm">
              {quartos.map((quarto) => (
                <tr key={quarto.id} className="hover:bg-slate-800/40 transition-colors">
                  <td className="py-4 px-6 font-mono text-xs text-teal-400">
                    #{quarto.id}
                  </td>
                  <td className="py-4 px-6 font-semibold text-slate-200">
                    Nº {quarto.numero}
                  </td>
                  <td className="py-4 px-6 text-slate-300">
                    {quarto.tipo}
                  </td>
                  <td className="py-4 px-6 text-slate-400">
                    {quarto.capacidade} {quarto.capacidade === 1 ? "pessoa" : "pessoas"}
                  </td>
                  <td className="py-4 px-6 font-mono text-teal-300">
                    R$ {Number(quarto.diaria || 0).toFixed(2)}
                  </td>
                  <td className="py-4 px-6">
                    <span
                      className={`inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-medium ${
                        quarto.status === "DISPONIVEL" || quarto.status === "DISPONÍVEL"
                          ? "bg-emerald-950/60 text-emerald-400 border border-emerald-500/30"
                          : "bg-rose-950/60 text-rose-400 border border-rose-500/30"
                      }`}
                    >
                      <span
                        className={`w-1.5 h-1.5 rounded-full ${
                          quarto.status === "DISPONIVEL" || quarto.status === "DISPONÍVEL"
                            ? "bg-emerald-400"
                            : "bg-rose-400"
                        }`}
                      />
                      {quarto.status}
                    </span>
                  </td>
                  <td className="py-4 px-6 text-right">
                    <div className="inline-flex items-center gap-3">
                      <Link
                        href={`/quarto/${quarto.id}/editar`}
                        className="text-xs font-medium text-slate-300 hover:text-teal-400 transition-colors"
                      >
                        Editar
                      </Link>
                      <button
                        onClick={() => handleDeletarQuarto(quarto)}
                        className="text-xs font-medium text-rose-500 hover:text-rose-400 transition-colors"
                      >
                        Deletar
                      </button>
                    </div>
                  </td>
                </tr>
              ))}

              {quartos.length === 0 && (
                <tr>
                  <td colSpan={7} className="py-12 text-center text-slate-500 italic">
                    Nenhum quarto encontrado!
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