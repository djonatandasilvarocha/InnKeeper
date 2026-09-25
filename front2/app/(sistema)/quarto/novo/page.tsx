"use client";

import Link from "next/link";
import QuartoForm from "../components/QuartoForm";

export default function NovoQuarto() {
  return (
    <div className="w-full px-6 py-10 md:px-10 md:py-14">
      <div className="max-w-4xl mx-auto space-y-6">
        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 md:p-8 shadow-xl">
          <Link
            href="/quarto"
            className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-slate-400 hover:text-teal-400 transition-colors duration-200"
          >
            ← Voltar para Listagem
          </Link>

          <div className="mt-4">
            <h1 className="text-2xl sm:text-3xl font-bold text-white tracking-tight">
              Cadastrar Novo <span className="text-teal-400">Quarto</span>
            </h1>
            <p className="mt-1 text-xs text-slate-400 uppercase tracking-wider">
              Preencha os campos abaixo para adicionar um novo quarto à pousada
            </p>
          </div>
        </div>

        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 sm:p-8 shadow-xl">
          <QuartoForm />
        </div>
      </div>
    </div>
  );
}