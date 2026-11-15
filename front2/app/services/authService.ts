
import api from "./api";
import { LoginResponse } from "@/app/types/auth";

export async function loginBack(
    email: string,
    senha: string
): Promise<LoginResponse> {

    const resposta = await api.post<LoginResponse>(
        "/auth/login",
        {
            email,
            senha
        }
    );

    return resposta.data;
}
