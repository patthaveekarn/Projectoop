import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface GameModeState {
  mode: string;
  players: string[];
  readyPlayers: string[]; // เพิ่ม readyPlayers เพื่อเก็บผู้เล่นที่ Ready
}

const initialState: GameModeState = {
  mode: "DUEL", // ✅ ต้องมีค่าเริ่มต้น
  players: [],
  readyPlayers: [], // ค่าเริ่มต้นของ readyPlayers
};

const gameModeSlice = createSlice({
  name: "gameMode",
  initialState,
  reducers: {
    setGameMode: (state, action: PayloadAction<string>) => {
      console.log("🎮 Setting Game Mode:", action.payload);
      state.mode = action.payload;
    },
    setPlayers: (state, action: PayloadAction<string[]>) => {
      console.log("👥 Updating Players:", action.payload);
      state.players = action.payload;
    },
    setReadyPlayers: (state, action: PayloadAction<string[]>) => {
      console.log("👥 Ready Players Updated:", action.payload);
      state.readyPlayers = action.payload;
    },
  },
});

export const { setGameMode, setPlayers, setReadyPlayers } = gameModeSlice.actions;
export default gameModeSlice.reducer;
