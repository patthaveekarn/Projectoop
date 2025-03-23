// src/stores/store.ts
import { configureStore } from "@reduxjs/toolkit";
import gameModeReducer from "./slices/gameModeSlice";
import minionReducer from "./slices/minionSlice";
import configReducer from "./slices/configSlice";
import hexMapReducer from "./slices/hexMapSlice";
import gameReducer from "./slices/gameSlice";
import webSocketReducer from './slices/WebSocketSlice'; // ✅ เพิ่ม webSocketReducer

// กำหนด store โดยรวม reducers ที่เกี่ยวข้องกับสถานะต่าง ๆ
export const store = configureStore({
  reducer: {
    gameMode: gameModeReducer,
    minion: minionReducer,
    config: configReducer,
    hexMap: hexMapReducer,
    game: gameReducer,
    webSocket: webSocketReducer, // ✅ เพิ่ม webSocketReducer
  },
});

// สร้าง RootState และ AppDispatch เพื่อให้การใช้ในคอมโพเนนต์ต่าง ๆ ง่ายขึ้น
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
