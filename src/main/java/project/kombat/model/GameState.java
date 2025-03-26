package project.kombat.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private static GameState instance;
    @Getter
    private int turn;
    
    @Getter
    private Player currentPlayer;
    
    @Getter
    private Player player1;
    
    @Getter
    private Player player2;
    
    private Map<String, Long> variables;
    
    @Setter @Getter
    private boolean gameOver;
    
    @Getter
    private Board board;

    private GameState() {
        this.turn = 0;
        this.variables = new HashMap<>();
        this.gameOver = false;
        this.board = new Board();
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void startGame(Player player1, Player player2) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Players cannot be null");
        }
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.turn = 0;
        this.gameOver = false;
        this.board = new Board();
        initializeVariables();
    }

    private void initializeVariables() {
        variables.clear();
        variables.put("t", 0L);
        variables.put("m", 0L);
    }

    public void nextTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        turn++;
    }

    public void setVariable(String name, long value) {
        variables.put(name, value);
    }

    public long getVariable(String name) {
        return variables.getOrDefault(name, 0L);
    }
}

