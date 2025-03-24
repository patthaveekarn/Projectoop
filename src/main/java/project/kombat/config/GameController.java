package project.kombat.config;

import project.kombat.config.model.*;
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

    // Handle joining a room
    @MessageMapping("/join_room/{gameMode}")
    @SendTo("/topic/{gameMode}/update_players")
    public List<String> joinRoom(@DestinationVariable String gameMode, PlayerJoinRequest request) {
        return gameService.addPlayerToRoom(gameMode, request.getPlayerId());
    }

    // Handle minion selection
    @MessageMapping("/select_minion/{gameMode}")
    public void selectMinion(@DestinationVariable String gameMode, MinionSelectionRequest request) {
        Map<String, List<Integer>> selections = gameService.ChooseMinion(
                gameMode, request.getPlayerId(), request.getMinionId(), request.getAction());

        // Send the updated selections to all clients in the room
        messagingTemplate.convertAndSend("/topic/" + gameMode + "/update_selections", selections);
    }

    // Handle player ready status
    @MessageMapping("/confirm_selection/{gameMode}")
    public void confirmSelection(@DestinationVariable String gameMode, Player request) {
        List<String> readyPlayers = gameService.markPlayerReady(gameMode, request.getPlayerId());

        // Send the updated ready players list to all clients in the room
        messagingTemplate.convertAndSend("/topic/" + gameMode + "/player_ready", readyPlayers);

        // Check if all players are ready
        if (gameService.allPlayersReady(gameMode)) {
            messagingTemplate.convertAndSend("/topic/" + gameMode + "/selection_complete", true);
        }
    }

    // Handle hex purchases
    @MessageMapping("/buy_hex")
    @SendTo("/topic/update_hex")
    public HexData buyHex(HexPurchaseRequest request) {
        return gameService.purchaseHex(request.getRow(), request.getCol(), request.getOwner());
    }

    // Handle turn switching
    @MessageMapping("/end_turn")
    @SendTo("/topic/update_game_state")
    public GameStateUpdate endTurn(TurnEndRequest request) {
        return gameService.switchTurn(request.getNextTurn());
    }

    // Start the game
    @MessageMapping("/start_game/{gameMode}")
    @SendTo("/topic/{gameMode}/game_started")
    public boolean startGame(@DestinationVariable String gameMode) {
        return gameService.startGame(gameMode);
    }
}