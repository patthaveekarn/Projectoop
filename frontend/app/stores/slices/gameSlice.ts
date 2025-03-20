import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface GameState {
  currentTurn: string;
  players: string[];
  gold: Record<string, number>;
}

const initialState: GameState = {
  currentTurn: "",
  players: [],
  gold: {},
};

const gameSlice = createSlice({
  name: "game",
  initialState,
  reducers: {
    setPlayers: (state, action: PayloadAction<string[]>) => {
      state.players = action.payload;
      state.currentTurn = action.payload[0];
      state.gold[action.payload[0]] = 10;
      state.gold[action.payload[1]] = 10;
    },
    switchTurn: (state) => {
      const nextPlayer = state.players.find(p => p !== state.currentTurn);
      if (nextPlayer) state.currentTurn = nextPlayer;
    },
    buyMinion: (state, action: PayloadAction<{ player: string }>) => {
      if (state.gold[action.payload.player] >= 3) {
        state.gold[action.payload.player] -= 3;
      }
    },
    buyHex: (state, action: PayloadAction<{ player: string }>) => {
      if (state.gold[action.payload.player] >= 5) {
        state.gold[action.payload.player] -= 5;
      }
    },
  },
});

export const { setPlayers, switchTurn, buyMinion, buyHex } = gameSlice.actions;
export default gameSlice.reducer;
