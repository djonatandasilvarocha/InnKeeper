"use client";

import { Usuario } from "@/app/types/usuario";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Usuarios() {
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const dados = await axios.get<Usuario[]>("http://localhost:8080/usuarios");
      setUsuarios(dados.data);
    } catch (error) {
      alert("Erro ao carregar dados!");
    }
  };

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 p-6 md:p-10 space-y-8 font-sans">
      {/* Cabeçalho da Página */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-6 border-b border-slate-800">
        <div>
          <h1 className="text-2xl sm:text-3xl font-bold tracking-tight text-slate-50">
            Gestão de <span className="text-teal-400">Usuários</span>
          </h1>
          <p className="text-slate-400 text-sm mt-1">
            Gerencie os acessos e permissões da equipe da pousada.
          </p>
        </div>

        <Link
          href="/usuarios/novo"
          className="inline-flex items-center justify-center gap-2 px-5 py-2.5 rounded-xl bg-gradient-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/10 active:scale-95 text-sm"
        >
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4" />
          </svg>
          Novo Usuário
        </Link>
      </div>

      {/* Container da Tabela */}
      <div className="bg-slate-900/80 border border-slate-800/80 rounded-2xl shadow-xl overflow-hidden backdrop-blur-md">
        
        {/* Contador no topo */}
        <div className="p-4 border-b border-slate-800 flex justify-between items-center bg-slate-900/40">
          <span className="text-xs text-slate-400">
            Total: <strong className="text-slate-200">{usuarios.length}</strong> usuários cadastrados
          </span>
        </div>

        {/* Tabela Responsiva */}
        <div className="overflow-x-auto w-full">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-slate-950/60 border-b border-slate-800 text-xs uppercase tracking-wider text-slate-400">
                <th className="py-3.5 px-6 font-semibold">Código</th>
                <th className="py-3.5 px-6 font-semibold">Nome</th>
                <th className="py-3.5 px-6 font-semibold">CPF</th>
                <th className="py-3.5 px-6 font-semibold">E-mail</th>
                <th className="py-3.5 px-6 font-semibold">Status</th>
              </tr>
            </thead>

            <tbody className="divide-y divide-slate-800/60 text-sm">
              {usuarios.map((usuario) => (
                <tr key={usuario.id} className="hover:bg-slate-800/40 transition-colors">
                  <td className="py-4 px-6 font-mono text-xs text-teal-400">
                    #{usuario.id}
                  </td>

                  <td className="py-4 px-6 font-medium text-slate-200 flex items-center gap-3">
                    <div className="w-8 h-8 rounded-full bg-teal-950 border border-teal-500/30 text-teal-400 font-semibold flex items-center justify-center text-xs">
                      {usuario.nome ? usuario.nome.substring(0, 2).toUpperCase() : "US"}
                    </div>
                    {usuario.nome}
                  </td>

                  <td className="py-4 px-6 text-slate-400 font-mono text-xs">
                    {usuario.cpf}
                  </td>

                  <td className="py-4 px-6 text-slate-400">
                    {usuario.email}
                  </td>

                  <td className="py-4 px-6">
                    <span
                      className={`inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-medium ${
                        usuario.status?.toLowerCase() === "ativo"
                          ? "bg-emerald-950/60 text-emerald-400 border border-emerald-500/30"
                          : "bg-slate-800 text-slate-400 border border-slate-700"
                      }`}
                    >
                      <span
                        className={`w-1.5 h-1.5 rounded-full ${
                          usuario.status?.toLowerCase() === "ativo" ? "bg-emerald-400" : "bg-slate-500"
                        }`}
                      />
                      {usuario.status}
                    </span>
                  </td>
                </tr>
              ))}

              {usuarios.length === 0 && (
                <tr>
                  <td colSpan={5} className="py-12 text-center text-slate-500 italic">
                    Nenhum usuário encontrado!
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