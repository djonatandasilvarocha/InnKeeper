"use client";

import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import axios from "axios";
import { Quarto, QuartoFormProps } from "@/app/types/quarto";

export default function QuartoForm({ quartoExistente }: QuartoFormProps) {
  const router = useRouter();

  const [quarto, setQuarto] = useState<Quarto>(
    quartoExistente || new Quarto(null, "", "Standard", 2, 0, "DISPONIVEL")
  );

  const handlerChange = (
    campo: "numero" | "tipo" | "capacidade" | "diaria" | "status",
    valor: any
  ) => {
    setQuarto(
      (valorAnterior) =>
        new Quarto(
          valorAnterior.id,
          campo === "numero" ? valor : valorAnterior.numero,
          campo === "tipo" ? valor : valorAnterior.tipo,
          campo === "capacidade" ? Number(valor) : valorAnterior.capacidade,
          campo === "diaria" ? Number(valor) : valorAnterior.diaria,
          campo === "status" ? valor : valorAnterior.status
        )
    );
  };

  const handlerSalvar = async () => {
    try {
      if (quartoExistente) {
        const dadosRetorno = await axios.put(
          `http://localhost:8080/quarto/${quarto.id}`,
          quarto
        );

        if (dadosRetorno.status === 200) {
          alert("Quarto atualizado com sucesso!");
        } else {
          alert(dadosRetorno.data);
          return;
        }
      } else {
        const dadosRetorno = await axios.post(
          "http://localhost:8080/quarto",
          quarto
        );

        if (dadosRetorno.status === 200 || dadosRetorno.status === 201) {
          alert("Quarto cadastrado com sucesso!");
        } else {
          alert(dadosRetorno.data);
          return;
        }
      }

      router.push("/quarto");
    } catch (error) {
      alert("Erro ao salvar quarto!");
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
            Número do Quarto:
          </label>
          <input
            name="numero"
            type="text"
            value={quarto.numero}
            required
            onChange={(e) => handlerChange("numero", e.target.value)}
            placeholder="Ex: 101, 202, A-01"
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
            Tipo de Quarto:
          </label>
          <select
            name="tipo"
            value={quarto.tipo}
            onChange={(e) => handlerChange("tipo", e.target.value)}
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          >
            <option value="Solteiro">Solteiro</option>
            <option value="Duplo">Duplo</option>
            <option value="Standard">Standard</option>
            <option value="Luxo">Luxo</option>
            <option value="Suíte Máster">Suíte Máster</option>
          </select>
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
            Capacidade (Pessoas):
          </label>
          <input
            name="capacidade"
            type="number"
            min="1"
            value={quarto.capacidade}
            required
            onChange={(e) => handlerChange("capacidade", e.target.value)}
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
            Valor da Diária (R$):
          </label>
          <input
            name="diaria"
            type="number"
            step="0.01"
            min="0"
            value={quarto.diaria}
            required
            onChange={(e) => handlerChange("diaria", e.target.value)}
            placeholder="0.00"
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 placeholder-slate-500 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
            Status:
          </label>
          <select
            name="status"
            value={quarto.status}
            onChange={(e) => handlerChange("status", e.target.value)}
            className="w-full bg-[#030712] border border-slate-800 rounded-lg px-4 py-3 text-slate-100 focus:outline-none focus:border-teal-500 focus:ring-1 focus:ring-teal-500 transition-all duration-200"
          >
            <option value="DISPONIVEL">DISPONIVEL</option>
            <option value="OCUPADO">OCUPADO</option>
            <option value="MANUTENCAO">MANUTENCAO</option>
          </select>
        </div>

        <div className="flex items-center justify-end gap-4 pt-4 border-t border-slate-800/80">
          <Link
            href="/quarto"
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