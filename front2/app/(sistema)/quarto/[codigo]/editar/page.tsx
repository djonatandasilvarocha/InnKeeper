"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import axios from "axios";
import { Quarto } from "@/app/types/quarto";
import QuartoForm from "../../components/QuartoForm";

export default function EditarQuarto() {
  const parametro = useParams();
  const codigo = Number(parametro.codigo);

  const [quarto, setQuarto] = useState<Quarto | null>(null);
  const router = useRouter();

  useEffect(() => {
    buscarDados();
  }, []);

  const buscarDados = async () => {
    try {
      const valorBack = await axios.get<Quarto>(
        `http://localhost:8080/quarto/${codigo}`
      );

      if (valorBack.status === 200) {
        setQuarto(valorBack.data);
      } else {
        router.push("/quarto");
      }
    } catch (error) {
      alert("Erro ao buscar dados do quarto!");
      router.push("/quarto");
    }
  };

  if (!quarto) {
    return (
      <div className="w-full px-6 py-10 text-center text-slate-400">
        Carregando dados do quarto...
      </div>
    );
  }

  return (
    <div className="w-full px-6 py-10 md:px-10 md:py-14">
      <div className="max-w-4xl mx-auto space-y-6">
        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 md:p-8 shadow-xl">
          <Link
            href="/quarto"
            className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-slate-400 hover:text-teal-400 transition-colors duration-200"
          >
            ← Voltar para Listagem
          </Link>

          <div className="mt-4">
            <h1 className="text-2xl sm:text-3xl font-bold text-white tracking-tight">
              Editar Quarto <span className="text-teal-400">#{codigo}</span>
            </h1>
            <p className="mt-1 text-xs text-slate-400 uppercase tracking-wider">
              Atualize os detalhes cadastrais do quarto
            </p>
          </div>
        </div>

        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 sm:p-8 shadow-xl">
          <QuartoForm quartoExistente={quarto} />
        </div>
      </div>
    </div>
  );
}