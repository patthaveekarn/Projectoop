const express = require("express");
const http = require("http");
const { Server } = require("socket.io");

const app = express();
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: ["http://localhost:3005", "http://127.0.0.1:3005", "http://10.125.161.101:3000"],
        methods: ["GET", "POST"],
        credentials: true,
    },
});

const rooms = {}; // เก็บข้อมูลผู้เล่นในห้อง
const roomSelections = {}; // เก็บมินเนี่ยนที่เลือกของแต่ละคน
const confirmedSelections = {}; // เก็บสถานะ Ready ของแต่ละคน
const grid = []; // ตัวอย่างแผนที่ grid (อาจต้องมีการสร้างเองจากข้อมูลจริง)

let playersInRooms = {};  // เก็บผู้เล่นในแต่ละห้อง

io.on("connection", (socket) => {
    console.log("🟢 A user connected:", socket.id);

    // การเข้าร่วมห้อง
    socket.on("join_room", (roomId) => {
        console.log(`ผู้เล่น ${socket.id} เข้าห้อง ${roomId}`);

        if (!playersInRooms[roomId]) {
            playersInRooms[roomId] = [];
        }

        if (!playersInRooms[roomId].includes(socket.id)) {
            playersInRooms[roomId].push(socket.id);
            socket.join(roomId);
        }

        console.log("✅ Updated Players:", playersInRooms[roomId]);
        io.to(roomId).emit("update_players", playersInRooms[roomId]);

        // ถ้ามีผู้เล่น 2 คน ให้เริ่มเกม
        if (playersInRooms[roomId].length === 2) {
            io.to(roomId).emit("game_started");
        }
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
        for (const roomId in playersInRooms) {
            playersInRooms[roomId] = playersInRooms[roomId].filter(id => id !== socket.id);
            if (playersInRooms[roomId].length > 0) {
                io.to(roomId).emit("update_players", playersInRooms[roomId]);
            }
        }
    });
});

server.listen(4000, () => {
    console.log("🚀 Server running on http://localhost:4000");
});
