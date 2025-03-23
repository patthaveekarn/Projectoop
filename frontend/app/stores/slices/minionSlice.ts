// src/stores/slices/minionSlice.ts
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface Minion {
  id: number;
  name: string;
  image: string;
  defense: number;
  strategy: string;
  owner: string;
}

interface MinionState {
  selectedMinions: Minion[];
  opponentMinions: Minion[]; // เก็บมินเนี่ยนของอีกฝั่ง
  readyPlayers: string[]; // เก็บว่าใครกด Ready แล้วบ้าง
}

const initialState: MinionState = {
  selectedMinions: [],
  opponentMinions: [],
  readyPlayers: [],
};

const minionSlice = createSlice({
  name: "minion",
  initialState,
  reducers: {
    addMinion: (state, action: PayloadAction<Minion>) => {
      if (state.selectedMinions.length < 3) {
        state.selectedMinions.push(action.payload);
      }
    },
    removeMinion: (state, action: PayloadAction<number>) => {
      state.selectedMinions = state.selectedMinions.filter((m) => m.id !== action.payload);
    },
    updateMinion: (state, action: PayloadAction<{ id: number; key: keyof Minion; value: any }>) => {
      const { id, key, value } = action.payload;
      const minion = state.selectedMinions.find((m) => m.id === id);
      if (minion) {
        (minion as any)[key] = value; // แก้ไขค่าใน Redux State
      }
    },
    resetMinions: (state) => {
      state.selectedMinions = [];
      state.opponentMinions = [];
      state.readyPlayers = [];
    },
    setOpponentMinions: (state, action: PayloadAction<Minion[]>) => {
      state.opponentMinions = action.payload;
    },
    setReadyPlayers: (state, action: PayloadAction<string[]>) => {
      state.readyPlayers = action.payload;
    },
  },
});

export const {
  addMinion,
  removeMinion,
  updateMinion,
  resetMinions,
  setOpponentMinions,
  setReadyPlayers
} = minionSlice.actions;

export default minionSlice.reducer;
