"use client";

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../stores/store";
import { setPlayers } from "../stores/slices/gameModeSlice";
import { socket } from "../libs/socket";

const WaitingRoom: React.FC = () => {
    const router = useRouter();
    const dispatch = useDispatch();
    const players = useSelector((state: RootState) => state.gameMode.players);
    const gameMode = useSelector((state: RootState) => state.gameMode.mode);
    const [connected, setConnected] = useState(false);

    useEffect(() => {
        if (!gameMode || gameMode !== "DUEL") {
            router.push("/ChooseMode");
        }

        socket.on("connect", () => {
            console.log("🟢 Connected to server");
            setConnected(true);
            socket.emit("join_room", gameMode); // ✅ ส่ง event ว่าเข้าห้อง
        });

        socket.on("update_players", (playerList) => {
            console.log("👥 Players Updated:", playerList);
            dispatch(setPlayers(playerList));
        });

        socket.on("disconnect", () => {
            console.log("🔴 Disconnected from server");
            setConnected(false);
        });

        return () => {
            socket.off("connect");
            socket.off("update_players");
            socket.off("disconnect");
        };
    }, [dispatch, router, gameMode]);

    useEffect(() => {
        socket.on("game_started", () => {
            console.log("🎮 Game Started! Redirecting...");
            router.push("/ChooseMinion"); // ✅ ไปหน้าเลือกมินเนี่ยน
        });

        return () => {
            socket.off("game_started");
        };
    }, []);


    const handleStartGame = () => {
        if (players.length === 2) {
            console.log("✅ Game Start! Redirecting...");
            socket.emit("start_game"); // ✅ ส่ง event ให้ backend ว่าเริ่มเกมแล้ว
            router.push("/ChooseMinion"); // ✅ ไปหน้าเลือกมินเนี่ยน
        } else {
            alert("รอผู้เล่นอีกคน...");
        }
    };

    return (
        <div className="waiting-room-container">
            <h1>Waiting Room</h1>
            <p>สถานะ: {connected ? "🟢 เชื่อมต่อแล้ว" : "🔴 หลุดการเชื่อมต่อ"}</p>
            <h3>ผู้เล่นในห้อง:</h3>
            <ul>
                {players.map((player, index) => (
                    <li key={index}>{player}</li>
                ))}
            </ul>
            <button onClick={handleStartGame} disabled={players.length < 2}>
                Start Game
            </button>
        </div>
    );
};

export default WaitingRoom;