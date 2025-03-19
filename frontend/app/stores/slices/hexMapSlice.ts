import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface Hex {
  row: number;
  col: number;
  selected: boolean;
  owner: string | null;
}

interface HexMapState {
  grid: Hex[][];
}

const GRID_SIZE = 8;

const initialState: HexMapState = {
  grid: Array.from({ length: GRID_SIZE }, (_, row) =>
    Array.from({ length: GRID_SIZE }, (_, col) => ({
      row,
      col,
      selected: false,
      owner: null,
    }))
  ),
};

const hexMapSlice = createSlice({
  name: "hexMap",
  initialState,
  reducers: {
    initializeGrid: (state) => {
      state.grid = initialState.grid;
    },
    selectHex: (state, action: PayloadAction<{ row: number; col: number }>) => {
      const { row, col } = action.payload;
      state.grid[row][col].selected = !state.grid[row][col].selected;
    },
    buyHex: (state, action: PayloadAction<{ row: number; col: number; owner: string }>) => {
      const { row, col, owner } = action.payload;
      state.grid[row][col].owner = owner;
    },
  },
});

export const { initializeGrid, selectHex, buyHex } = hexMapSlice.actions;
export default hexMapSlice.reducer;
