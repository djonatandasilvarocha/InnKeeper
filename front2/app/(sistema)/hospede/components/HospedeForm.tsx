
"use client";

import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

import { Hospede, HospedeFormProps } from "@/app/types/hospede";

import {
  salvarHospede,
  atualizarHospede
} from "@/app/services/hospedeService";

import {
  buscarEnderecoPorCep,
  EnderecoResponse
} from "@/app/services/enderecoService";

export default function HospedeForm({
  hospedeExistente
}: HospedeFormProps) {
  const router = useRouter();

  const [hospede, setHospede] = useState<Hospede>(
    hospedeExistente || new Hospede(null, "", "", "", "")
  );

  const [cep, setCep] = useState("");
  const [endereco, setEndereco] = useState<EnderecoResponse | null>(null);
  const [buscandoCep, setBuscandoCep] = useState(false);
  const [erroCep, setErroCep] = useState("");

  const handlerChange = (
    campo: "nome" | "cpf" | "email" | "telefone",
    valor: string
  ) => {
    setHospede(
      (anterior) =>
        new Hospede(
          anterior.id,
          campo === "nome" ? valor : anterior.nome,
          campo === "cpf" ? valor : anterior.cpf,
          campo === "email" ? valor : anterior.email,
          campo === "telefone" ? valor : anterior.telefone
        )
    );
  };

  // Consultar o CEP pela API do backend
  const handlerBuscarCep = async () => {
    const cepLimpo = cep.replace(/\D/g, "");

    setErroCep("");
    setEndereco(null);

    if (cepLimpo.length !== 8) {
      setErroCep("Digite um CEP com 8 números.");
      return;
    }

    setBuscandoCep(true);

    try {
      const resultado = await buscarEnderecoPorCep(cepLimpo);

      setEndereco(resultado);
      setCep(resultado.cep);
    } catch {
      setErroCep("Não foi possível localizar o CEP informado.");
    } finally {
      setBuscandoCep(false);
    }
  };

  // Cadastrar ou atualizar hóspede
  const handlerSalvar = async (
    e: React.FormEvent<HTMLFormElement>
  ) => {
    e.preventDefault();

    try {
      if (hospedeExistente) {
        await atualizarHospede(hospede);
        alert("Hóspede atualizado com sucesso!");
      } else {
        await salvarHospede(hospede);
        alert("Hóspede cadastrado com sucesso!");
      }

      router.push("/hospede");
      router.refresh();
    } catch {
      alert("Erro ao salvar hóspede!");
    }
  };

  const estiloInput =
    "w-full bg-[#030712] border border-slate-800 rounded-lg " +
    "px-4 py-3 text-slate-100 placeholder-slate-500 " +
    "focus:outline-none focus:border-teal-500 " +
    "focus:ring-1 focus:ring-teal-500";

  const estiloLabel =
    "block text-xs font-semibold text-slate-300 " +
    "uppercase tracking-wider mb-2";

  return (
    <form
      onSubmit={handlerSalvar}
      className="w-full max-w-2xl mx-auto bg-[#0b1120] border border-slate-800 rounded-xl p-8 shadow-2xl"
    >
      <div className="space-y-6">

        <div>
          <label className={estiloLabel}>
            Nome Completo:
          </label>

          <input
            name="nome"
            type="text"
            value={hospede.nome}
            required
            onChange={(e) =>
              handlerChange("nome", e.target.value)
            }
            placeholder="Digite o nome do hóspede"
            className={estiloInput}
          />
        </div>

        <div>
          <label className={estiloLabel}>
            CPF:
          </label>

          <input
            name="cpf"
            type="text"
            value={hospede.cpf}
            required
            onChange={(e) =>
              handlerChange("cpf", e.target.value)
            }
            placeholder="000.000.000-00"
            className={estiloInput}
          />
        </div>

        <div>
          <label className={estiloLabel}>
            E-mail:
          </label>

          <input
            name="email"
            type="email"
            value={hospede.email}
            required
            onChange={(e) =>
              handlerChange("email", e.target.value)
            }
            placeholder="exemplo@email.com"
            className={estiloInput}
          />
        </div>

        <div>
          <label className={estiloLabel}>
            Telefone:
          </label>

          <input
            name="telefone"
            type="text"
            value={hospede.telefone}
            required
            onChange={(e) =>
              handlerChange("telefone", e.target.value)
            }
            placeholder="(00) 00000-0000"
            className={estiloInput}
          />
        </div>

        {/* CONSULTA DE CEP */}

        <div className="border-t border-slate-800 pt-6">
          <h2 className="text-lg font-semibold text-teal-400 mb-4">
            Consultar endereço pelo CEP
          </h2>

          <label className={estiloLabel}>
            CEP:
          </label>

          <div className="flex flex-col sm:flex-row gap-3">

            <input
              type="text"
              value={cep}
              maxLength={9}
              onChange={(e) => {
                setCep(e.target.value);
                setEndereco(null);
                setErroCep("");
              }}
              placeholder="00000-000"
              className={estiloInput}
            />

            <button
              type="button"
              onClick={handlerBuscarCep}
              disabled={buscandoCep}
              className="px-5 py-3 rounded-lg bg-teal-500 text-slate-950 font-semibold hover:bg-teal-400 disabled:opacity-50"
            >
              {buscandoCep ? "Buscando..." : "Buscar CEP"}
            </button>

          </div>

          {erroCep && (
            <p className="text-red-400 text-sm mt-3">
              {erroCep}
            </p>
          )}

          {endereco && (
            <div className="mt-5 space-y-4">

              <div>
                <label className={estiloLabel}>
                  Logradouro:
                </label>

                <input
                  value={endereco.logradouro}
                  readOnly
                  className={estiloInput}
                />
              </div>

              <div>
                <label className={estiloLabel}>
                  Bairro:
                </label>

                <input
                  value={endereco.bairro}
                  readOnly
                  className={estiloInput}
                />
              </div>

              <div>
                <label className={estiloLabel}>
                  Cidade:
                </label>

                <input
                  value={endereco.cidade}
                  readOnly
                  className={estiloInput}
                />
              </div>

              <div>
                <label className={estiloLabel}>
                  Estado (UF):
                </label>

                <input
                  value={endereco.uf}
                  readOnly
                  className={estiloInput}
                />
              </div>

              <p className="text-sm text-teal-400">
                Endereço encontrado com sucesso!
              </p>

            </div>
          )}

          <p className="text-xs text-slate-500 mt-3">
            Consulta realizada pelo backend do InnKeeper
            utilizando a API ViaCEP.
          </p>
        </div>

        <div className="flex items-center justify-end gap-4 pt-4 border-t border-slate-800">

          <Link
            href="/hospede"
            className="px-5 py-2.5 text-sm font-medium text-slate-400 hover:text-slate-200"
          >
            Cancelar
          </Link>

          <button
            type="submit"
            className="px-6 py-2.5 text-sm font-semibold text-slate-950 bg-teal-400 hover:bg-teal-300 rounded-lg"
          >
            Salvar
          </button>

        </div>
      </div>
    </form>
  );
}
