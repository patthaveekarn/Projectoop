package project.kombat.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

// คลาสนี้เก็บสถานะทั้งหมดของเกม ใช้ Singleton pattern เพราะต้องมีแค่ตัวเดียวในเกม
public class GameState {
    // instance เดียวที่มีในเกม
    private static GameState instance;
    
    // เก็บว่าตอนนี้เทิร์นที่เท่าไหร่แล้ว
    @Getter
    private int turn;
    
    // ผู้เล่นที่กำลังเล่นอยู่ตอนนี้
    @Getter
    private Player currentPlayer;
    
    // ผู้เล่นคนที่ 1
    @Getter
    private Player player1;
    
    // ผู้เล่นคนที่ 2
    @Getter
    private Player player2;
    
    // ตัวแปรที่ใช้ในเกม เช่น t (เวลา), m (เงิน)
    private Map<String, Long> variables;
    
    // สถานะว่าเกมจบรึยัง
    @Setter @Getter
    private boolean gameOver;
    
    // กระดานเกม
    @Getter
    private Board board;

    // constructor ส่วนตัว เพราะใช้ Singleton
    private GameState() {
        this.turn = 0;
        this.variables = new HashMap<>();
        this.gameOver = false;
        this.board = new Board();
    }

    // เมธอดสำหรับเรียกใช้ instance เดียวที่มี
    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();  // สร้างใหม่ถ้ายังไม่มี
        }
        return instance;
    }

    // รีเซ็ต instance ถ้าต้องการเริ่มเกมใหม่
    public static void resetInstance() {
        instance = null;
    }

    // เริ่มเกมใหม่ด้วยผู้เล่น 2 คน
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

    // ตั้งค่าตัวแปรเริ่มต้น t=0 (เวลา) และ m=0 (เงิน)
    private void initializeVariables() {
        variables.clear();
        variables.put("t", 0L);
        variables.put("m", 0L);
    }

    // เปลี่ยนเทิร์นไปผู้เล่นถัดไป
    public void nextTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        turn++;
    }

    // เซ็ตค่าตัวแปรในเกม
    public void setVariable(String name, long value) {
        variables.put(name, value);
    }

    // ดึงค่าตัวแปรในเกม ถ้าไม่มีก็คืน 0
    public long getVariable(String name) {
        return variables.getOrDefault(name, 0L);
    }
}

