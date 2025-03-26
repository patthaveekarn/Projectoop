package project.kombat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/join_room/{gameMode}")
    @SendTo("/topic/{gameMode}/update_players")
    public List<String> joinRoom(@DestinationVariable String gameMode, PlayerJoinRequest request) {
        return gameService.addPlayerToRoom(gameMode, request.getPlayerId());
    }

    @MessageMapping("/select_minion/{gameMode}")
    public void selectMinion(@DestinationVariable String gameMode, MinionSelectionRequest request) {
        Map<String, List<Integer>> selections = gameService.ChooseMinion(
                gameMode, request.getPlayerId(), request.getMinionId(), request.getAction());

        messagingTemplate.convertAndSend("/topic/" + gameMode + "/update_selections", selections);
    }

    @MessageMapping("/confirm_selection/{gameMode}")
    public void confirmSelection(@DestinationVariable String gameMode, Player request) {
        List<String> readyPlayers = gameService.markPlayerReady(gameMode, request.getPlayerId());

        messagingTemplate.convertAndSend("/topic/" + gameMode + "/player_ready", readyPlayers);

        if (gameService.allPlayersReady(gameMode)) {
            messagingTemplate.convertAndSend("/topic/" + gameMode + "/selection_complete", true);
        }
    }

    @MessageMapping("/buy_hex")
    @SendTo("/topic/update_hex")
    public HexData buyHex(HexPurchaseRequest request) {
        return gameService.purchaseHex(request.getRow(), request.getCol(), request.getOwner());
    }

    @MessageMapping("/end_turn")
    @SendTo("/topic/update_game_state")
    public GameStateUpdate endTurn(TurnEndRequest request) {
        return gameService.switchTurn(request.getNextTurn());
    }

    @MessageMapping("/start_game/{gameMode}")
    @SendTo("/topic/{gameMode}/game_started")
    public boolean startGame(@DestinationVariable String gameMode) {
        return gameService.startGame(gameMode);
    }
}