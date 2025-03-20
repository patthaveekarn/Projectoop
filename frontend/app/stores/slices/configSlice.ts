import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface ConfigState {
  budget: number;
  turn: number;
}

const initialState: ConfigState = {
  budget: 0,
  turn: 0
};

const configSlice = createSlice({
  name: "config",
  initialState,
  reducers: {
    setConfig: (state, action: PayloadAction<ConfigState>) => {
      state.budget = action.payload.budget;
      state.turn = action.payload.turn;
    }
  }
});

export const { setConfig } = configSlice.actions;
export default configSlice.reducer;
