package project.kombat.config;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

public class GameWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messagePayload = message.getPayload();

        System.out.println("Received message: " + messagePayload);

        session.sendMessage(new TextMessage("Game mode updated"));
    }
}
