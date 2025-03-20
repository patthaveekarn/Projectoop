package project.kombat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Register the WebSocket handler to handle messages for "/ws" endpoint
        registry.addHandler(webSocketHandler(), "/ws").setAllowedOrigins("*");
    }

    // WebSocket handler to handle incoming and outgoing messages
    public WebSocketHandler webSocketHandler() {
        return new TextWebSocketHandler() {

            // Handle incoming message (custom minion data from frontend)
            @Override
            public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                // ขาเข้า - รับข้อความจาก client (เช่น ข้อมูลมินเนี่ยนที่เลือกจากหน้า CustomizeMinion)
                String incomingMessage = message.getPayload();
                System.out.println("Received message: " + incomingMessage);

                // สมมุติว่าเรามีข้อมูลมินเนี่ยนที่ส่งมาจาก client
                ObjectMapper objectMapper = new ObjectMapper();
                MinionData minionData = objectMapper.readValue(incomingMessage, MinionData.class);

                // ประมวลผลข้อมูลมินเนี่ยน หรือบันทึกในฐานข้อมูล (หากจำเป็น)

                // ขาออก - ส่งข้อความตอบกลับไปยัง client (อาจเป็นผลลัพธ์หรือสถานะ)
                String responseMessage = "Minion data received and processed!";
                session.sendMessage(new TextMessage(responseMessage));
            }
        };
    }
}
