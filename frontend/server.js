const express = require("express");
const http = require("http");
const { Server } = require("socket.io");

const app = express();
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: ["http://localhost:3000", "http://127.0.0.1:3000"],
        methods: ["GET", "POST"],
        credentials: true,
    },
});

const rooms = {}; // เก็บข้อมูลผู้เล่นในห้อง
const roomSelections = {}; // เก็บมินเนี่ยนที่เลือกของแต่ละคน
const confirmedSelections = {}; // เก็บสถานะ Ready ของแต่ละคน
const grid = []; // ตัวอย่างแผนที่ grid (อาจต้องมีการสร้างเองจากข้อมูลจริง)

io.on("connection", (socket) => {
    console.log("🟢 A user connected:", socket.id);

    // การเข้าร่วมห้อง
    socket.on("join_room", (gameMode) => {
        console.log(`🚀 Player joined room ${gameMode}:`, socket.id);

        if (!rooms[gameMode]) {
            rooms[gameMode] = [];
        }

        if (!rooms[gameMode].includes(socket.id)) {
            rooms[gameMode].push(socket.id);
            socket.join(gameMode);
        }

        console.log("✅ Updated Players:", rooms[gameMode]);
        io.to(gameMode).emit("update_players", rooms[gameMode]);
    });

    // เลือกมินเนี่ยน
    socket.on("select_minion", ({ gameMode, playerId, minionId, action }) => {
        if (!roomSelections[gameMode]) {
            roomSelections[gameMode] = {};
        }

        if (!roomSelections[gameMode][playerId]) {
            roomSelections[gameMode][playerId] = [];
        }

        if (action === "add") {
            if (roomSelections[gameMode][playerId].length < 3) {
                roomSelections[gameMode][playerId].push(minionId);
            }
        } else if (action === "remove") {
            roomSelections[gameMode][playerId] = roomSelections[gameMode][playerId].filter((id) => id !== minionId);
        }

        console.log(`🔄 Player ${playerId} updated selection:`, roomSelections[gameMode]);
        io.to(gameMode).emit("update_selections", roomSelections[gameMode]); // ส่งข้อมูลอัปเดตให้ทุกคน
    });

    // ยืนยันการเลือกมินเนี่ยน (Ready)
    socket.on("confirm_selection", ({ gameMode, playerId }) => {
        if (!confirmedSelections[gameMode]) {
            confirmedSelections[gameMode] = new Set();
        }

        confirmedSelections[gameMode].add(playerId);
        io.to(gameMode).emit("player_ready", Array.from(confirmedSelections[gameMode]));

        if (confirmedSelections[gameMode].size === 2) {
            io.to(gameMode).emit("selection_complete"); // ทั้ง 2 คนกด Ready แล้ว
        }
    });

    // ซื้อ Hex
    socket.on("hex_bought", ({ row, col, owner }) => {
        if (!grid[row]) {
            grid[row] = [];
        }
        grid[row][col] = { owner };
        io.emit("update_hex", grid); // ส่งข้อมูล Hex ที่อัปเดตไปยังผู้เล่นทุกคน
    });

    // เปลี่ยนเทิร์น
    socket.on("switch_turn", (nextTurn) => {
        io.emit("switch_turn", nextTurn); // ส่งการเปลี่ยนเทิร์นไปยังผู้เล่นทุกคน
    });

    // การออกจากห้อง
    socket.on("disconnect", () => {
        console.log("🔴 A user disconnected:", socket.id);
        for (const gameMode in rooms) {
            rooms[gameMode] = rooms[gameMode].filter((id) => id !== socket.id);
            io.to(gameMode).emit("update_players", rooms[gameMode]);
        }
    });
});

server.listen(4000, () => {
    console.log("🚀 Server running on http://localhost:4000");
});
