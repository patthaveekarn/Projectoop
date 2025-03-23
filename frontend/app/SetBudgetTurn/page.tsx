// src/components/SetBudgetTurn.tsx
"use client";

import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setConfig } from "../stores/slices/configSlice";
import { RootState } from "../stores/store";
import "../styles/SetBudgetTurn.css";
import {useRouter} from "next/navigation";

const SetBudgetTurn: React.FC = () => {
  const dispatch = useDispatch();
  const selectedMinions = useSelector((state: RootState) => state.minion.selectedMinions);
  const [budget, setBudget] = useState(10);  // ค่าเริ่มต้น
  const [turns, setTurns] = useState(10);  // ค่าเริ่มต้น
    const router = useRouter();

  const handleNext = () => {
    // บันทึกข้อมูล Budget และ Turn ลง Redux
    dispatch(setConfig({turn: 0, budget }));

    // ไปยังหน้า GamePlay
    console.log("Final Config:", { budget, turns });
    router.push("/Gameplay");
  };

  return (
      <div className="set-budget-turn-container">
        <h1>Set Your Budget and Turns</h1>

        <h3>Minions:</h3>
        <div className="minion-list">
          {selectedMinions.map((minion) => (
              <div key={minion.id} className="minion-card">
                <img src={minion.image} alt={minion.name} className="minion-image" />
                <h3>{minion.name}</h3>
                <p>Defense: {minion.defense}</p>
                <p>Strategy: {minion.strategy}</p>
              </div>
          ))}
        </div>

        <div className="budget-turn-inputs">
          <label>
            Budget:
            <input
                type="number"
                value={budget}
                onChange={(e) => setBudget(Number(e.target.value))}
                min="1"
                required
            />
          </label>

          <label>
            Turns:
            <input
                type="number"
                value={turns}
                onChange={(e) => setTurns(Number(e.target.value))}
                min="1"
                required
            />
          </label>
        </div>

        <button onClick={handleNext}>Next</button>
      </div>
  );
};

export default SetBudgetTurn;
