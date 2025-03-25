"use client"
import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../stores/store";
import { setPlayers } from "../stores/slices/gameModeSlice";
import { socket } from "../libs/socket";
import '../styles/WaitingRoom.css';

const WaitingRoom: React.FC = () => {
    const router = useRouter();
    const dispatch = useDispatch();
    const players = useSelector((state: RootState) => state.gameMode.players);
    const gameMode = useSelector((state: RootState) => state.gameMode.mode);
    const [connected, setConnected] = useState(false);

    useEffect(() => {
        if (!gameMode || gameMode !== "DUEL") {
            router.push("/ChooseMode");
            return;
        }

        // Handle connection
        socket.on("connect", () => {
            console.log("🟢 Connected to server");
            setConnected(true);
            socket.emit("join_room", gameMode);
        });

        // Handle player updates
        socket.on("update_players", (playerList) => {
            console.log("👥 Players Updated:", playerList);
            dispatch(setPlayers(playerList));
        });

        // Handle disconnection
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

    const handleStartGame = () => {
        // Ensure there are exactly 2 players before starting
        if (players.length === 2) {
            console.log("✅ Game Start! Redirecting...");
            socket.emit("start_game");
            router.push("/ChooseMinion"); // Redirect to ChooseMinion after starting the game
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
            {/* ปุ่ม Start Game จะสามารถกดได้ก็ต่อเมื่อมีผู้เล่นครบ 2 คน */}
            <button
                onClick={handleStartGame}
                disabled={players.length !== 2} // Disable button if less than 2 players
            >
                {players.length < 2 ? "รอผู้เล่นอีกคน..." : "Start Game"}
            </button>
        </div>
    );
};

export default WaitingRoom;
