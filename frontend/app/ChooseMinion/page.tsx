// frontend/app/ChooseMinion/page.tsx
"use client";

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useDispatch, useSelector } from "react-redux";
import { addMinion, removeMinion, resetMinions, setReadyPlayers } from "../stores/slices/minionSlice";
import { RootState } from "../stores/store";
import { socket } from "../libs/socket";
import "../styles/ChooseMinion.css";

const minionList = [
  { id: 1, name: "MN1", image: "/images/minions/mn1.jpg" },
  { id: 2, name: "MN2", image: "/images/minions/mn2.jpg" },
  { id: 3, name: "MN3", image: "/images/minions/mn3.jpg" },
  { id: 4, name: "MN4", image: "/images/minions/mn4.jpg" },
  { id: 5, name: "MN5", image: "/images/minions/mn5.jpg" },
];

const ChooseMinion: React.FC = () => {
  const router = useRouter();
  const dispatch = useDispatch();
  const selectedMinions = useSelector((state: RootState) => state.minion.selectedMinions);
  const readyPlayers = useSelector((state: RootState) => state.minion.readyPlayers);
  const gameMode = useSelector((state: RootState) => state.gameMode.mode);
  const [loading, setLoading] = useState(true);
  const [playerSelections, setPlayerSelections] = useState<Record<string, number[]>>({});

  // Check valid game mode
  useEffect(() => {
    console.log("🔍 Checking gameMode:", gameMode);
    if (gameMode === undefined) return;
    if (!["DUEL", "SINGLE", "AUTO"].includes(gameMode)) {
      console.log("⛔ Redirecting to ChooseMode...");
      router.push("/ChooseMode");
    } else {
      setLoading(false);
    }
  }, [gameMode, router]);

  useEffect(() => {
    console.log("Ready Players:", readyPlayers);
  }, [readyPlayers]);

  useEffect(() => {
    if (loading) return;

    dispatch(resetMinions());
    sessionStorage.removeItem("selectedMinions");

    // Only use WebSocket in DUEL mode
    if (gameMode === "DUEL") {
      socket.emit("join_room", gameMode);

      socket.on("update_selections", (selections) => {
        setPlayerSelections(selections);
      });

      socket.on("player_ready", (readyList) => {
        dispatch(setReadyPlayers(readyList));
      });

      socket.on("selection_complete", () => {
        router.push("/CustomizeMinion");
      });

      return () => {
        socket.off("update_selections");
        socket.off("player_ready");
        socket.off("selection_complete");
      };
    }
  }, [router, gameMode, dispatch, loading]);

  const handleSelectMinion = (minion: any) => {
    const isSelected = selectedMinions.find((m) => m.id === minion.id);
    if (isSelected) {
      dispatch(removeMinion(minion.id));
      if (gameMode === "DUEL") {
        socket.emit("select_minion", { gameMode, playerId: socket.id, minionId: minion.id, action: "remove" });
      }
    } else if (selectedMinions.length < 3) {
      dispatch(addMinion({ ...minion, name: "", defense: 1, strategy: "" }));
      if (gameMode === "DUEL") {
        socket.emit("select_minion", { gameMode, playerId: socket.id, minionId: minion.id, action: "add" });
      }
    }
  };

  const handleReady = () => {
    console.log("Selected Minions:", selectedMinions);
    if (selectedMinions.length === 3) {
      console.log("Ready to proceed with selection.");
      if (gameMode === "DUEL") {
        socket.emit("confirm_selection", { gameMode, playerId: socket.id });
      } else {
        router.push("/CustomizeMinion");
      }
    } else {
      alert("คุณต้องเลือกมินเนี่ยนครบ 3 ตัวก่อนกด Ready!");
    }
  };

  if (loading) return <p>⏳ กำลังโหลดข้อมูล...</p>;

  return (
      <div className="choose-minion-container">
        <h1 className="choose-title">Choose Your Minions</h1>

        <div className="minion-list">
          {minionList.map((minion) => (
              <div
                  key={minion.id}
                  className={`minion-card ${
                      selectedMinions.find((m) => m.id === minion.id) ? "selected" : ""
                  } ${gameMode === "DUEL" && Object.values(playerSelections).flat().includes(minion.id) ? "occupied" : ""}`}
                  onClick={() => handleSelectMinion(minion)}
              >
                <img src={minion.image} alt={minion.name} className="minion-image" />
                <h3 className="minion-name">{minion.name}</h3>
              </div>
          ))}
        </div>

        <button
            className="ready-button"
            onClick={handleReady}
            disabled={gameMode === "DUEL" && (readyPlayers?.includes(socket.id) ?? false)}
        >
          {gameMode === "DUEL" && readyPlayers?.includes(socket.id) ? "✅ Ready" : "Ready"}
        </button>

        {/* Next button only available in SINGLE and AUTO modes */}
        {gameMode !== "DUEL" && (
            <button className="next-button" onClick={() => router.push("/CustomizeMinion")}>
              Next
            </button>
        )}
      </div>
  );
};

export default ChooseMinion;