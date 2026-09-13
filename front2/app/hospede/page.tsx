"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import Link from "next/link";
import { Hospede } from "@/app/types/hospede";

export default function HospedesPage() {
  const [hospedes, setHospedes] = useState<Hospede[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const resposta = await axios.get<Hospede[]>("http://localhost:8080/hospedes");
      setHospedes(resposta.data);
    } catch (error) {
      alert("Erro ao carregar a lista de hóspedes!");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-6">
      {/* Cabeçalho da Página */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h2 className="text-2xl md:text-3xl font-bold text-slate-50 tracking-tight">
            Lista de <span className="text-teal-400">Hóspedes</span>
          </h2>
          <p className="text-slate-400 text-sm mt-1">
            Cadastre e gerencie o histórico dos clientes da sua pousada.
          </p>
        </div>

        <Link
          href="/hospede/novo"
          className="inline-flex items-center justify-center gap-2 px-4 py-2.5 rounded-xl bg-linear-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/10 active:scale-95 text-sm"
        >
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4" />
          </svg>
          Novo Hóspede
        </Link>
      </div>

      {/* Tabela / Card Container */}
      <div className="bg-slate-900/50 border border-slate-800/80 rounded-2xl p-6 backdrop-blur-sm">
        <div className="flex items-center justify-between pb-4 border-b border-slate-800/80">
          <input
            type="text"
            placeholder="Buscar por nome ou CPF..."
            className="bg-slate-950 border border-slate-800 rounded-xl px-4 py-2 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 transition-colors w-full md:w-80"
          />
          <span className="text-xs text-slate-400">
            Total: <strong className="text-slate-200">{hospedes.length}</strong>
          </span>
        </div>

        <div className="overflow-x-auto mt-4">
          <table className="w-full text-left text-sm text-slate-300">
            <thead className="text-xs uppercase bg-slate-950/60 text-slate-400 border-b border-slate-800">
              <tr>
                <th className="px-4 py-3">Código</th>
                <th className="px-4 py-3">Nome</th>
                <th className="px-4 py-3">CPF / Documento</th>
                <th className="px-4 py-3">Telefone</th>
                <th className="px-4 py-3 text-right">Ações</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-800/60">
              {hospedes.map((hospede) => (
                <tr key={hospede.id} className="hover:bg-slate-800/30 transition-colors">
                  <td className="px-4 py-3.5 font-mono text-xs text-teal-400">
                    #{hospede.id}
                  </td>
                  <td className="px-4 py-3.5 font-medium text-slate-100">
                    {hospede.nome}
                  </td>
                  <td className="px-4 py-3.5 text-slate-400 font-mono text-xs">
                    {hospede.cpf}
                  </td>
                  <td className="px-4 py-3.5 text-slate-400 font-mono text-xs">
                    {hospede.telefone}
                  </td>
                  <td className="px-4 py-3.5 text-right">
                    <button className="text-teal-400 hover:text-teal-300 text-xs font-semibold mr-3">
                      Editar
                    </button>
                    <button className="text-rose-400 hover:text-rose-300 text-xs font-semibold">
                      Excluir
                    </button>
                  </td>
                </tr>
              ))}

              {hospedes.length === 0 && !loading && (
                <tr>
                  <td colSpan={5} className="py-8 text-center text-slate-500 italic">
                    Nenhum hóspede encontrado!
                  </td>
                </tr>
              )}

              {loading && (
                <tr>
                  <td colSpan={5} className="py-8 text-center text-slate-400">
                    Carregando hóspedes...
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