import { Hospede } from "./hospede";
import { Quarto } from "./quarto";

export type EnumStatusReserva = "PENDENTE" | "CONFIRMADA" | "FINALIZADA" | "CANCELADA";

export class Reserva {
  constructor(
    public id: number | null,
    public hospede: Hospede | null,
    public quarto: Quarto | null,
    public dataEntrada: string,
    public dataSaida: string,
    public valorTotal: number | null,
    public status: EnumStatusReserva
  ) {}
}

export interface ReservaFormProps {
  reservaExistente?: Reserva;
}