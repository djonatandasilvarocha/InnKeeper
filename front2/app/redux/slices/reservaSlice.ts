
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Reserva } from "@/app/types/reserva";

interface ReservaState {
    reservas: Reserva[];
}

const initialState: ReservaState = {
    reservas: []
};

const reservaSlice = createSlice({
    name: "reserva",
    initialState,
    reducers: {
        setReservas: (state, action: PayloadAction<Reserva[]>) => {
            state.reservas = action.payload;
        },

        adicionarReserva: (state, action: PayloadAction<Reserva>) => {
            state.reservas.push(action.payload);
        },

        atualizarReserva: (state, action: PayloadAction<Reserva>) => {
            const id = action.payload.id;

            if (id === null) return;

            const index = state.reservas.findIndex(
                (reserva) => reserva.id === id
            );

            if (index !== -1) {
                state.reservas[index] = action.payload;
            }
        },

        removerReserva: (state, action: PayloadAction<number>) => {
            state.reservas = state.reservas.filter(
                (reserva) => reserva.id !== action.payload
            );
        },

        limparReservas: (state) => {
            state.reservas = [];
        }
    }
});

export const {
    setReservas,
    adicionarReserva,
    atualizarReserva,
    removerReserva,
    limparReservas
} = reservaSlice.actions;

export default reservaSlice.reducer;
