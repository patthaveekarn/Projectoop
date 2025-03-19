"use client";

import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../stores/store";
import { initializeGrid, selectHex, buyHex } from "../stores/slices/hexMapSlice";
import { switchTurn, buyMinion } from "../stores/slices/gameSlice";
import { socket } from "../libs/socket"; // นำเข้า socket ที่ใช้เชื่อมต่อ
import "../styles/HexGrid.css";

const GamePlay: React.FC = () => {
  const dispatch = useDispatch();
  const grid = useSelector((state: RootState) => state.hexMap.grid);
  const currentTurn = useSelector((state: RootState) => state.game.currentTurn);
  const players = useSelector((state: RootState) => state.game.players);
  const gold = useSelector((state: RootState) => state.game.gold);
  const selectedMinions = useSelector((state: RootState) => state.minion.selectedMinions);

  // เชื่อมต่อกับ WebSocket เมื่อ component ถูก mount
  useEffect(() => {
    dispatch(initializeGrid());

    // ฟังเหตุการณ์จาก backend
    socket.on("update_game_state", (data) => {
      dispatch(switchTurn(data.currentTurn)); // อัปเดตเทิร์นของเกม
    });

    socket.on("hex_updated", (data) => {
      dispatch(buyHex(data)); // อัปเดตข้อมูล hex ที่ถูกซื้อ
    });

    return () => {
      // ลบ event listeners เมื่อ component ถูก unmount
      socket.off("update_game_state");
      socket.off("hex_updated");
    };
  }, [dispatch]);

  const handleHexClick = (row: number, col: number) => {
    if (currentTurn === socket.id) {
      dispatch(selectHex({ row, col }));
    }
  };

  const handleBuyHex = (row: number, col: number) => {
    if (gold[currentTurn] >= 5) {
      const hexData = { row, col, owner: currentTurn };
      dispatch(buyHex(hexData));
      socket.emit("buy_hex", hexData); // ส่งข้อมูลการซื้อ hex ไปยัง backend
    }
  };

  const handleBuyMinion = () => {
    if (gold[currentTurn] >= 3) {
      dispatch(buyMinion({ player: currentTurn }));
      socket.emit("buy_minion", { player: currentTurn }); // ส่งข้อมูลการซื้อมินเนี่ยนไปยัง backend
    }
  };

  const handleEndTurn = () => {
    const nextPlayer = currentTurn === players[0] ? players[1] : players[0];
    dispatch(switchTurn(nextPlayer));
    socket.emit("end_turn", { nextTurn: nextPlayer }); // ส่งข้อมูลการสิ้นสุดเทิร์นไปยัง backend
  };

  return (
      <div className="gameplay-container">
        {/* แสดงข้อมูลของผู้เล่น 1 */}
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

        {/* แสดงแผนที่ Hex */}
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

        {/* แสดงข้อมูลของผู้เล่น 2 */}
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

        {/* ปุ่มสำหรับจบเทิร์น */}
        <div className="game-buttons">
          <button className="end-turn-button" onClick={handleEndTurn} disabled={socket.id !== currentTurn}>
            End Turn
          </button>
        </div>
      </div>
  );
};

export default GamePlay;
