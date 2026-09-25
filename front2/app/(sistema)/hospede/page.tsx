"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import axios from "axios";
import { Hospede } from "@/app/types/hospede";

export default function Hospedes() {
  const [hospedes, setHospedes] = useState<Hospede[]>([]);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const dados = await axios.get<Hospede[]>("http://localhost:8080/hospede");
      setHospedes(dados.data);
    } catch (error) {
      alert("Erro ao carregar lista de hóspedes!");
    }
  };

  const handleDeletarHospede = async (hospede: Hospede) => {
    if (!confirm(`Deseja realmente excluir o hóspede ${hospede.nome}?`)) return;

    try {
      const dadosRetorno = await axios.delete(
        `http://localhost:8080/hospede/${hospede.id}`
      );

      if (dadosRetorno.status === 200) {
        alert("Excluído com sucesso!");
      } else {
        alert(dadosRetorno.data);
        return;
      }

      carregarDados();
    } catch (error) {
      alert("Erro ao excluir hóspede!");
    }
  };

  return (
    <div className="bg-slate-950 text-slate-100 p-6 md:p-10 space-y-8 font-sans">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-6 border-b border-slate-800">
        <div>
          <h1 className="text-2xl sm:text-3xl font-bold tracking-tight text-slate-50">
            Gestão de <span className="text-teal-400">Hóspedes</span>
          </h1>
          <p className="text-slate-400 text-sm mt-1">
            Gerencie os cadastros e dados de contato dos hóspedes.
          </p>
        </div>

        <Link
          href="/hospede/novo"
          className="inline-flex items-center justify-center gap-2 px-5 py-2.5 rounded-xl bg-gradient-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/10 active:scale-95 text-sm"
        >
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4" />
          </svg>
          Novo Hóspede
        </Link>
      </div>

      <div className="bg-slate-900/80 border border-slate-800/80 rounded-2xl shadow-xl overflow-hidden backdrop-blur-md">
        <div className="p-4 border-b border-slate-800 flex justify-between items-center bg-slate-900/40">
          <span className="text-xs text-slate-400">
            Total: <strong className="text-slate-200">{hospedes.length}</strong> hóspedes cadastrados
          </span>
        </div>

        <div className="overflow-x-auto w-full">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-slate-950/60 border-b border-slate-800 text-xs uppercase tracking-wider text-slate-400">
                <th className="py-3.5 px-6 font-semibold">Código</th>
                <th className="py-3.5 px-6 font-semibold">Nome</th>
                <th className="py-3.5 px-6 font-semibold">CPF</th>
                <th className="py-3.5 px-6 font-semibold">E-mail</th>
                <th className="py-3.5 px-6 font-semibold">Telefone</th>
                <th className="py-3.5 px-6 font-semibold text-right">Ações</th>
              </tr>
            </thead>

            <tbody className="divide-y divide-slate-800/60 text-sm">
              {hospedes.map((hospede) => (
                <tr key={hospede.id} className="hover:bg-slate-800/40 transition-colors">
                  <td className="py-4 px-6 font-mono text-xs text-teal-400">
                    #{hospede.id}
                  </td>
                  <td className="py-4 px-6 font-medium text-slate-200">
                    {hospede.nome}
                  </td>
                  <td className="py-4 px-6 text-slate-400 font-mono text-xs">
                    {hospede.cpf}
                  </td>
                  <td className="py-4 px-6 text-slate-400">
                    {hospede.email}
                  </td>
                  <td className="py-4 px-6 text-slate-400">
                    {hospede.telefone}
                  </td>
                  <td className="py-4 px-6 text-right">
                    <div className="inline-flex items-center gap-3">
                      <Link
                        href={`/hospede/${hospede.id}/editar`}
                        className="text-xs font-medium text-slate-300 hover:text-teal-400 transition-colors"
                      >
                        Editar
                      </Link>
                      <button
                        onClick={() => handleDeletarHospede(hospede)}
                        className="text-xs font-medium text-rose-500 hover:text-rose-400 transition-colors"
                      >
                        Deletar
                      </button>
                    </div>
                  </td>
                </tr>
              ))}

              {hospedes.length === 0 && (
                <tr>
                  <td colSpan={6} className="py-12 text-center text-slate-500 italic">
                    Nenhum hóspede encontrado!
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