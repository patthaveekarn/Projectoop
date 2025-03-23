package project.kombat.config;

import lombok.Data;
import java.util.List;

@Data
class PlayerJoinRequest {
    private String playerId;
}

@Data
class MinionSelectionRequest {
    private String playerId;
    private Integer minionId;
    private String action; // "add" or "remove"
}

@Data
class PlayerReadyRequest {
    private String playerId;
}

@Data
class HexPurchaseRequest {
    private int row;
    private int col;
    private String owner;
}

@Data
class HexData {
    private int row;
    private int col;
    private String owner;
}

@Data
class TurnEndRequest {
    private String nextTurn;
}

@Data
class GameStateUpdate {
    private String currentTurn;
}