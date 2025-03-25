package project.kombat.controller;

import project.kombat_3.evaluator.*;
import project.kombat_3.model.GameState;
import project.kombat_3.model.Player;

import java.util.List;

// คลาสนี้เป็นตัวจัดการเกมในระดับสูง ทำหน้าที่ประสานงานระหว่างส่วนต่างๆ ของเกม
public class GameService {
    // ผู้เล่นคนที่ 1
    private Player player1;
    
    // ผู้เล่นคนที่ 2
    private Player player2;
    
    // สถานะของเกม
    private GameState gameState;

    // สร้าง service ใหม่ ดึงสถานะเกมมาเก็บไว้
    public GameService() {
        this.gameState = GameState.getInstance();
    }

    // เริ่มเกมใหม่ด้วยผู้เล่น 2 คน
    public void startGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        gameState.startGame(player1, player2);
    }

    // ประมวลผลคำสั่งทั้งหมดที่ได้รับมา
    public void processCommands(List<ExecuteNode> commands) {
        for (ExecuteNode command : commands) {
            command.execute();  // รันแต่ละคำสั่งทีละอัน
        }
    }

    // เปลี่ยนไปเทิร์นถัดไป
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