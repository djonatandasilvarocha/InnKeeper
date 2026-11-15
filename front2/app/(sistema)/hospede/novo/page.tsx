import Link from "next/link";
import HospedeForm from "../components/HospedeForm";

export default function CadastroHospede() {
  return (
    <div className="min-h-full py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-4xl mx-auto space-y-6">
        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 md:p-8 shadow-xl">
          <Link
            href="/hospede"
            className="inline-flex items-center gap-2 text-sm font-medium text-slate-400 hover:text-teal-400 transition-colors duration-200 mb-6 group"
          >
            <span className="group-hover:-translate-x-1 transition-transform duration-200">←</span>
            Voltar para Listagem
          </Link>

          <div>
            <h1 className="text-2xl md:text-3xl font-bold text-white tracking-tight">
              Novo <span className="text-teal-400">Hóspede</span>
            </h1>
            <p className="mt-1 text-sm text-slate-400">
              Preencha os dados para registrar um novo hóspede
            </p>
          </div>
        </div>

        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 md:p-8 shadow-xl">
          <HospedeForm />
        </div>
      </div>
    </div>
  );
}