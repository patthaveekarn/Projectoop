package project.kombat.evaluator;

import lombok.Getter;
import project.kombat_3.model.*;

public class MoveCommand implements ExecuteNode {
    @Getter
    private final DirectionNode direction;
    private final GameState gameState;
    private final Board board;
    private final Config config;

    public MoveCommand(DirectionNode direction) {
        this.direction = direction;
        this.gameState = GameState.getInstance();
        this.board = new Board();
        this.config = new Config();
    }

    @Override
    public void execute() {
        // หาตำแหน่งของมินิออนที่กำลังเคลื่อนที่
        Player currentPlayer = gameState.getCurrentPlayer();
        Minion currentMinion = findCurrentMinion(currentPlayer);
        if (currentMinion != null) {
            execute(currentMinion);
        }
    }

    private Minion findCurrentMinion(Player player) {
        // หาตำแหน่งของมินิออนที่กำลังเคลื่อนที่
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

    public void execute(Minion minion) {
        // ตรวจสอบว่าผู้เล่นมีงบประมาณเพียงพอหรือไม่
        Player currentPlayer = gameState.getCurrentPlayer();
        if (currentPlayer.getBudget() < config.getMoveCost()) {
            return;  // ไม่สามารถเคลื่อนที่ได้เนื่องจากงบประมาณไม่เพียงพอ
        }

        // คำนวณตำแหน่งใหม่
        int[] newPosition = calculateNewPosition(minion.getRow(), minion.getCol());
        int newRow = newPosition[0];
        int newCol = newPosition[1];

        // ตรวจสอบว่าตำแหน่งใหม่อยู่ในขอบเขตของบอร์ดและไม่มีมินิออนอื่นอยู่
        if (isValidPosition(newRow, newCol)) {
            // อัพเดตตำแหน่งของมินิออน
            board.getHex(minion.getRow(), minion.getCol()).setOccupied(false);
            board.getHex(newRow, newCol).setOccupied(true);
            minion.setRow(newRow);
            minion.setCol(newCol);

            // หักงบประมาณ
            currentPlayer.reduceBudget(config.getMoveCost());
        }
    }

    private int[] calculateNewPosition(int currentRow, int currentCol) {
        int newRow = currentRow;
        int newCol = currentCol;

        switch (direction) {
            case UP:
                newRow--;
                break;
            case DOWN:
                newRow++;
                break;
            case UPLEFT:
                newRow--;
                newCol--;
                break;
            case UPRIGHT:
                newRow--;
                newCol++;
                break;
            case DOWNLEFT:
                newRow++;
                newCol--;
                break;
            case DOWNRIGHT:
                newRow++;
                newCol++;
                break;
        }

        return new int[]{newRow, newCol};
    }

    private boolean isValidPosition(int row, int col) {
        // ตรวจสอบว่าตำแหน่งอยู่ในขอบเขตของบอร์ด
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return false;
        }

        // ตรวจสอบว่าไม่มีมินิออนอื่นอยู่ในตำแหน่งนั้น
        Hex hex = board.getHex(row, col);
        return hex != null && !hex.isOccupied();
    }
}
