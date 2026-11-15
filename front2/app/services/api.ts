
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
        const token = store.getState().auth.token;

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
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
        if (error.response?.status === 401) {
            store.dispatch(logout());

            if (
                typeof window !== "undefined" &&
                window.location.pathname !== "/login"
            ) {
                window.location.href = "/login";
            }
        }

        return Promise.reject(error);
    }
);

export default api;
