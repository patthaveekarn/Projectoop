package project.kombat.config;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    // In-memory data structures to store game state
    private final Map<String, List<String>> rooms = new ConcurrentHashMap<>();
    private final Map<String, Map<String, List<Integer>>> roomSelections = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> confirmedSelections = new ConcurrentHashMap<>();
    private final List<List<HexData>> grid = new ArrayList<>();

    // Add a player to a room
    public List<String> addPlayerToRoom(String gameMode, String playerId) {
        rooms.computeIfAbsent(gameMode, k -> new ArrayList<>());

        // Only add the player if they're not already in the room
        if (!rooms.get(gameMode).contains(playerId)) {
            rooms.get(gameMode).add(playerId);
        }

        return rooms.get(gameMode);
    }

    // Update minion selection for a player
    public Map<String, List<Integer>> updateMinionSelection(String gameMode, String playerId, Integer minionId, String action) {
        roomSelections.computeIfAbsent(gameMode, k -> new ConcurrentHashMap<>());
        roomSelections.get(gameMode).computeIfAbsent(playerId, k -> new ArrayList<>());

        if ("add".equals(action)) {
            List<Integer> selections = roomSelections.get(gameMode).get(playerId);
            if (selections.size() < 3 && !selections.contains(minionId)) {
                selections.add(minionId);
            }
        } else if ("remove".equals(action)) {
            roomSelections.get(gameMode).get(playerId).removeIf(id -> id.equals(minionId));
        }

        return roomSelections.get(gameMode);
    }

    // Mark a player as ready
    public List<String> markPlayerReady(String gameMode, String playerId) {
        confirmedSelections.computeIfAbsent(gameMode, k -> new HashSet<>());
        confirmedSelections.get(gameMode).add(playerId);

        return new ArrayList<>(confirmedSelections.get(gameMode));
    }

    // Check if all players in a room are ready
    public boolean allPlayersReady(String gameMode) {
        if (!rooms.containsKey(gameMode) || !confirmedSelections.containsKey(gameMode)) {
            return false;
        }

        int playersInRoom = rooms.get(gameMode).size();
        int readyPlayers = confirmedSelections.get(gameMode).size();

        return playersInRoom >= 2 && playersInRoom == readyPlayers;
    }

    // Process hex purchase
    public HexData purchaseHex(int row, int col, String owner) {
        // Ensure the grid has enough rows
        while (grid.size() <= row) {
            grid.add(new ArrayList<>());
        }

        // Ensure the specified row has enough columns
        List<HexData> rowList = grid.get(row);
        while (rowList.size() <= col) {
            rowList.add(null);
        }

        // Update the hex data
        HexData hexData = new HexData();
        hexData.setRow(row);
        hexData.setCol(col);
        hexData.setOwner(owner);
        rowList.set(col, hexData);

        return hexData;
    }

    // Switch to the next player's turn
    public GameStateUpdate switchTurn(String nextTurn) {
        GameStateUpdate update = new GameStateUpdate();
        update.setCurrentTurn(nextTurn);
        return update;
    }

    // Start the game
    public boolean startGame(String gameMode) {
        // Additional game initialization logic can be added here
        return true;
    }
}