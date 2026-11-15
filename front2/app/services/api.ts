
"use client";

import axios from "axios";

import { store } from "@/app/redux/store";
import { logout } from "@/app/redux/slices/authSlice";

const api = axios.create({
    baseURL: "http://localhost:8080"
});

// INTERCEPTOR DE REQUISIÇÃO
api.interceptors.request.use(
    (config) => {

        // Busca o token diretamente da store do Redux
        const token = store.getState().auth.token;

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            delete config.headers.Authorization;
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// INTERCEPTOR DE RESPOSTA
api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {

        // Quando o backend retorna 401,
        // remove a autenticação da store Redux.
        if (error.response?.status === 401) {

            store.dispatch(logout());

            // O layout protegido identifica que
            // o token foi removido e redireciona
            // automaticamente para /login.
        }

        return Promise.reject(error);
    }
);

export default api;
