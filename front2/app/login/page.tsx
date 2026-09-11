'use client'

import axios from "axios";
import { useRouter } from "next/navigation";
import { LoginResponse } from "../types/auth";

export default function Login() {
  const router = useRouter();

  const handlerLogin = async (formData: FormData) => {
    try{
    debugger;
    const emailTela = formData.get("email")?.toString() ?? "";
    const senhaTela = formData.get("senha")?.toString() ?? "";

    var loginResposta = await axios.post<LoginResponse>("http://localhost:8080/auth/login", 
    {email:emailTela, senha:senhaTela});

    if(loginResposta.status==200) {
      router.push("/home")
    }else{
      alert("Login ou senha Invalido!")
    }
  }catch (error){
    alert("Login ou senha Invalido!")
  }

  };

  return (
    // FUNDO EXTERNO: Slate super limpo com orbes luminosos em tom Teal/Emerald no fundo
    <div className="relative min-h-screen w-full flex items-center justify-center bg-slate-50 p-4 antialiased overflow-hidden selection:bg-teal-500 selection:text-white">
      
      {/* Detalhes/Efeitos de brilho flutuando ao fundo */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[500px] h-[500px] bg-gradient-to-tr from-teal-400/20 to-emerald-300/20 rounded-full blur-3xl pointer-events-none -z-10 animate-pulse" />
      <div className="absolute bottom-10 right-10 w-80 h-80 bg-teal-500/10 rounded-full blur-3xl pointer-events-none -z-10" />

      {/* CARD DE LOGIN */}
      <div className="relative z-10 w-full max-w-md bg-white/90 backdrop-blur-xl border border-teal-100 rounded-3xl p-8 sm:p-10 shadow-2xl shadow-teal-900/5 transition-all duration-300">
        <div>
          {/* LOGO / ÍCONE */}
          <div className="flex justify-center mb-6">
            <div className="w-12 h-12 bg-gradient-to-tr from-teal-600 via-emerald-500 to-cyan-400 rounded-2xl flex items-center justify-center text-white shadow-lg shadow-teal-500/25">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
                <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                <polyline points="9 22 9 12 15 12 15 22"></polyline>
              </svg>
            </div>
          </div>

          {/* TÍTULO */}
          <h1 className="text-2xl font-extrabold text-slate-900 text-center mb-2 tracking-tight">
            Entrar no sistema
          </h1>
          <p className="text-xs text-slate-500 text-center mb-8">
            Acesse o painel do <strong className="text-teal-700">InnKeeper</strong>
          </p>
        </div>

        <form action={handlerLogin} className="space-y-5">
          <div>
            {/* LABEL E-MAIL */}
            <label className="block text-xs font-bold text-slate-700 mb-1.5 uppercase tracking-wider">
              E-mail
            </label>
            {/* INPUT E-MAIL */}
            <input
              name="email"
              type="email"
              placeholder="seu@email.com"
              className="w-full px-4 py-3 bg-slate-50/80 border border-slate-200 rounded-xl text-slate-800 placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-teal-500/50 focus:border-teal-500 transition-all duration-200 text-sm"
            />
          </div>

          <div>
            {/* LABEL SENHA */}
            <label className="block text-xs font-bold text-slate-700 mb-1.5 uppercase tracking-wider">
              Senha
            </label>
            {/* INPUT SENHA */}
            <input
              name="senha"
              type="password"
              placeholder="••••••••"
              className="w-full px-4 py-3 bg-slate-50/80 border border-slate-200 rounded-xl text-slate-800 placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-teal-500/50 focus:border-teal-500 transition-all duration-200 text-sm"
            />
          </div>

          {/* BOTÃO SUBMIT */}
          <button
            type="submit"
            className="w-full h-12 flex items-center justify-center bg-gradient-to-r from-teal-600 to-emerald-600 hover:from-teal-700 hover:to-emerald-700 text-white font-bold rounded-xl shadow-lg shadow-teal-600/30 hover:shadow-teal-600/40 transition-all duration-200 ease-in-out active:scale-[0.98] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500 mt-6"
          >
            Entrar
          </button>
        </form>
      </div>  
    </div>
  );
}