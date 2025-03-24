import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// Define the initial state of the grid
const initialState = {
  grid: Array(8).fill(Array(8).fill({ owner: null, occupied: false, minion: null })),
};

// Define the slice for managing the hex map
const hexMapSlice = createSlice({
  name: "hexMap",
  initialState,
  reducers: {
    initializeGrid(state) {
      // Initialize the grid state
      state.grid = Array(8).fill(Array(8).fill({ owner: null, occupied: false, minion: null }));
    },
    selectHex(state, action: PayloadAction<{ row: number; col: number }>) {
      const { row, col } = action.payload;
      state.grid[row][col].selected = !state.grid[row][col].selected;
    },
    buyHex(state, action: PayloadAction<{ row: number; col: number; owner: string }>) {
      const { row, col, owner } = action.payload;
      state.grid[row][col].owner = owner;
      state.grid[row][col].occupied = true;
    },
    placeMinion(state, action: PayloadAction<{ player: string; row: number; col: number }>) {
      const { player, row, col } = action.payload;
      state.grid[row][col].minion = { name: `${player}'s Minion`, owner: player };
      state.grid[row][col].occupied = true; // Mark hex as occupied after placing minion
    },
  },
});

export const { initializeGrid, selectHex, buyHex, placeMinion } = hexMapSlice.actions;
export default hexMapSlice.reducer;
