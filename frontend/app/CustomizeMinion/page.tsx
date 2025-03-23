"use client";

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useDispatch, useSelector } from "react-redux";
import { addMinion, updateMinion } from "../stores/slices/minionSlice";
import { RootState } from "../stores/store";
import "../styles/CustomizeMinion.css";


const CustomizeMinion: React.FC = () => {
  const router = useRouter();
  const dispatch = useDispatch();
  const selectedMinions = useSelector((state: RootState) => state.minion.selectedMinions);
  const [isValid, setIsValid] = useState(false);

  useEffect(() => {
    if (selectedMinions.length < 3) {
      router.push("/ChooseMinion");
    }
  }, [selectedMinions, router]);

  useEffect(() => {
    const allValid = selectedMinions.every(
        (m) => m.name.trim() !== "" && m.defense > 0 && m.strategy.trim() !== ""
    );
    setIsValid(allValid); // อัปเดตสถานะของปุ่ม Next
  }, [selectedMinions]);

  const handleUpdateMinion = (id: number, key: keyof typeof selectedMinions[0], value: any) => {
    dispatch(updateMinion({ id, key, value }));  // อัปเดตใน Redux
  };

  const handleNext = () => {
    if (!isValid) {
      alert("กรุณากรอกข้อมูลให้ครบทุกช่องก่อนดำเนินการต่อ");
      return;
    }

    router.push("/SetBudgetTurn");
  };
  const [selectedOption, setSelectedOption] = useState('');

  const handleSelectChange = (e) => {
    setSelectedOption(e.target.value);
    console.log('Selected:', e.target.value);
    // Add your logic here to handle selection
  };

  return (
      <div className="customize-minion-container">
        <h1 className="customize-title">Customize Your Minions</h1>

        <div className="minion-list">
          {selectedMinions.map((minion) => (
              <div key={minion.id} className="minion-card">
                <img src={minion.image} alt={minion.name} className="minion-image"/>
                <h3 className="minion-name">{minion.name}</h3>

                <label>
                  Name:
                  <input
                      type="text"
                      value={minion.name}
                      onChange={(e) => handleUpdateMinion(minion.id, "name", e.target.value)}
                  />
                </label>

                <label>
                  DEF (Defense):
                  <input
                      type="number"
                      value={minion.defense || ""}
                      onChange={(e) => handleUpdateMinion(minion.id, "defense", Number(e.target.value))}
                      min="1"
                  />
                </label>

                <label>
                  Strategy:
                  <textarea
                      value={minion.strategy}
                      onChange={(e) => handleUpdateMinion(minion.id, "strategy", e.target.value)}
                  />
                </label>
              </div>
          ))}
        </div>

        <div className="mb-4">
          <label
              htmlFor="dropdown"
              className="block text-sm font-medium text-gray-700 mb-1"
          >
            Select an option
          </label>

          <select
              id="dropdown"
              value={selectedOption}
              onChange={handleSelectChange}
              className="block w-full rounded-md border border-gray-300 px-3 py-2 text-gray-700 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-1 focus:ring-indigo-500"
          >
            <option value="" disabled>Choose an option</option>
            <option value="option1">Option 1</option>
            <option value="option2">Option 2</option>
            <option value="option3">Option 3</option>
            <option value="option4">Option 4</option>
          </select>
        </div>
        <div className="button-container">
          <button className="back-button" onClick={() => router.push("/ChooseMinion")}>Back</button>
          <button className="next-button" onClick={handleNext} disabled={!isValid}>Next</button>
        </div>
      </div>
  );
};

export default CustomizeMinion;
