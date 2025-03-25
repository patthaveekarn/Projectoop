package project.kombat.controller;

import project.kombat_3.model.*;

// คลาสนี้เป็นตัวควบคุมเกมหลักเลย ทำหน้าที่จัดการทุกอย่างในเกม
public class GameController {
    // เก็บสถานะเกมทั้งหมด เช่น ผู้เล่น กระดาน ฯลฯ
    private GameState gameState;
    // เก็บค่าคอนฟิกต่างๆ ของเกม เช่น ราคา ดอกเบี้ย
    private Config config;

    // ตอนสร้างคลาสก็แค่ดึงข้อมูลเกมมาเก็บไว้
    public GameController() {
        this.gameState = GameState.getInstance();
        this.config = new Config();
    }

    // เริ่มเกมใหม่ด้วยผู้เล่น 2 คน ถ้าไม่มีผู้เล่นก็เล่นไม่ได้นะ
    public void startGame(Player player1, Player player2) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Players cannot be null");
        }
        gameState.startGame(player1, player2);
    }

    // ประมวลผลแต่ละเทิร์น คิดดอกเบี้ยให้ผู้เล่น แล้วก็ให้มินเนี่ยนทำงานตามที่โปรแกรมไว้
    public void processTurn() {
        if (isGameOver()) {
            return;
        }
        Player currentPlayer = gameState.getCurrentPlayer();
        long interest = calculateInterest(currentPlayer);
        currentPlayer.setBudget(currentPlayer.getBudget() + interest);
        processMinionStrategies(currentPlayer);
        gameState.nextTurn();
    }

    // เช็คว่าผู้เล่นสามารถสร้างมินเนี่ยนได้มั้ย ต้องมีเงินพอและยังไม่เกินลิมิต
    public boolean canSpawnMinion(Player player) {
        if (player == null) {
            return false;
        }
        return player.getBudget() >= config.getSpawnCost() &&
               player.getMinions().size() < config.getMaxSpawns();
    }

    // สร้างมินเนี่ยนใหม่ที่ตำแหน่งที่กำหนด ถ้าทำได้ก็หักเงินด้วย
    public void spawnMinion(Player player, Minion minion, int row, int col) {
        if (player == null || minion == null) {
            return;
        }
        if (canSpawnMinion(player) && isValidPosition(row, col)) {
            minion.setRow(row);
            minion.setCol(col);
            player.addMinion(minion);
            player.reduceBudget(config.getSpawnCost());
            gameState.getBoard().getHex(row, col).setOccupied(true);
        }
    }

    // เช็คว่าซื้อช่องนี้ได้มั้ย ต้องมีเงินพอและต้องติดกับช่องที่เราเป็นเจ้าของอยู่แล้ว
    public boolean canPurchaseHex(Player player, int row, int col) {
        if (player.getBudget() < config.getHexPurchaseCost()) {
            return false;
        }
        return isAdjacentToOwnedHex(player, row, col);
    }

    // ซื้อช่องที่ตำแหน่งที่กำหนด ถ้าทำได้ก็หักเงินด้วย
    public void purchaseHex(Player player, int row, int col) {
        if (canPurchaseHex(player, row, col)) {
            Hex hex = gameState.getBoard().getHex(row, col);
            player.getOwnedHexes().add(hex);
            player.setBudget(player.getBudget() - config.getHexPurchaseCost());
        }
    }

    // เช็คว่าเกมจบยัง จบถ้าครบเทิร์นหรือฝ่ายใดฝ่ายหนึ่งไม่มีมินเนี่ยนแล้ว
    public boolean isGameOver() {
        return gameState.getTurn() >= config.getMaxTurns() ||
               gameState.getPlayer1().getMinions().isEmpty() ||
               gameState.getPlayer2().getMinions().isEmpty();
    }

    // หาผู้ชนะ ดูจากจำนวนมินเนี่ยน ถ้าเท่ากันก็ดูที่เงิน
    public Player getWinner() {
        if (!isGameOver()) {
            return null;
        }

        Player player1 = gameState.getPlayer1();
        Player player2 = gameState.getPlayer2();

        int minions1 = player1.getMinions().size();
        int minions2 = player2.getMinions().size();

        if (minions1 > minions2) {
            return player1;
        } else if (minions2 > minions1) {
            return player2;
        } else {
            // เสมอกัน ตัดสินด้วยงบประมาณ
            return player1.getBudget() >= player2.getBudget() ? player1 : player2;
        }
    }

    // คำนวณดอกเบี้ยให้ผู้เล่น ถ้ามีเงินเกินลิมิตก็ไม่ได้ดอกนะ
    public long calculateInterest(Player player) {
        if (player.getBudget() >= config.getMaxBudget()) {
            return 0;
        }
        return (long)(player.getBudget() * config.getInterestPct() / 100.0);
    }

    // เช็คว่าตำแหน่งนี้วางมินเนี่ยนได้มั้ย ต้องอยู่ในกระดานและต้องว่างด้วย
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS &&
               !gameState.getBoard().getHex(row, col).isOccupied();
    }

    // เช็คว่าช่องนี้ติดกับช่องที่เราเป็นเจ้าของมั้ย
    private boolean isAdjacentToOwnedHex(Player player, int row, int col) {
        // ตรวจสอบ hex รอบๆ ทั้ง 6 ทิศทาง
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}, {-1,-1}, {-1,1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < Board.ROWS && newCol >= 0 && newCol < Board.COLS) {
                Hex hex = gameState.getBoard().getHex(newRow, newCol);
                if (player.getOwnedHexes().contains(hex)) {
                    return true;
                }
            }
        }
        return false;
    }

    // ให้มินเนี่ยนทำงานตามที่โปรแกรมไว้ เรียงตามอายุด้วยนะ
    private void processMinionStrategies(Player player) {
        // เรียงลำดับมินิออนตามอายุ เอาตัวแก่สุดทำก่อน
        player.getMinions().sort((m1, m2) -> m1.getAge() - m2.getAge());
        
        for (Minion minion : player.getMinions()) {
            MinionStrategy strategy = minion.getStrategy();
            if (strategy != null) {
                strategy.execute();
            }
            minion.incrementAge();
        }
    }
}