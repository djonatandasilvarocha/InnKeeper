import api from "./api";
import { Reserva } from "../types/reserva";

export async function buscarListaReservas(): Promise<Reserva[]> {
    const dados = await api.get<Reserva[]>("/reserva");

    if (dados.status === 200) {
        return dados.data;
    }

    return [];
}

export async function consultaReservaPorId(id: number): Promise<Reserva> {
    const dados = await api.get<Reserva>(`/reserva/${id}`);
    return dados.data;
}

export async function salvarReserva(reserva: Reserva): Promise<Reserva> {
    const dados = await api.post<Reserva>("/reserva", reserva);
    return dados.data;
}

export async function atualizarReserva(reserva: Reserva): Promise<Reserva> {
    const dados = await api.put<Reserva>(`/reserva/${reserva.id}`, reserva);
    return dados.data;
}

export async function deletarReserva(id: number): Promise<number> {
    const dados = await api.delete(`/reserva/${id}`);
    return dados.status;
}

export async function checkoutReserva(id: number): Promise<number> {
    const dados = await api.patch(`/reserva/${id}/checkout`, {});
    return dados.status;
}