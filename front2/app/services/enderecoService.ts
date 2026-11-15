
import api from "./api";

export interface EnderecoResponse {
  cep: string;
  logradouro: string;
  bairro: string;
  cidade: string;
  uf: string;
}

export async function buscarEnderecoPorCep(
  cep: string
): Promise<EnderecoResponse> {

  const response = await api.get<EnderecoResponse>(
    `/endereco/${cep.replace(/\D/g, "")}`
  );

  return response.data;
}
