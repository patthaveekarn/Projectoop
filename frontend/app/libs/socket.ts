import { io } from "socket.io-client";

// เชื่อมต่อกับ WebSocket server โดยใช้ socket.io-client
export const socket = io("http://localhost:4000"); // ใช้ URL ที่ตรงกับ backend ของคุณ

// เมื่อเชื่อมต่อสำเร็จ
socket.on("connect", () => {
  console.log("Socket.io connection established!");
});

// เมื่อรับข้อความจาก backend
socket.on("message", (message) => {
  console.log("Received from server:", message);
});

// เมื่อเกิดข้อผิดพลาดในการเชื่อมต่อ
socket.on("connect_error", (error) => {
  console.error("Socket.io connection error:", error);
});

// เมื่อ WebSocket ปิด
socket.on("disconnect", () => {
  console.log("Socket.io connection closed!");
});
