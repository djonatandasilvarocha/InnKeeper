import Image from "next/image";

export default function Home() {
  return (
    <div className="min-h-screen bg-slate-50 text-slate-800 font-sans antialiased selection:bg-teal-500 selection:text-white overflow-x-hidden">
      {/* Header */}
      <header className="fixed top-0 left-0 w-full bg-white/80 backdrop-blur-xl border-b border-teal-100/50 z-50 shadow-sm transition-all">
        <div className="max-w-7xl mx-auto px-6">
          <div className="flex items-center justify-between h-20">
            <a href="#" className="flex items-center gap-3 font-extrabold text-2xl text-slate-900 group">
              <div className="w-11 h-11 bg-gradient-to-tr from-teal-600 via-emerald-500 to-cyan-400 rounded-2xl flex items-center justify-center text-white shadow-lg shadow-teal-500/25 group-hover:scale-105 transition-transform">
                <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
              </div>
              <span className="bg-gradient-to-r from-teal-700 to-emerald-600 bg-clip-text text-transparent">InnKeeper</span>
            </a>
            
            <nav className="hidden md:flex gap-8 items-center font-medium text-slate-600">
              <a href="#problemas" className="hover:text-teal-600 transition-colors">O Desafio</a>
              <a href="#historia" className="hover:text-teal-600 transition-colors">Nossa História</a>
              <a href="#recursos" className="hover:text-teal-600 transition-colors">Recursos</a>
            </nav>

            <div className="flex items-center gap-4">
              <a href="#login" className="relative group overflow-hidden rounded-full p-[2px]">
                <span className="absolute inset-0 bg-gradient-to-r from-teal-500 to-emerald-500 rounded-full group-hover:opacity-100 opacity-80 transition-opacity"></span>
                <span className="relative px-6 py-2.5 rounded-full bg-white text-teal-700 font-semibold text-sm flex items-center gap-2 group-hover:bg-opacity-90 transition-all shadow-sm">
                  Entrar no Sistema
                </span>
              </a>
            </div>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="pt-44 pb-28 relative overflow-hidden bg-gradient-to-b from-teal-50/60 via-emerald-50/20 to-transparent">
        {/* Decorative background color blobs */}
        <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[600px] bg-gradient-to-tr from-teal-400/20 to-emerald-300/20 rounded-full blur-3xl pointer-events-none -z-10 animate-pulse"></div>

        <div className="max-w-7xl mx-auto px-6">
          <div className="grid grid-cols-1 lg:grid-cols-[1.1fr_0.9fr] gap-16 items-center">
            
            <div>
              <div className="inline-flex items-center gap-2 bg-gradient-to-r from-teal-500/10 to-emerald-500/10 text-teal-800 px-4 py-2 rounded-full text-sm font-bold mb-6 border border-teal-500/20 shadow-sm">
                <span className="w-2 h-2 rounded-full bg-teal-500 animate-ping"></span>
                Gestão de Reservas Simplificada & Inteligente
              </div>

              <h1 className="text-5xl lg:text-6xl font-extrabold text-slate-900 mb-6 tracking-tight leading-[1.15]">
                Transforme a recepção da sua <span className="bg-gradient-to-r from-teal-600 to-emerald-500 bg-clip-text text-transparent">pousada</span> em excelência.
              </h1>

              <p className="text-lg text-slate-600 mb-10 max-w-[540px] leading-relaxed">
                O <strong className="text-teal-700">InnKeeper</strong> centraliza o controle de hóspedes, quartos e datas em um único sistema intuitivo, impedindo conflitos e agilizando check-outs com máxima eficiência.
              </p>

              <div className="flex flex-wrap gap-4">
                <a href="#historia" className="bg-gradient-to-r from-teal-600 to-emerald-600 text-white px-8 py-4 rounded-2xl font-bold shadow-lg shadow-teal-600/30 hover:shadow-teal-600/50 hover:-translate-y-0.5 transition-all flex items-center gap-3 group">
                  Conhecer o Projeto
                  <svg className="w-5 h-5 group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2.5" d="M13 7l5 5m0 0l-5 5m5-5H6"></path></svg>
                </a>
              </div>
            </div>

            {/* Interactive/Colorful Mockup Card */}
            <div className="relative">
              <div className="absolute -inset-1.5 bg-gradient-to-r from-teal-500 to-emerald-400 rounded-3xl blur-xl opacity-30 group-hover:opacity-100 transition duration-1000 group-hover:duration-200"></div>
              
              <div className="relative bg-white/90 backdrop-blur-xl p-8 rounded-2xl shadow-2xl border border-teal-100">
                <div className="flex justify-between items-center mb-6 pb-4 border-b border-slate-100">
                  <div className="flex gap-2">
                    <div className="w-3.5 h-3.5 rounded-full bg-rose-400 shadow-sm"></div>
                    <div className="w-3.5 h-3.5 rounded-full bg-amber-400 shadow-sm"></div>
                    <div className="w-3.5 h-3.5 rounded-full bg-emerald-400 shadow-sm"></div>
                  </div>
                  <span className="text-xs font-bold uppercase tracking-wider text-teal-700 bg-teal-50 px-3 py-1 rounded-full">Painel ao Vivo</span>
                </div>

                <div className="flex flex-col gap-3.5">
                  <div className="flex justify-between items-center p-4 bg-gradient-to-r from-teal-50/80 to-emerald-50/40 rounded-xl border border-teal-100/60 shadow-xs">
                    <div className="flex items-center gap-3">
                      <div className="w-2.5 h-2.5 rounded-full bg-amber-500 animate-pulse"></div>
                      <span className="font-semibold text-slate-700">Quarto 102 - Casal Deluxe</span>
                    </div>
                    <span className="text-xs font-bold text-amber-700 bg-amber-100 px-3 py-1 rounded-lg">Check-out Hoje</span>
                  </div>

                  <div className="flex justify-between items-center p-4 bg-gradient-to-r from-emerald-50/80 to-teal-50/40 rounded-xl border border-emerald-100/60 shadow-xs">
                    <div className="flex items-center gap-3">
                      <div className="w-2.5 h-2.5 rounded-full bg-emerald-500"></div>
                      <span className="font-semibold text-slate-700">Quarto 204 - Standard</span>
                    </div>
                    <span className="text-xs font-bold text-emerald-700 bg-emerald-100 px-3 py-1 rounded-lg">Disponível</span>
                  </div>

                  <div className="flex justify-between items-center p-4 bg-gradient-to-r from-cyan-50/80 to-teal-50/40 rounded-xl border border-cyan-100/60 shadow-xs">
                    <div className="flex items-center gap-3">
                      <div className="w-2.5 h-2.5 rounded-full bg-cyan-500"></div>
                      <span className="font-semibold text-slate-700">Conflitos de Reserva</span>
                    </div>
                    <span className="text-xs font-bold text-cyan-800 bg-cyan-100 px-3 py-1 rounded-lg">0 Detectados ✨</span>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </section>

      {/* Problems Section */}
      <section className="py-28 bg-white relative" id="problemas">
        <div className="max-w-7xl mx-auto px-6">
          <div className="text-center max-w-2xl mx-auto mb-16">
            <span className="text-rose-500 font-bold tracking-wider uppercase text-sm bg-rose-50 px-4 py-1.5 rounded-full border border-rose-100">O Risco Operacional</span>
            <h2 className="text-4xl font-extrabold mt-4 mb-4 text-slate-900 tracking-tight">Os desafios da gestão manual</h2>
            <p className="text-slate-600 text-lg">Administrar uma pousada envolve muito mais do que realizar reservas. Sem a ferramenta certa, erros custam caro.</p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="group bg-gradient-to-b from-white to-rose-50/30 p-8 rounded-3xl border border-rose-100/80 shadow-lg shadow-rose-900/5 hover:-translate-y-1.5 transition-all duration-300">
              <div className="w-14 h-14 bg-gradient-to-tr from-rose-500 to-pink-500 text-white rounded-2xl flex items-center justify-center text-2xl mb-6 shadow-md shadow-rose-500/30 group-hover:scale-110 transition-transform">⚠️</div>
              <h3 className="text-xl font-bold mb-3 text-slate-900">Dupla Reserva</h3>
              <p className="text-slate-600 text-sm leading-relaxed">Um quarto pode acabar sendo reservado para duas pessoas no mesmo período por falta de controle centralizado.</p>
            </div>

            <div className="group bg-gradient-to-b from-white to-amber-50/30 p-8 rounded-3xl border border-amber-100/80 shadow-lg shadow-amber-900/5 hover:-translate-y-1.5 transition-all duration-300">
              <div className="w-14 h-14 bg-gradient-to-tr from-amber-500 to-orange-500 text-white rounded-2xl flex items-center justify-center text-2xl mb-6 shadow-md shadow-amber-500/30 group-hover:scale-110 transition-transform">📁</div>
              <h3 className="text-xl font-bold mb-3 text-slate-900">Dados Desorganizados</h3>
              <p className="text-slate-600 text-sm leading-relaxed">Informações de hóspedes espalhadas em planilhas ou papéis geram confusão e perda de tempo valioso na recepção.</p>
            </div>

            <div className="group bg-gradient-to-b from-white to-cyan-50/30 p-8 rounded-3xl border border-cyan-100/80 shadow-lg shadow-cyan-900/5 hover:-translate-y-1.5 transition-all duration-300">
              <div className="w-14 h-14 bg-gradient-to-tr from-cyan-500 to-blue-500 text-white rounded-2xl flex items-center justify-center text-2xl mb-6 shadow-md shadow-cyan-500/30 group-hover:scale-110 transition-transform">🧮</div>
              <h3 className="text-xl font-bold mb-3 text-slate-900">Erros de Cálculo</h3>
              <p className="text-slate-600 text-sm leading-relaxed">O cálculo manual do valor da hospedagem e da diária frequentemente resulta em incorreções no fechamento da conta.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Story / Solution Section */}
      <section className="py-28 bg-gradient-to-b from-slate-50 to-teal-50/30 relative" id="historia">
        <div className="max-w-7xl mx-auto px-6">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">
            
            <div>
              <span className="text-teal-600 font-bold tracking-wider uppercase text-sm bg-teal-50 px-4 py-1.5 rounded-full border border-teal-100">Nossa Origem</span>
              <h2 className="text-4xl font-extrabold mt-4 mb-6 text-slate-900 tracking-tight">Nossa História & Propósito</h2>
              
              <p className="text-slate-600 mb-4 text-base leading-relaxed">
                O <strong className="text-teal-700">InnKeeper</strong> nasceu a partir da percepção de que gerenciar uma pousada exige precisão e harmonia. Em locais que utilizam processos manuais ou ferramentas desconectadas, a rotina diária torna-se estressante.
              </p>
              
              <p className="text-slate-600 mb-8 text-base leading-relaxed">
                Foi pensando nesses desafios que desenvolvemos uma solução para centralizar e simplificar cada etapa: do cadastro à estadia.
              </p>
              
              <div className="flex flex-col gap-4">
                <div className="flex items-start gap-4 p-4 rounded-2xl bg-white border border-teal-100 shadow-xs">
                  <div className="w-7 h-7 bg-teal-500 text-white rounded-full flex items-center justify-center font-bold flex-shrink-0 mt-0.5 shadow-sm shadow-teal-500/40">✓</div>
                  <div>
                    <h4 className="text-base font-bold text-slate-900 mb-1">Prevenção Automática de Conflitos</h4>
                    <p className="text-slate-600 text-sm leading-relaxed">O sistema cruza as datas automaticamente e impede que o mesmo quarto seja ocupado por reservas conflitantes.</p>
                  </div>
                </div>

                <div className="flex items-start gap-4 p-4 rounded-2xl bg-white border border-teal-100 shadow-xs">
                  <div className="w-7 h-7 bg-teal-500 text-white rounded-full flex items-center justify-center font-bold flex-shrink-0 mt-0.5 shadow-sm shadow-teal-500/40">✓</div>
                  <div>
                    <h4 className="text-base font-bold text-slate-900 mb-1" id="recursos">Check-out Inteligente</h4>
                    <p className="text-slate-600 text-sm leading-relaxed">Facilita o fechamento calculando o valor total da estadia baseado na quantidade exata de dias e no valor da diária.</p>
                  </div>
                </div>
              </div>
            </div>

            <div className="relative">
              <div className="absolute -inset-1 bg-gradient-to-r from-teal-500 to-emerald-400 rounded-3xl blur-xl opacity-25"></div>
              <div className="relative bg-gradient-to-br from-teal-900 to-emerald-950 rounded-3xl p-10 text-center text-white shadow-2xl border border-teal-700/50">
                <div className="w-full h-80 bg-slate-900/80 backdrop-blur-md rounded-2xl flex flex-col items-center justify-center shadow-inner border border-teal-500/20 p-6">
                  <div className="w-16 h-16 bg-gradient-to-tr from-teal-500 to-emerald-400 rounded-2xl flex items-center justify-center text-white shadow-lg mb-4 animate-bounce">
                    <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
                  </div>
                  <span className="font-extrabold text-2xl bg-gradient-to-r from-teal-300 to-emerald-300 bg-clip-text text-transparent">InnKeeper Dashboard</span>
                  <span className="text-xs text-teal-400/80 mt-2 font-medium">Controle total, zero planilhas confusas.</span>
                </div>
              </div>
            </div>

          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-slate-950 text-white pt-20 pb-10 border-t border-slate-900">
        <div className="max-w-7xl mx-auto px-6">
          <div className="flex flex-col md:flex-row justify-between items-center pb-12 border-b border-slate-800/80 gap-6">
            <div className="flex items-center gap-3 font-extrabold text-2xl">
              <div className="w-10 h-10 bg-gradient-to-tr from-teal-500 to-emerald-400 rounded-xl flex items-center justify-center text-white shadow-md shadow-teal-500/30">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
              </div>
              <span className="bg-gradient-to-r from-teal-400 to-emerald-400 bg-clip-text text-transparent">InnKeeper</span>
            </div>
            
            <div className="flex gap-8 text-slate-400 text-sm font-medium">
              <a href="#problemas" className="hover:text-teal-400 transition-colors">Desafios</a>
              <a href="#historia" className="hover:text-teal-400 transition-colors">História</a>
              <a href="#recursos" className="hover:text-teal-400 transition-colors">Recursos</a>
            </div>
          </div>
          
          <div className="pt-8 text-center text-slate-500 text-sm">
            <p>&copy; 2026 InnKeeper. Todos os direitos reservados. Criado para transformar a gestão de pousadas.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}
