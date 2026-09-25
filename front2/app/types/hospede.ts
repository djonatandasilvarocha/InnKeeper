export class Hospede {
  constructor(
    public id: number | null,
    public nome: string,
    public cpf: string,
    public email: string,
    public telefone: string
  ) {}
}

export interface HospedeFormProps {
  hospedeExistente?: Hospede;
}