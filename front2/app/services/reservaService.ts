
import api from "./api";
import { Reserva } from "../types/reserva";

export async function buscarListaReservas(): Promise<Reserva[]> {
  const resposta = await api.get<Reserva[]>("/reserva");
  return resposta.data;
}

export async function consultaReservaPorId(
  id: number
): Promise<Reserva> {
  const resposta = await api.get<Reserva>(`/reserva/${id}`);
  return resposta.data;
}

export async function salvarReserva(
  reserva: Reserva
): Promise<Reserva> {
  const resposta = await api.post<Reserva>("/reserva", {
    hospedeId: reserva.hospede?.id,
    quartoId: reserva.quarto?.id,
    dataEntrada: reserva.dataEntrada,
    dataSaida: reserva.dataSaida
  });

  return resposta.data;
}

export async function atualizarReserva(
  reserva: Reserva
): Promise<Reserva> {
  const resposta = await api.put<Reserva>(
    `/reserva/${reserva.id}`,
    {
      hospedeId: reserva.hospede?.id,
      quartoId: reserva.quarto?.id,
      dataEntrada: reserva.dataEntrada,
      dataSaida: reserva.dataSaida,
      status: reserva.status
    }
  );

  return resposta.data;
}

export async function deletarReserva(
  id: number
): Promise<number> {
  const resposta = await api.delete(`/reserva/${id}`);
  return resposta.status;
}

export async function checkoutReserva(
  id: number
): Promise<number> {
  const resposta = await api.post(
    `/reserva/checkout/${id}`
  );

  return resposta.status;
}
