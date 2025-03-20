import { configureStore } from "@reduxjs/toolkit";
import gameModeReducer from "./slices/gameModeSlice";
import minionReducer from "./slices/minionSlice";
import configReducer from "./slices/configSlice";
import hexMapReducer from "./slices/hexMapSlice"; 
import gameReducer from "./slices/gameSlice";  // ✅ เพิ่ม gameReducer

export const store = configureStore({
  reducer: {
    gameMode: gameModeReducer,
    minion: minionReducer,
    config: configReducer,
    hexMap: hexMapReducer, 
    game: gameReducer,  // ✅ เพิ่ม gameReducer
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export default store;
