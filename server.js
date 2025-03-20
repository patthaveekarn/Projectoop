const express = require("express");
const http = require("http");
const socketIo = require("socket.io");

const app = express();
const server = http.createServer(app);
const io = socketIo(server);

// เก็บสถานะเกม
let currentTurn = 0;
let players = ["player1", "player2"];  // ผู้เล่น 2 คน
let grid = [];  // ตัวอย่างแผนที่
let gold = { player1: 10, player2: 10 }; // Gold ของผู้เล่น

// เมื่อผู้เล่นเชื่อมต่อ
io.on("connection", (socket) => {
    console.log("User connected:", socket.id);

    // ส่งข้อมูลเริ่มต้น
    socket.emit("update_game_state", { currentTurn });

    // ฟังเหตุการณ์ "buy_hex" จาก client
    socket.on("buy_hex", (hexData) => {
        console.log("Hex bought:", hexData);
        io.emit("hex_updated", hexData); // ส่งข้อมูลการซื้อไปยังทุกคน
    });

    // ฟังเหตุการณ์ "buy_minion" จาก client
    socket.on("buy_minion", (data) => {
        console.log("Minion bought by:", data.player);
        io.emit("minion_updated", { player: data.player });
    });

    // เปลี่ยนเทิร์น
    socket.on("end_turn", (data) => {
        currentTurn = data.nextTurn;
        io.emit("update_game_state", { currentTurn }); // อัปเดตเทิร์นให้ทุกคน
    });

    // เมื่อผู้เล่นออกจากเกม
    socket.on("disconnect", () => {
        console.log("User disconnected:", socket.id);
    });
});

// เริ่ม server
server.listen(4000, () => {
    console.log("Server running on http://localhost:4000");
});
