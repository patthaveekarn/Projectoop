package project.kombat.Controller;

import project.kombat.model.*;

public class GameController {
    private GameState gameState;
    private Config config;

    public GameController() {
        this.gameState = GameState.getInstance();
        this.config = new Config();
    }

    public void startGame(Player player1, Player player2) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Players cannot be null");
        }
        gameState.startGame(player1, player2);
    }

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

    public boolean canSpawnMinion(Player player) {
        if (player == null) {
            return false;
        }
        return player.getBudget() >= config.getSpawnCost() &&
               player.getMinions().size() < config.getMaxSpawns();
    }

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

    public boolean canPurchaseHex(Player player, int row, int col) {
        if (player.getBudget() < config.getHexPurchaseCost()) {
            return false;
        }
        return isAdjacentToOwnedHex(player, row, col);
    }

    public void purchaseHex(Player player, int row, int col) {
        if (canPurchaseHex(player, row, col)) {
            Hex hex = gameState.getBoard().getHex(row, col);
            player.getOwnedHexes().add(hex);
            player.setBudget(player.getBudget() - config.getHexPurchaseCost());
        }
    }

    public boolean isGameOver() {
        return gameState.getTurn() >= config.getMaxTurns() ||
               gameState.getPlayer1().getMinions().isEmpty() ||
               gameState.getPlayer2().getMinions().isEmpty();
    }

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
            return player1.getBudget() >= player2.getBudget() ? player1 : player2;
        }
    }

    public long calculateInterest(Player player) {
        if (player.getBudget() >= config.getMaxBudget()) {
            return 0;
        }
        return (long)(player.getBudget() * config.getInterestPct() / 100.0);
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS &&
               !gameState.getBoard().getHex(row, col).isOccupied();
    }

    private boolean isAdjacentToOwnedHex(Player player, int row, int col) {
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

    private void processMinionStrategies(Player player) {
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