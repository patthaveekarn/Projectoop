package project.kombat.config;

import project.kombat.websocket.GameMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SetGameMode {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public enum GameMode {
        PlayerVSPlayer, PlayerVSBot, BotVSBot
    }

    // Endpoint สำหรับการตั้งค่าโหมดเกม
    @PostMapping("/gameMode")
    public void setGameMode(@RequestBody GameMode gameMode) {
        // ทำการเปลี่ยนแปลงโหมดเกมใน GameState
        GameState gameState = getGameState();
        gameState.setGameMode(gameMode);

        // ส่งข้อมูลโหมดเกมที่อัปเดตไปยัง WebSocket client
        messagingTemplate.convertAndSend("/topic/gameMode", gameMode.name());
    }

    private GameState getGameState() {
        // สมมติว่าเรามี GameState ที่จัดการสถานะของเกม
        return new GameState();
    }
}
