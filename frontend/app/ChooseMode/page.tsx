"use client";

import React from "react";
import { useRouter } from "next/navigation";
import { useDispatch } from "react-redux";
import { setGameMode } from "../stores/slices/gameModeSlice";  // Redux action
import "../styles/ChooseMode.css";

const ChooseMode: React.FC = () => {
    const router = useRouter();
    const dispatch = useDispatch();

    const handleModeSelect = (mode: "DUEL" | "SINGLE" | "AUTO") => {
        dispatch(setGameMode(mode)); // เซ็ต Redux ให้ gameMode มีค่า
        console.log("🔵 Game Mode Set:", mode); // Debug Redux State

        if (mode === "DUEL") {
            router.push("/WaitingRoom");
        } else if (mode === "SINGLE") {
            router.push("/ChooseMinion");
        } else if (mode === "AUTO") {
            router.push("/Gameplay");
        }
    };

    return (
        <div className="choose-mode-container">
            <h1 className="choose-title">Choose Game Mode</h1>

            <button className="mode-button" onClick={() => handleModeSelect("DUEL")}>
                Duel Mode (Multiplayer)
            </button>

            <button className="mode-button" onClick={() => handleModeSelect("SINGLE")}>
                Single Player (vs AI)
            </button>

            <button className="mode-button" onClick={() => handleModeSelect("AUTO")}>
                Auto Battle (Bot vs Bot)
            </button>

            <button className="back-button" onClick={() => router.push("/")}>
                Back to Home
            </button>
        </div>
    );
};

export default ChooseMode;
