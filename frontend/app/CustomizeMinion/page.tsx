"use client";

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useDispatch, useSelector } from "react-redux";
import { updateMinion } from "../stores/slices/minionSlice";
import { RootState } from "../stores/store";
import "../styles/CustomizeMinion.css";

const CustomizeMinion: React.FC = () => {
  const router = useRouter();
  const dispatch = useDispatch();
  const selectedMinions = useSelector((state: RootState) => state.minion.selectedMinions);
  const [isValid, setIsValid] = useState(false);

  // ถ้าจำนวนมินเนี่ยนไม่ครบ 3 ตัว จะพาผู้เล่นไปหน้าเลือกมินเนี่ยน
  useEffect(() => {
    if (selectedMinions.length < 3) {
      router.push("/ChooseMinion");
    }
  }, [selectedMinions, router]);

  // ตรวจสอบว่าทุกมินเนี่ยนกรอกข้อมูลครบถ้วน
  useEffect(() => {
    const allValid = selectedMinions.every(
        (m) => m.name.trim() !== "" && m.defense > 0 && m.strategy.trim() !== ""
    );
    setIsValid(allValid);  // อัปเดตสถานะของปุ่ม Next
  }, [selectedMinions]);

  // ฟังก์ชันเพื่ออัปเดตข้อมูลมินเนี่ยน
  const handleUpdateMinion = (id: number, key: keyof typeof selectedMinions[0], value: any) => {
    if (key === "defense" && value < 1) return;  // ตรวจสอบค่าการป้องกัน (defense) ไม่ให้น้อยกว่า 1
    dispatch(updateMinion({ id, key, value })); // อัปเดตใน Redux store
  };

  const handleNext = () => {
    if (!isValid) {
      alert("กรุณากรอกข้อมูลให้ครบทุกช่องก่อนดำเนินการต่อ");
      return;
    }

    // สำหรับ Debug ดูค่าของ selectedMinions ใน Redux store
    console.log("Final Minions Data:", selectedMinions);

    // นำผู้เล่นไปยังหน้า SetBudgetTurn
    router.push("/SetBudgetTurn");
  };

  return (
      <div className="customize-minion-container">
        <h1 className="customize-title">Customize Your Minions</h1>

        <div className="minion-list">
          {selectedMinions.map((minion) => (
              <div key={minion.id} className="minion-card">
                <img src={minion.image} alt={minion.name} className="minion-image" />
                <h3 className="minion-name">{minion.name}</h3>

                <label>
                  Name:
                  <input
                      type="text"
                      value={minion.name}
                      onChange={(e) => handleUpdateMinion(minion.id, "name", e.target.value)}
                      required
                  />
                </label>
                {minion.name.trim() === "" && <span className="error-message">กรุณากรอกชื่อมินเนี่ยน</span>}

                <label>
                  DEF (Defense):
                  <input
                      type="number"
                      value={minion.defense === 0 ? "" : minion.defense}
                      onChange={(e) => handleUpdateMinion(minion.id, "defense", Number(e.target.value) || 0)}
                      required
                      min="1"
                      placeholder="กรอกค่า DEF มากกว่า 0"
                  />
                </label>
                {minion.defense <= 0 && <span className="error-message">คุณต้องใส่ค่า DEF มากกว่า 0</span>}

                <label>
                  Strategy:
                  <textarea
                      value={minion.strategy}
                      onChange={(e) => handleUpdateMinion(minion.id, "strategy", e.target.value)}
                      required
                  />
                </label>
                {minion.strategy.trim() === "" && <span className="error-message">กรุณาใส่ Strategy</span>}
              </div>
          ))}
        </div>

        <div className="button-container">
          <button className="back-button" onClick={() => router.push("/ChooseMinion")}>
            Back
          </button>
          <button className="next-button" onClick={handleNext} disabled={!isValid}>
            Next
          </button>
        </div>
      </div>
  );
};

export default CustomizeMinion;
