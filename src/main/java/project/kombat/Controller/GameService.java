package project.kombat.Controller;

import project.kombat.evaluator.*;
import project.kombat.model.GameState;
import project.kombat.model.Player;

import java.util.List;

public class GameService {
    private Player player1;
    
    private Player player2;
    
    private GameState gameState;

    public GameService() {
        this.gameState = GameState.getInstance();
    }

    public void startGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        gameState.startGame(player1, player2);
    }

    public void processCommands(List<ExecuteNode> commands) {
        for (ExecuteNode command : commands) {
            command.execute();  // รันแต่ละคำสั่งทีละอัน
        }
    }

    public void nextTurn(Player player1,Player player2) {
        gameState.nextTurn();
    }

    // เช็คว่าเกมจบรึยัง
    public boolean isGameOver() {
        return gameState.isGameOver();
    }

    // ดูว่าตอนนี้เป็นเทิร์นของใคร
    public Player getCurrentPlayer() {
        return gameState.getCurrentPlayer();
    }
}