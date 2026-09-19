import Link from "next/link";
export default function Sidebar(){

    return (
        <aside className="w-64 min-h-screen bg-[#030712] border-r border-slate-800/80 p-6 flex flex-col gap-8 shrink-0">
          <div className="text-xs font-semibold tracking-wider text-slate-400 uppercase leading-relaxed border-b border-slate-800 pb-4">
            Sistema de Gerenciamento de Pousadas 2026
          </div>
          <nav className="flex flex-col gap-1.5">
            <Link 
              href="/home" 
              className="flex items-center px-4 py-3 text-sm font-medium text-slate-300 rounded-lg hover:bg-slate-800/60 hover:text-teal-400 transition-all duration-200"
            >
              Home
            </Link>
            <Link 
              href="/usuarios" 
              className="flex items-center px-4 py-3 text-sm font-medium text-teal-400 bg-slate-800/80 rounded-lg border border-teal-500/20 shadow-sm transition-all duration-200"
            >
              Usuários
            </Link>
            <Link 
              href="/hospede" 
              className="flex items-center px-4 py-3 text-sm font-medium text-slate-300 rounded-lg hover:bg-slate-800/60 hover:text-teal-400 transition-all duration-200"
            >
              Hospedes
            </Link>
            <Link 
              href="/quarto" 
              className="flex items-center px-4 py-3 text-sm font-medium text-slate-300 rounded-lg hover:bg-slate-800/60 hover:text-teal-400 transition-all duration-200"
            >
              Quartos
            </Link>
            <Link 
              href="/reserva" 
              className="flex items-center px-4 py-3 text-sm font-medium text-slate-300 rounded-lg hover:bg-slate-800/60 hover:text-teal-400 transition-all duration-200"
            >
              Reservas
            </Link>
          </nav>
        </aside>
      );
}