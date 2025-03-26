package project.kombat.evaluator;

import project.kombat.model.*;

import java.util.List;

public class InfoExpressionNode implements ExpressionNode {
    private final String infoType;
    private final ExpressionNode direction;

    public InfoExpressionNode(String infoType, ExpressionNode direction) {
        this.infoType = infoType;
        this.direction = direction;
    }

    @Override
    public long evaluate() {
        GameState state = GameState.getInstance();
        switch (infoType.toLowerCase()) {
            case "nearest":
                return findNearestMinion(state);
            case "direction":
                return direction.evaluate();
            default:
                return 0;
        }
    }

    private long findNearestMinion(GameState state) {
        Player currentPlayer = state.getCurrentPlayer();
        List<Minion> currentMinions = currentPlayer.getMinions();
        
        if (currentMinions.isEmpty()) {
            return -1; // ไม่มีมินิออน
        }

        double minDistance = Double.MAX_VALUE;
        Minion nearestMinion = null;

        // ค้นหามินิออนที่ใกล้ที่สุดจากมินิออนทั้งหมดของผู้เล่น
        for (Minion currentMinion : currentMinions) {
            for (Minion otherMinion : currentMinions) {
                if (currentMinion != otherMinion) {
                    double distance = calculateDistance(currentMinion, otherMinion);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestMinion = otherMinion;
                    }
                }
            }
        }

        if (nearestMinion == null) {
            return -1; // ไม่พบมินิออนที่ใกล้ที่สุด
        }

        // คำนวณทิศทางไปยังมินิออนที่ใกล้ที่สุด
        return (long) calculateDirection(currentMinions.get(0), nearestMinion);
    }

    private double calculateDistance(Minion m1, Minion m2) {
        int dx = m2.getCol() - m1.getCol();
        int dy = m2.getRow() - m1.getRow();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double calculateDirection(Minion from, Minion to) {
        int dx = to.getCol() - from.getCol();
        int dy = to.getRow() - from.getRow();
        
        // คำนวณมุมในหน่วยองศา
        double angle = Math.toDegrees(Math.atan2(dy, dx));
        
        // ปรับมุมให้อยู่ในช่วง 0-360 องศา
        if (angle < 0) {
            angle += 360;
        }
        
        // แปลงมุมเป็นทิศทาง (0-5)
        return Math.round(angle / 60.0) % 6;
    }

    @Override
    public String toString() {
        return "Info(" + infoType + ", " + direction + ")";
    }
} 