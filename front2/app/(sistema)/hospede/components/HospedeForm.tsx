"use client";

import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import axios from "axios";
import { Hospede, HospedeFormProps } from "@/app/types/hospede";

export default function HospedeForm({ hospedeExistente }: HospedeFormProps) {
  const router = useRouter();

  const [hospede, setHospede] = useState<Hospede>(
    hospedeExistente || new Hospede(null, "", "", "", "")
  );

  const handlerChange = (
    campo: "nome" | "cpf" | "email" | "telefone",
    valor: string
  ) => {
    setHospede(
      (valorAnterior) =>
        new Hospede(
          valorAnterior.id,
          campo === "nome" ? valor : valorAnterior.nome,
          campo === "cpf" ? valor : valorAnterior.cpf,
          campo === "email" ? valor : valorAnterior.email,
          campo === "telefone" ? valor : valorAnterior.telefone
        )
    );
  };

  const handlerSalvar = async (formData: FormData) => {
    try {
      if (hospedeExistente) {
        const dadosRetorno = await axios.put(
          `http://localhost:8080/hospede/${hospede.id}`,
          hospede
        );

        if (dadosRetorno.status === 200) {
          alert("Hóspede atualizado com sucesso!");
        } else {
          alert(dadosRetorno.data);
          return;
        }
      } else {
        const dadosRetorno = await axios.post(
          "http://localhost:8080/hospede",
          hospede
        );

        if (dadosRetorno.status === 200) {
          alert("Hóspede cadastrado com sucesso!");
        } else {
          alert(dadosRetorno.data);
          return;
        }
      }

      router.push("/hospede");
    } catch (error) {
      alert("Erro ao salvar hóspede!");
    }
  };

  return (
    <form
      action={handlerSalvar}
      className="w-full max-w-2xl mx-auto bg-[#0b1120] border border-slate-800 rounded-xl p-8 shadow-2xl"
    >
      <div className="space-y-6">
        <div>
          <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
            Nome Completo:
          </label>
          <input
            name="nome"
            type="text"
            value={hospede.nome}
            required
            onChange={(e) => handlerChange("nome", e.target.value)}
            placeholder="Digite o nome do hóspede"
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
            CPF:
          </label>
          <input
            name="cpf"
            type="text"
            value={hospede.cpf}
            required
            onChange={(e) => handlerChange("cpf", e.target.value)}
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
            value={hospede.email}
            required
            onChange={(e) => handlerChange("email", e.target.value)}
            placeholder="exemplo@email.com"
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
            Telefone:
          </label>
          <input
            name="telefone"
            type="text"
            value={hospede.telefone}
            required
            onChange={(e) => handlerChange("telefone", e.target.value)}
            placeholder="(00) 00000-0000"
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          />
        </div>

        <div className="flex items-center justify-end gap-4 pt-4 border-t border-slate-800/80">
          <Link
            href="/hospede"
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