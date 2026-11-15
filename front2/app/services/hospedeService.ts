import api from "./api";
import { Hospede } from "../types/hospede";

export async function buscarListaHospedes(): Promise<Hospede[]> {
    const dados = await api.get<Hospede[]>("/hospede");

    if (dados.status === 200) {
        return dados.data;
    }

    return [];
}

export async function consultaHospedePorId(id: number): Promise<Hospede> {
    const dados = await api.get<Hospede>(`/hospede/${id}`);
    return dados.data;
}

export async function salvarHospede(hospede: Hospede): Promise<Hospede> {
    const dados = await api.post<Hospede>("/hospede", hospede);
    return dados.data;
}

// PUT: atualiza todos os dados
export async function atualizarHospede(hospede: Hospede): Promise<Hospede> {
    const dados = await api.put<Hospede>(`/hospede/${hospede.id}`, hospede);
    return dados.data;
}

// PATCH: atualiza apenas os campos informados
export async function atualizarParcialHospede(id: number, campos: Partial<Hospede>): Promise<Hospede> {
    const dados = await api.patch<Hospede>(`/hospede/${id}`, campos);
    return dados.data;
}

export async function deletarHospede(id: number): Promise<number> {
    const dados = await api.delete(`/hospede/${id}`);
    return dados.status;
}