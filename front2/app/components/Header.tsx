
"use client";

import { useRouter } from "next/navigation";
import { useDispatch, useSelector } from "react-redux";

import type { AppDispatch, RootState } from "@/app/redux/store";
import { logout } from "@/app/redux/slices/authSlice";

export default function Header() {
  const router = useRouter();
  const dispatch = useDispatch<AppDispatch>();

  const usuario = useSelector(
    (state: RootState) => state.auth.usuario
  );

  const handleLogout = () => {
    // Remove o token JWT e os dados do usuário do Redux
    dispatch(logout());

    // Redireciona para a página de login
    router.replace("/login");
  };

  return (
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
              <circle cx="12" cy="7" r="4" />
              <path d="M5.5 21a6.5 6.5 0 0 1 13 0" />
            </svg>
          </div>

          <span className="text-sm font-medium text-slate-200">
            Usuário {usuario?.nome || "Logado"}
          </span>
        </div>

        <button
          type="button"
          onClick={handleLogout}
          className="px-4 py-2 text-sm font-medium text-slate-300 hover:text-white bg-slate-800/50 hover:bg-slate-800 border border-slate-700/60 rounded-lg transition-colors"
        >
          Sair
        </button>

      </div>
    </header>
  );
}
