import { io } from "socket.io-client";

export const socket = io("http://localhost:4000", {
  transports: ["websocket", "polling"], // ✅ รองรับทั้ง WebSocket และ Polling
  reconnectionAttempts: 5,  // ✅ ลองเชื่อมต่อใหม่ 5 ครั้งหากล้มเหลว
  reconnectionDelay: 2000,   // ✅ เว้นระยะ 2 วิระหว่างการลองใหม่
});

socket.on("connect", () => {
  console.log("🟢 Socket connected:", socket.id);
});

socket.on("disconnect", () => {
  console.log("🔴 Socket disconnected");
});

export default socket;