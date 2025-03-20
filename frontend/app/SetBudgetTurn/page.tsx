"use client";

import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { useDispatch, useSelector } from "react-redux";
import { setConfig } from "../stores/slices/configSlice";
import { RootState } from "../stores/store";
import "../styles/SetBudgetTurn.css";

const SetBudgetTurn: React.FC = () => {
  const router = useRouter();
  const dispatch = useDispatch();
  const selectedMinions = useSelector((state: RootState) => state.minion.selectedMinions);
  const config = useSelector((state: RootState) => state.config);

  // ใช้ State สำหรับ Budget และ Turn (โหลดค่าจาก Redux ถ้ามี)
  const [budget, setBudget] = useState(config.budget || "");
  const [turn, setTurn] = useState(config.turn || "");

  // ถ้าไม่มี Minion เลือกไว้ ให้กลับไปหน้า ChooseMinion
  useEffect(() => {
    if (selectedMinions.length < 3) {
      router.push("/ChooseMinion");
    }
  }, [selectedMinions, router]);

  // ตรวจสอบว่า Budget และ Turn กรอกถูกต้องหรือไม่
  const isValid = () => {
    return Number(budget) >= 1 && Number(turn) >= 1;
  };

  // ตรวจสอบการอัปเดต Redux เมื่อมีการส่งข้อมูล
  useEffect(() => {
    console.log("📌 Updated Redux Config:", config); // ตรวจสอบค่า config หลังจากการอัปเดต
  }, [config]);

  const handleNext = () => {
    if (isValid()) {
      dispatch(setConfig({ budget: Number(budget), turn: Number(turn) }));
      console.log("✅ Dispatch setConfig:", { budget: Number(budget), turn: Number(turn) });

      // หลังจากส่งค่าไปยัง Redux แล้ว, รอการอัปเดตก่อนเปลี่ยนหน้า
      router.push("/Gameplay");
    } else {
      alert("กรุณากรอกค่า Budget และ Turn ให้มากกว่า 0");
    }
  };

  return (
      <div className="set-budget-turn-container">
        <h1 className="set-budget-turn-title">Set Budget and Turn</h1>

        <div className="budget-turn-inputs">
          <label>
            Budget:
            <input
                type="number"
                value={budget}
                onChange={(e) => setBudget(e.target.value)}
                placeholder="Enter Budget (1+)"
                min="1"
            />
            {Number(budget) < 1 && <span className="error-message">Budget ต้องมากกว่า 0</span>}
          </label>

          <label>
            Turn:
            <input
                type="number"
                value={turn}
                onChange={(e) => setTurn(e.target.value)}
                placeholder="Enter Turn (1+)"
                min="1"
            />
            {Number(turn) < 1 && <span className="error-message">Turn ต้องมากกว่า 0</span>}
          </label>
        </div>

        <div className="button-container">
          <button className="back-button" onClick={() => router.push("/CustomizeMinion")}>
            Back
          </button>
          <button className="next-button" onClick={handleNext} disabled={!isValid()}>
            Next
          </button>
        </div>
      </div>
  );
};

export default SetBudgetTurn;
