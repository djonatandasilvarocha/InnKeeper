
"use client";

import { useEffect, useState } from "react";
import Link from "next/link";

import { Usuario } from "@/app/types/usuario";

import {
  buscarListaUsuarios,
  deletarUsuario,
  atualizarStatusUsuario
} from "@/app/services/usuarioService";

export default function Usuarios() {
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);
  const [carregando, setCarregando] = useState(true);
  const [processando, setProcessando] = useState<number | null>(null);
  const [erro, setErro] = useState("");

  useEffect(() => {
    let ativo = true;

    async function carregarUsuarios() {
      try {
        const lista = await buscarListaUsuarios();

        if (ativo) {
          setUsuarios(lista);
        }
      } catch {
        if (ativo) {
          setErro("Erro ao carregar dados dos usuários!");
        }
      } finally {
        if (ativo) {
          setCarregando(false);
        }
      }
    }

    void carregarUsuarios();

    return () => {
      ativo = false;
    };
  }, []);

  const carregarDados = async () => {
    setCarregando(true);
    setErro("");

    try {
      const lista = await buscarListaUsuarios();
      setUsuarios(lista);
    } catch {
      setErro("Erro ao carregar dados dos usuários!");
    } finally {
      setCarregando(false);
    }
  };

  const handleDeletarUsuario = async (usuario: Usuario) => {
    if (usuario.id === null) return;

    const confirmado = window.confirm(
      `Deseja realmente excluir o usuário ${usuario.nome}?`
    );

    if (!confirmado) return;

    setProcessando(usuario.id);

    try {
      const status = await deletarUsuario(usuario.id);

      if (status === 200 || status === 204) {
        alert("Usuário excluído com sucesso!");
        await carregarDados();
      } else {
        alert("Não foi possível excluir o usuário.");
      }
    } catch {
      alert("Erro ao excluir usuário!");
    } finally {
      setProcessando(null);
    }
  };

  const handleAlterarStatusUsuario = async (usuario: Usuario) => {
    if (usuario.id === null) return;

    const acao =
      usuario.status === "ATIVO" ? "bloquear" : "ativar";

    const confirmado = window.confirm(
      `Deseja ${acao} o usuário ${usuario.nome}?`
    );

    if (!confirmado) return;

    setProcessando(usuario.id);

    try {
      await atualizarStatusUsuario(usuario);
      await carregarDados();
    } catch {
      alert("Erro ao alterar o status do usuário!");
    } finally {
      setProcessando(null);
    }
  };

  return (
    <div className="bg-slate-950 text-slate-100 p-6 md:p-10 space-y-8 font-sans">

      {/* Cabeçalho */}
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
          className="inline-flex items-center justify-center gap-2 px-5 py-2.5 rounded-xl bg-linear-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/10 active:scale-95 text-sm"
        >
          <svg
            className="w-4 h-4"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M12 4v16m8-8H4"
            />
          </svg>

          Novo Usuário
        </Link>
      </div>

      {erro && (
        <div className="p-4 rounded-xl bg-red-950/30 border border-red-500/30 text-red-400 text-sm">
          {erro}
        </div>
      )}

      {/* Tabela */}
      <div className="bg-slate-900/80 border border-slate-800/80 rounded-2xl shadow-xl overflow-hidden backdrop-blur-md">

        <div className="p-4 border-b border-slate-800 flex justify-between items-center bg-slate-900/40">

          <span className="text-xs text-slate-400">
            Total:{" "}
            <strong className="text-slate-200">
              {usuarios.length}
            </strong>{" "}
            usuários cadastrados
          </span>

          <button
            type="button"
            onClick={carregarDados}
            disabled={carregando}
            className="text-xs text-teal-400 hover:text-teal-300 disabled:opacity-50"
          >
            Atualizar
          </button>
        </div>

        <div className="overflow-x-auto w-full">

          <table className="w-full text-left border-collapse">

            <thead>
              <tr className="bg-slate-950/60 border-b border-slate-800 text-xs uppercase tracking-wider text-slate-400">

                <th className="py-3.5 px-6 font-semibold">
                  Código
                </th>

                <th className="py-3.5 px-6 font-semibold">
                  Nome
                </th>

                <th className="py-3.5 px-6 font-semibold">
                  CPF
                </th>

                <th className="py-3.5 px-6 font-semibold">
                  E-mail
                </th>

                <th className="py-3.5 px-6 font-semibold">
                  Status
                </th>

                <th className="py-3.5 px-6 font-semibold text-right">
                  Ações
                </th>

              </tr>
            </thead>

            <tbody className="divide-y divide-slate-800/60 text-sm">

              {usuarios.map((usuario) => (
                <tr
                  key={usuario.id}
                  className="hover:bg-slate-800/40 transition-colors"
                >

                  <td className="py-4 px-6 font-mono text-xs text-teal-400">
                    #{usuario.id}
                  </td>

                  <td className="py-4 px-6 text-slate-200">
                    <div className="flex items-center gap-3">

                      <div className="w-8 h-8 rounded-full bg-teal-950 border border-teal-500/30 text-teal-400 font-semibold flex items-center justify-center text-xs shrink-0">
                        {usuario.nome
                          ? usuario.nome.substring(0, 2).toUpperCase()
                          : "US"}
                      </div>

                      <span className="font-medium">
                        {usuario.nome}
                      </span>

                    </div>
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
                        usuario.status === "ATIVO"
                          ? "bg-emerald-950/60 text-emerald-400 border border-emerald-500/30"
                          : "bg-amber-950/60 text-amber-400 border border-amber-500/30"
                      }`}
                    >
                      <span
                        className={`w-1.5 h-1.5 rounded-full ${
                          usuario.status === "ATIVO"
                            ? "bg-emerald-400"
                            : "bg-amber-400"
                        }`}
                      />

                      {usuario.status}
                    </span>
                  </td>

                  <td className="py-4 px-6 text-right">
                    <div className="inline-flex items-center gap-3">

                      <Link
                        href={`/usuarios/${usuario.id}/editar`}
                        className="text-xs font-medium text-slate-300 hover:text-teal-400 transition-colors"
                      >
                        Editar
                      </Link>

                      <button
                        type="button"
                        disabled={processando !== null}
                        onClick={() => handleAlterarStatusUsuario(usuario)}
                        className={`text-xs font-medium transition-colors disabled:opacity-50 ${
                          usuario.status === "BLOQUEADO"
                            ? "text-emerald-400 hover:text-emerald-300"
                            : "text-amber-400 hover:text-amber-300"
                        }`}
                      >
                        {usuario.status === "BLOQUEADO"
                          ? "Ativar"
                          : "Bloquear"}
                      </button>

                      <button
                        type="button"
                        disabled={processando !== null}
                        onClick={() => handleDeletarUsuario(usuario)}
                        className="text-xs font-medium text-rose-500 hover:text-rose-400 transition-colors disabled:opacity-50"
                      >
                        Deletar
                      </button>

                    </div>
                  </td>

                </tr>
              ))}

              {carregando ? (
                <tr>
                  <td
                    colSpan={6}
                    className="py-12 text-center text-slate-400"
                  >
                    Carregando usuários...
                  </td>
                </tr>
              ) : usuarios.length === 0 ? (
                <tr>
                  <td
                    colSpan={6}
                    className="py-12 text-center text-slate-500 italic"
                  >
                    Nenhum usuário encontrado!
                  </td>
                </tr>
              ) : null}

            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
