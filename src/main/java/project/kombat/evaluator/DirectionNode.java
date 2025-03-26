package project.kombat.evaluator;

public enum DirectionNode implements ExpressionNode {
    UP, DOWN, LEFT, RIGHT, UPRIGHT, DOWNRIGHT, UPLEFT, DOWNLEFT;

    @Override
    public long evaluate() {
        return this.ordinal();
    }

    public static DirectionNode fromString(String direction) {
        try {
            // แปลงค่าทิศทางที่รับมาเป็นตัวพิมพ์ใหญ่แล้วแปลงเป็น DirectionNode
            return DirectionNode.valueOf(direction.toUpperCase());
        } catch (IllegalArgumentException e) {
            // หากไม่พบทิศทางที่ตรงกัน ให้โยนข้อผิดพลาด
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    public static boolean isValidDirection(String direction) {
        try {
            fromString(direction); // ใช้เมธอด fromString ในการตรวจสอบ
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}


