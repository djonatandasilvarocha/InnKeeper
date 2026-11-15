

export default function Footer(){

    const anoAtual = new Date().getFullYear();

return (
  <footer className="w-full bg-[#030712] border-t border-slate-800/80 py-4 px-6 mt-auto">
    <div className="max-w-7xl mx-auto flex items-center justify-center">
      <div className="text-center">
        <p className="text-xs text-slate-400 font-normal tracking-wide">
          &copy;{anoAtual}{" "}
          <span className="text-teal-400 font-medium">
            Sistema de Gerenciamento de Pousadas.
          </span>{" "}
          Todos os direitos reservados.
        </p>
      </div>
    </div>
  </footer>
);
}