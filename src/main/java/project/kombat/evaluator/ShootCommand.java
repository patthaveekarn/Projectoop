package project.kombat.evaluator;

import lombok.Getter;
import project.kombat_3.model.*;

public class ShootCommand implements ExecuteNode {
    @Getter
    private final DirectionNode direction;
    @Getter
    private final ExpressionNode power;
    private final GameState gameState;
    private final Board board;
    private final Config config;

    public ShootCommand(DirectionNode direction, ExpressionNode power) {
        this.direction = direction;
        this.power = power;
        this.gameState = GameState.getInstance();
        this.board = new Board();
        this.config = new Config();
    }

    @Override
    public void execute() {
        // หาตำแหน่งของมินิออนที่กำลังยิง
        Player currentPlayer = gameState.getCurrentPlayer();
        Minion currentMinion = findCurrentMinion(currentPlayer);
        if (currentMinion != null) {
            execute(currentMinion);
        }
    }

    private Minion findCurrentMinion(Player player) {
        // หาตำแหน่งของมินิออนที่กำลังยิง
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Hex hex = board.getHex(row, col);
                if (hex != null && hex.isOccupied()) {
                    for (Minion minion : player.getMinions()) {
                        if (minion.getRow() == row && minion.getCol() == col) {
                            return minion;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void execute(Minion shooter) {
        // ตรวจสอบว่าผู้เล่นมีงบประมาณเพียงพอหรือไม่
        Player currentPlayer = gameState.getCurrentPlayer();
        if (currentPlayer.getBudget() < config.getShootCost()) {
            return;  // ไม่สามารถยิงได้เนื่องจากงบประมาณไม่เพียงพอ
        }

        // คำนวณตำแหน่งเป้าหมาย
        int[] targetPosition = calculateTargetPosition(shooter.getRow(), shooter.getCol());
        int targetRow = targetPosition[0];
        int targetCol = targetPosition[1];

        // หักงบประมาณ (ต้องเสียงบประมาณแม้จะยิงไม่โดน)
        currentPlayer.reduceBudget(config.getShootCost());

        // ตรวจสอบว่ามีมินิออนที่ตำแหน่งเป้าหมายหรือไม่
        Hex targetHex = board.getHex(targetRow, targetCol);
        if (targetHex != null && targetHex.isOccupied()) {
            // หาว่าเป็นมินิออนของผู้เล่นคนไหน
            Minion target = findTargetMinion(targetRow, targetCol);
            if (target != null) {
                // คำนวณความเสียหาย
                long damage = power.evaluate() - target.getDefense();
                if (damage > 0) {
                    target.reduceHp(damage);
                    // ถ้า HP เหลือ 0 ให้ลบมินิออนออกจากเกม
                    if (target.getHp() <= 0) {
                        Player targetPlayer = findTargetPlayer(target);
                        if (targetPlayer != null) {
                            targetPlayer.getMinions().remove(target);
                            targetHex.setOccupied(false);
                        }
                    }
                }
            }
        }
    }

    private int[] calculateTargetPosition(int currentRow, int currentCol) {
        int targetRow = currentRow;
        int targetCol = currentCol;

        switch (direction) {
            case UP:
                targetRow--;
                break;
            case DOWN:
                targetRow++;
                break;
            case UPLEFT:
                targetRow--;
                targetCol--;
                break;
            case UPRIGHT:
                targetRow--;
                targetCol++;
                break;
            case DOWNLEFT:
                targetRow++;
                targetCol--;
                break;
            case DOWNRIGHT:
                targetRow++;
                targetCol++;
                break;
        }

        return new int[]{targetRow, targetCol};
    }

    private Minion findTargetMinion(int row, int col) {
        Player player1 = gameState.getPlayer1();
        Player player2 = gameState.getPlayer2();

        // ตรวจสอบมินิออนของผู้เล่นที่ 1
        for (Minion minion : player1.getMinions()) {
            if (minion.getRow() == row && minion.getCol() == col) {
                return minion;
            }
        }

        // ตรวจสอบมินิออนของผู้เล่นที่ 2
        for (Minion minion : player2.getMinions()) {
            if (minion.getRow() == row && minion.getCol() == col) {
                return minion;
            }
        }

        return null;
    }

    private Player findTargetPlayer(Minion target) {
        Player player1 = gameState.getPlayer1();
        Player player2 = gameState.getPlayer2();

        if (player1.getMinions().contains(target)) {
            return player1;
        } else if (player2.getMinions().contains(target)) {
            return player2;
        }

        return null;
    }
}
