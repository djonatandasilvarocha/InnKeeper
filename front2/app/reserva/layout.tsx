import Link from "next/link";

export default function ReservaLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 flex flex-col font-sans">
      <header className="border-b border-slate-800/80 bg-slate-950/50 backdrop-blur px-6 py-4 sticky top-0 z-10">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <div className="flex items-center gap-3">
            <span className="p-2 rounded-lg bg-teal-500/10 text-teal-400">
              <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
            </span>
            <h1 className="text-lg font-bold text-slate-50 tracking-wide">Painel de Reservas</h1>
          </div>
          <Link
            href="/"
            className="text-xs text-slate-400 hover:text-teal-400 transition-colors flex items-center gap-1 font-medium"
          >
            ← Voltar ao Início
          </Link>
        </div>
      </header>

      <main className="flex-1 p-6 md:p-10 max-w-7xl mx-auto w-full">
        {children}
      </main>

      <footer className="border-t border-slate-800/80 p-6 text-xs text-slate-500 text-center">
        Sistema de Gestão de Pousadas • Módulo Reservas
      </footer>
    </div>
  );
}