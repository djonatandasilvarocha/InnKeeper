import Link from "next/link";



export default function UsuarioForm(){


    return (
        <form className="w-full max-w-2xl mx-auto bg-[#0b1120] border border-slate-800 rounded-xl p-8 shadow-2xl">
          <div className="space-y-6">
            <div>
              <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
                Nome completo:
              </label>
              <input 
                name="nome" 
                type="text" 
                placeholder="Digite o nome completo"
                className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
              />
            </div>
      
            <div>
              <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
                CPF:
              </label>
              <input 
                name="CPF" 
                type="text" 
                placeholder="000.000.000-00"
                className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
              />
            </div>
      
            <div>
              <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
                E-mail:
              </label>
              <input 
                name="email" 
                type="email" 
                placeholder="exemplo@email.com"
                className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
              />
            </div>
      
            <div>
              <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
                Senha:
              </label>
              <input 
                name="Senha" 
                type="password" 
                placeholder="••••••••"
                className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
              />
            </div>
      
            <div className="flex items-center justify-end gap-4 pt-4 border-t border-slate-800/80">
              <Link 
                href="/usuarios" 
                className="px-5 py-2.5 text-sm font-medium text-slate-400 hover:text-slate-200 hover:bg-slate-800/60 rounded-lg transition-all duration-200"
              >
                Cancelar
              </Link>
              <button 
                type="submit" 
                className="px-6 py-2.5 text-sm font-semibold text-slate-950 bg-teal-400 hover:bg-teal-300 rounded-lg shadow-md hover:shadow-teal-500/10 active:scale-[0.98] transition-all duration-200"
              >
                Salvar
              </button>
            </div>
          </div>
        </form>
      );
}