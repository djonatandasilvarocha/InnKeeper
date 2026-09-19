"use client";

import Link from "next/link";

export default function Home() {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 p-6 md:p-10 flex flex-col justify-between font-sans">
      {/* Conteúdo Principal */}
      <div className="max-w-4xl space-y-6">
        <div>
          <h1 className="text-3xl md:text-4xl font-bold tracking-tight text-slate-50">
            Bem-vindo ao sistema de gestão de <span className="text-teal-400">Hospedagens</span>!
          </h1>
          <p className="text-slate-400 text-base mt-2">
            Gerencie reservas, hóspedes e acompanhe a ocupação da pousada de forma simples e rápida.
          </p>
        </div>

        {/* Atalho Rápido */}
        <div className="pt-4">
          <Link
            href="/usuarios"
            className="inline-flex items-center justify-center gap-2 px-5 py-2.5 rounded-xl bg-linear-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/10 active:scale-95 text-sm"
          >
            Acessar Usuários e Equipe
            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 5l7 7m0 0l-7 7m7-7H3" />
            </svg>
          </Link>
        </div>
      </div>

      {/* Rodapé simples */}
      <div className="border-t border-slate-800/80 pt-6 mt-12 text-xs text-slate-500">
        Sistema de Gestão de Pousadas • Painel Administrativo
      </div>
    </div>
  );
}