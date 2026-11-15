"use client";

import Link from "next/link";

export default function Home() {
  return (
    <div className="min-h-full bg-slate-950 text-slate-100 p-6 md:p-10 flex flex-col justify-between font-sans space-y-10">
      {/* Conteúdo Principal / Banner */}
      <div className="space-y-8">
        <div className="relative overflow-hidden rounded-3xl bg-gradient-to-r from-slate-900 via-slate-900/90 to-teal-950/40 border border-slate-800/80 p-8 md:p-12 shadow-2xl">
          <div className="relative z-10 max-w-2xl space-y-4">
            <span className="inline-flex items-center gap-2 px-3 py-1 rounded-full text-xs font-semibold bg-teal-500/10 text-teal-400 border border-teal-500/20">
              <span className="w-2 h-2 rounded-full bg-teal-400 animate-pulse" />
              Painel Principal
            </span>

            <h1 className="text-3xl sm:text-5xl font-extrabold text-slate-50 tracking-tight leading-tight">
              Bem-vindo ao sistema de gestão de{" "}
              <span className="text-transparent bg-clip-text bg-gradient-to-r from-teal-400 to-emerald-400">
                Hospedagens!
              </span>
            </h1>

            <p className="text-slate-400 text-base sm:text-lg leading-relaxed">
              Gerencie reservas, hóspedes e acompanhe a ocupação da pousada de forma simples e rápida.
            </p>

            <div className="pt-2 flex flex-wrap gap-4">
              <Link
                href="/reserva"
                className="inline-flex items-center gap-2 px-6 py-3 rounded-xl bg-gradient-to-r from-teal-500 to-emerald-500 text-slate-950 font-semibold hover:from-teal-400 hover:to-emerald-400 transition-all duration-200 shadow-lg shadow-teal-500/20 active:scale-95 text-sm"
              >
                Gerenciar Reservas
                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 5l7 7m0 0l-7 7m7-7H3" />
                </svg>
              </Link>
            </div>
          </div>

          {/* Luz de fundo decorativa (Glow) */}
          <div className="absolute top-1/2 -right-10 -translate-y-1/2 w-96 h-96 bg-teal-500/10 rounded-full blur-3xl pointer-events-none" />
        </div>

        {/* Grade de Módulos Rápidos */}
        <div>
          <h2 className="text-xs font-semibold uppercase tracking-wider text-slate-400 mb-4">
            Acesso Rápido aos Módulos
          </h2>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5">
            {/* Módulo Hóspedes */}
            <Link
              href="/hospede"
              className="group p-6 bg-slate-900/60 hover:bg-slate-800/60 border border-slate-800/80 hover:border-teal-500/40 rounded-2xl transition-all duration-200 shadow-lg flex flex-col justify-between space-y-4"
            >
              <div className="w-12 h-12 rounded-xl bg-teal-950 border border-teal-500/30 text-teal-400 flex items-center justify-center text-xl group-hover:scale-110 transition-transform">
                👤
              </div>
              <div>
                <h3 className="text-base font-bold text-slate-100 group-hover:text-teal-400 transition-colors">
                  Hóspedes
                </h3>
                <p className="text-xs text-slate-400 mt-1">
                  Cadastre e consulte as informações dos hóspedes.
                </p>
              </div>
            </Link>

            {/* Módulo Quartos */}
            <Link
              href="/quarto"
              className="group p-6 bg-slate-900/60 hover:bg-slate-800/60 border border-slate-800/80 hover:border-teal-500/40 rounded-2xl transition-all duration-200 shadow-lg flex flex-col justify-between space-y-4"
            >
              <div className="w-12 h-12 rounded-xl bg-teal-950 border border-teal-500/30 text-teal-400 flex items-center justify-center text-xl group-hover:scale-110 transition-transform">
                🛏️
              </div>
              <div>
                <h3 className="text-base font-bold text-slate-100 group-hover:text-teal-400 transition-colors">
                  Quartos
                </h3>
                <p className="text-xs text-slate-400 mt-1">
                  Controle a disponibilidade e os tipos de acomodação.
                </p>
              </div>
            </Link>

            {/* Módulo Reservas */}
            <Link
              href="/reserva"
              className="group p-6 bg-slate-900/60 hover:bg-slate-800/60 border border-slate-800/80 hover:border-teal-500/40 rounded-2xl transition-all duration-200 shadow-lg flex flex-col justify-between space-y-4"
            >
              <div className="w-12 h-12 rounded-xl bg-teal-950 border border-teal-500/30 text-teal-400 flex items-center justify-center text-xl group-hover:scale-110 transition-transform">
                📅
              </div>
              <div>
                <h3 className="text-base font-bold text-slate-100 group-hover:text-teal-400 transition-colors">
                  Reservas
                </h3>
                <p className="text-xs text-slate-400 mt-1">
                  Acompanhe o calendário e status das reservas.
                </p>
              </div>
            </Link>

            {/* Módulo Usuários */}
            <Link
              href="/usuarios"
              className="group p-6 bg-slate-900/60 hover:bg-slate-800/60 border border-slate-800/80 hover:border-teal-500/40 rounded-2xl transition-all duration-200 shadow-lg flex flex-col justify-between space-y-4"
            >
              <div className="w-12 h-12 rounded-xl bg-teal-950 border border-teal-500/30 text-teal-400 flex items-center justify-center text-xl group-hover:scale-110 transition-transform">
                ⚙️
              </div>
              <div>
                <h3 className="text-base font-bold text-slate-100 group-hover:text-teal-400 transition-colors">
                  Usuários
                </h3>
                <p className="text-xs text-slate-400 mt-1">
                  Gerencie acessos e permissões dos funcionários.
                </p>
              </div>
            </Link>
          </div>
        </div>
      </div>

      {/* Rodapé */}
      <div className="border-t border-slate-800/80 pt-6 mt-12 text-xs text-slate-500">
        Sistema de Gestão de Pousadas • Painel Administrativo
      </div>
    </div>
  );
}