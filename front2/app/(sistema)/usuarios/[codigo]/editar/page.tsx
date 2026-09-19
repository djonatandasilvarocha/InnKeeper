"use client"
import Link from "next/link";
import UsuarioForm from "../../components/UsuarioForm";
import { useParams } from "next/navigation";

export default function EditarUsuario() {
  const parametro = useParams();
  const codigo = Number(parametro.codigo);

  return (
    <div className="w-full px-6 py-10 md:px-10 md:py-14">
      <div className="max-w-4xl mx-auto space-y-6">

        {/* Header e Navegação */}
        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 md:p-8 shadow-xl">
          <Link
            href="/usuarios"
            className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-slate-400 hover:text-teal-400 transition-colors duration-200"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="w-4 h-4 stroke-current fill-none stroke-[2]"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M10.5 19.5L3 12m0 0l7.5-7.5M3 12h18"
              />
            </svg>
            Voltar para Listagem
          </Link>

          <div className="mt-4">
            <h1 className="text-2xl sm:text-3xl font-bold text-white tracking-tight">
              Editar usuário <span className="text-teal-400">#{codigo}</span>
            </h1>
            <p className="mt-1 text-xs text-slate-400 uppercase tracking-wider">
              Atualize os dados do usuário no sistema
            </p>
          </div>
        </div>

        {/* Container do Formulário */}
        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 sm:p-8 shadow-xl">
          <UsuarioForm />
        </div>

      </div>
    </div>
  );
}