
"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";

import { Reserva } from "@/app/types/reserva";
import { consultaReservaPorId } from "@/app/services/reservaService";
import ReservaForm from "../../components/ReservaForm";

export default function EditarReserva() {
  const params = useParams();
  const router = useRouter();

  const codigo = Number(params.codigo);

  const [reserva, setReserva] = useState<Reserva | null>(null);
  const [erro, setErro] = useState("");

  useEffect(() => {
    let ativo = true;

    async function buscarDados() {
      try {
        const dados = await consultaReservaPorId(codigo);

        if (ativo) {
          setReserva(dados);
        }
      } catch {
        if (ativo) {
          setErro("Erro ao buscar dados da reserva.");
          router.replace("/reserva");
        }
      }
    }

    if (Number.isInteger(codigo) && codigo > 0) {
      void buscarDados();
    } else {
      router.replace("/reserva");
    }

    return () => {
      ativo = false;
    };
  }, [codigo, router]);

  if (erro) {
    return (
      <div className="w-full px-6 py-10 text-center text-red-400">
        {erro}
      </div>
    );
  }

  if (!reserva) {
    return (
      <div className="w-full px-6 py-10 text-center text-slate-400">
        Carregando dados da reserva...
      </div>
    );
  }

  return (
    <div className="w-full px-6 py-10 md:px-10 md:py-14">
      <div className="max-w-4xl mx-auto space-y-6">

        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 md:p-8 shadow-xl">

          <Link
            href="/reserva"
            className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-slate-400 hover:text-teal-400 transition-colors duration-200"
          >
            ← Voltar para Listagem
          </Link>

          <div className="mt-4">
            <h1 className="text-2xl sm:text-3xl font-bold text-white tracking-tight">
              Editar Reserva{" "}
              <span className="text-teal-400">
                #{codigo}
              </span>
            </h1>

            <p className="mt-1 text-xs text-slate-400 uppercase tracking-wider">
              Atualize as informações da reserva
            </p>
          </div>
        </div>

        <div className="bg-[#0b1120] border border-slate-800/80 rounded-2xl p-6 sm:p-8 shadow-xl">
          <ReservaForm reservaExistente={reserva} />
        </div>

      </div>
    </div>
  );
}
