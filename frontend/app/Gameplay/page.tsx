// frontend/app/Gameplay/page.tsx
"use client";

import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../stores/store";
import { socket } from "../libs/socket";

import { initializeGrid, selectHex, buyHex } from "../stores/slices/hexMapSlice";
import { switchTurn, buyMinion } from "../stores/slices/gameSlice";
import "../styles/HexGrid.css";

const GamePlay: React.FC = () => {
  const dispatch = useDispatch();
  const grid = useSelector((state: RootState) => state.hexMap.grid);
  const currentTurn = useSelector((state: RootState) => state.game.currentTurn);
  const players = useSelector((state: RootState) => state.game.players);
  const gold = useSelector((state: RootState) => state.game.gold);
  const selectedMinions = useSelector((state: RootState) => state.minion.selectedMinions);

  const [playerAction, setPlayerAction] = useState("");

  // Connect to WebSocket when component mounts
  useEffect(() => {
    dispatch(initializeGrid());

    // Listen for events from backend
    socket.on("update_game_state", (data) => {
      dispatch(switchTurn(data.currentTurn));
    });

    socket.on("update_hex", (data) => {
      dispatch(buyHex(data));
    });

    socket.on("minion_updated", (data) => {
      console.log("Minion updated:", data);
    });

    return () => {
      // Remove event listeners when component unmounts
      socket.off("update_game_state");
      socket.off("update_hex");
      socket.off("minion_updated");
    };
  }, [dispatch]);

  // Function to handle hex clicks
  const handleHexClick = (row: number, col: number) => {
    if (currentTurn === socket.id) {
      dispatch(selectHex({ row, col }));
    }
  };

  // Function to buy hex
  const handleBuyHex = (row: number, col: number) => {
    if (gold[currentTurn] >= 5) {
      const hexData = { row, col, owner: currentTurn };
      dispatch(buyHex(hexData));
      socket.emit("buy_hex", hexData);
    } else {
      alert("คุณไม่มี Gold เพียงพอเพื่อซื้อ Hex");
    }
  };

  // Function to buy minion
  const handleBuyMinion = () => {
    if (gold[currentTurn] >= 3) {
      dispatch(buyMinion({ player: currentTurn }));
      socket.emit("buy_minion", { player: currentTurn });
    } else {
      alert("คุณไม่มี Gold เพียงพอเพื่อซื้อ Minion");
    }
  };

  // Function to end turn
  const handleEndTurn = () => {
    const nextPlayer = currentTurn === players[0] ? players[1] : players[0];
    dispatch(switchTurn(nextPlayer));
    socket.emit("end_turn", { nextTurn: nextPlayer });
  };

  return (
      <div className="gameplay-container">
        {/* Player 1 info */}
        <div className={`player-info player1 ${currentTurn === players[0] ? "active" : ""}`}>
          <h2>Player 1</h2>
          <p>Gold: {gold[players[0]]}</p>
          <button className="buy-minion-button" onClick={handleBuyMinion} disabled={currentTurn !== players[0]}>
            Buy Minion
          </button>
          <button className="buy-hex-button" onClick={() => handleBuyHex(0, 0)} disabled={currentTurn !== players[0]}>
            Buy Hex
          </button>
          <h3>Minions</h3>
          <ul>
            {selectedMinions
                .filter((minion) => minion.owner === players[0])
                .map((minion, index) => (
                    <li key={index}>{minion.name}</li>
                ))}
          </ul>
        </div>

        {/* Hex Grid */}
        <div className="hex-grid">
          {grid.map((row, rowIndex) =>
              row.map((hex, colIndex) => (
                  <div
                      key={`${rowIndex}-${colIndex}`}
                      className={`hex ${hex.selected ? "selected" : ""} ${colIndex % 2 === 0 ? "even-col" : "odd-col"}`}
                      onClick={() => handleHexClick(rowIndex, colIndex)}
                  >
                    <span className="hex-text">{`(${rowIndex + 1},${colIndex + 1})`}</span>
                  </div>
              ))
          )}
        </div>

        {/* Player 2 info */}
        <div className={`player-info player2 ${currentTurn === players[1] ? "active" : ""}`}>
          <h2>Player 2</h2>
          <p>Gold: {gold[players[1]]}</p>
          <button className="buy-minion-button" onClick={handleBuyMinion} disabled={currentTurn !== players[1]}>
            Buy Minion
          </button>
          <button className="buy-hex-button" onClick={() => handleBuyHex(0, 0)} disabled={currentTurn !== players[1]}>
            Buy Hex
          </button>
          <h3>Minions</h3>
          <ul>
            {selectedMinions
                .filter((minion) => minion.owner === players[1])
                .map((minion, index) => (
                    <li key={index}>{minion.name}</li>
                ))}
          </ul>
        </div>

        {/* End turn button */}
        <div className="game-buttons">
          <button className="end-turn-button" onClick={handleEndTurn} disabled={socket.id !== currentTurn}>
            End Turn
          </button>
        </div>
      </div>
  );
};

export default GamePlay;