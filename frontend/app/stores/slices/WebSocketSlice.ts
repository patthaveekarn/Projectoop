// src/stores/slices/WebSocketSlice.ts
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface WebSocketState {
    isConnected: boolean;
    socketId: string;
    messages: string[];
}

const initialState: WebSocketState = {
    isConnected: false,
    socketId: "",
    messages: [],
};

const webSocketSlice = createSlice({
    name: "webSocket",
    initialState,
    reducers: {
        initializeWebSocket: (state) => {
            state.isConnected = true;
            state.socketId = "dummy-socket-id"; // แทนที่ด้วย socket ID จริง
        },
        sendMessage: (state, action: PayloadAction<string>) => {
            state.messages.push(action.payload);
        },
        receiveMessage: (state, action: PayloadAction<string>) => {
            state.messages.push(action.payload);
        },
        setSocketConnected: (state, action: PayloadAction<boolean>) => {
            state.isConnected = action.payload;
        },
        setSocketId: (state, action: PayloadAction<string>) => {
            state.socketId = action.payload;
        },
    },
});

export const { initializeWebSocket, sendMessage, receiveMessage, setSocketConnected, setSocketId } = webSocketSlice.actions;
export default webSocketSlice.reducer;
