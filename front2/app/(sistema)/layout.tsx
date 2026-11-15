
"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { useSelector } from "react-redux";

import type { RootState } from "@/app/redux/store";

import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import Footer from "../components/Footer";

export default function SistemaLayout({
  children
}: {
  children: React.ReactNode;
}) {

  const router = useRouter();

  // Consulta o token diretamente na store do Redux
  const token = useSelector(
    (state: RootState) => state.auth.token
  );

  // Se não existir token, redireciona para o login
  useEffect(() => {
    if (!token) {
      router.replace("/login");
    }
  }, [token, router]);

  // Não renderiza o sistema sem autenticação
  if (!token) {
    return null;
  }

  // Renderiza o sistema somente com token
  return (
    <div className="flex min-h-screen bg-[#030712] text-slate-100">

      <Sidebar />

      <div className="flex-1 flex flex-col min-w-0">

        <Header />

        <main className="flex-1 w-full max-w-7xl mx-auto p-6 md:p-8">
          {children}
        </main>

        <Footer />

      </div>

    </div>
  );
}
