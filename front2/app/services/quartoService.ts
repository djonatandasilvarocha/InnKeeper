import api from "./api";
import { Quarto } from "../types/quarto";

export async function buscarListaQuartos(): Promise<Quarto[]> {
    const dados = await api.get<Quarto[]>("/quarto");

    if (dados.status === 200) {
        return dados.data;
    }

    return [];
}

export async function consultaQuartoPorId(id: number): Promise<Quarto> {
    const dados = await api.get<Quarto>(`/quarto/${id}`);
    return dados.data;
}

export async function salvarQuarto(quarto: Quarto): Promise<Quarto> {
    const dados = await api.post<Quarto>("/quarto", quarto);
    return dados.data;
}

export async function atualizarQuarto(quarto: Quarto): Promise<Quarto> {
    const dados = await api.put<Quarto>(`/quarto/${quarto.id}`, quarto);
    return dados.data;
}

export async function deletarQuarto(id: number): Promise<number> {
    const dados = await api.delete(`/quarto/${id}`);
    return dados.status;
}