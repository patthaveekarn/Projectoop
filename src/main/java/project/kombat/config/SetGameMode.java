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

    @PostMapping("/gameMode")
    public void setGameMode(@RequestBody GameMode gameMode) {
        GameState gameState = getGameState();
        gameState.setGameMode(gameMode);

        messagingTemplate.convertAndSend("/topic/gameMode", gameMode.name());
    }

    private GameState getGameState() {
        return new GameState();
    }
}
