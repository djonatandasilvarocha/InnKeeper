

export default function Header(){

    return(

        <header className="w-full bg-[#030712] border-b border-slate-800 px-6 py-4">
  <div className="max-w-7xl mx-auto flex items-center justify-between">
    <div className="flex items-center gap-3">
      <div className="flex items-center justify-center w-10 h-10 rounded-full bg-slate-800/80 border border-slate-700 text-teal-400">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="w-5 h-5 stroke-current"
          fill="none"
          viewBox="0 0 24 24"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
        >
          <path d="M19 21v" />
          <circle cx="12" cy="7" r="4" />
        </svg>
      </div>
      <span className="text-sm font-medium text-slate-200">
        Usuário Djonatan Rocha
      </span>
    </div>
    <button className="px-4 py-2 text-sm font-medium text-slate-300 hover:text-white bg-slate-800/50 hover:bg-slate-800 border border-slate-700/60 rounded-lg transition-colors">
      Sair
    </button>
  </div>
</header>
    )



}