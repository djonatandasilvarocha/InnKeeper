
import { configureStore } from "@reduxjs/toolkit";

import authReducer from "./slices/authSlice";
import reservaReducer from "./slices/reservaSlice";

export const store = configureStore({
    reducer: {
        auth: authReducer,
        reserva: reservaReducer
    }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
