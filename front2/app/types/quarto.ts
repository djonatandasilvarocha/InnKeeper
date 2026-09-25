export class Quarto {
  constructor(
    public id: number | null,
    public numero: string,
    public tipo: string,
    public capacidade: number,
    public diaria: number,
    public status: string
  ) {}
}

export interface QuartoFormProps {
  quartoExistente?: Quarto;
}