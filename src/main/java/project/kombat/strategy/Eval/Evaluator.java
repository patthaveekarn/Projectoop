package project.kombat.strategy.Eval;

import project.kombat.model.*;

import java.util.HashMap;
import java.util.Map;


public class Evaluator {
    private Map<String, Long> variables; // เก็บตัวแปรที่ใช้ในการประมวลผลกลยุทธ์

    // คอนสตรัคเตอร์เริ่มต้น
    public Evaluator() {
        variables = new HashMap<>();
        variables.put("row", 0L);  // เริ่มต้นตำแหน่งแถว
        variables.put("col", 0L);  // เริ่มต้นตำแหน่งคอลัมน์
        variables.put("budget", 1000L);  // เริ่มต้นงบประมาณ
        variables.put("spawnsleft", 5L); // เริ่มต้นจำนวนมินเนียนที่สามารถสร้างได้
        variables.put("random", (long) (Math.random() * 1000)); // ค่า random ระหว่าง 0 ถึง 999
    }

    // ฟังก์ชันประมวลผลกลยุทธ์
    public void evaluateStrategy(String strategy, Minion minion, GameState gameState) {
        if (strategy.contains("move")) {
            String direction = strategy.split(" ")[1];  // รับทิศทาง
            executeMove(minion, direction);
        } else if (strategy.contains("shoot")) {
            String direction = strategy.split(" ")[1];  // รับทิศทาง
            int cost = Integer.parseInt(strategy.split(" ")[2]);  // รับค่าใช้จ่าย
            executeShoot(minion, direction, cost, gameState);
        } else if (strategy.contains("done")) {
            // ฟังก์ชันการจบกลยุทธ์
            System.out.println(minion.getRow() + "," + minion.getCol() + " has finished its turn.");
        }
    }

    // ฟังก์ชันการเคลื่อนไหวมินเนียน
    private void executeMove(Minion minion, String direction) {
        // ประมวลผลการเคลื่อนไหวของมินเนียนไปยังทิศทางที่กำหนด
        switch (direction) {
            case "up":
                minion.move(minion.getRow() - 1, minion.getCol());
                break;
            case "down":
                minion.move(minion.getRow() + 1, minion.getCol());
                break;
            case "left":
                minion.move(minion.getRow(), minion.getCol() - 1);
                break;
            case "right":
                minion.move(minion.getRow(), minion.getCol() + 1);
                break;
        }
        // ลดงบประมาณหลังการเคลื่อนไหว
        long currentBudget = variables.get("budget");
        variables.put("budget", currentBudget - 1);  // คิดค่าใช้จ่าย 1 หน่วยสำหรับการเคลื่อนไหว
    }

    // ฟังก์ชันการโจมตี
    private void executeShoot(Minion minion, String direction, int cost, GameState gameState) {
        // ตรวจสอบว่าโจมตีได้หรือไม่
        long currentBudget = variables.get("budget");
        if (currentBudget >= cost + 1) {  // ค่าใช้จ่ายในการโจมตี + 1 สำหรับการโจมตี
            Minion target = gameState.getTargetMinion(minion, direction);  // หามินเนียนที่เป็นเป้าหมาย
            if (target != null) {
                int damage = Math.max(0, cost - target.getDefenseFactor());
                target.takeDamage(damage);
                System.out.println(minion.getRow() + "," + minion.getCol() + " shoots " + direction + " for " + damage + " damage.");
            } else {
                System.out.println("No minion found in the target direction.");
            }
            // ลดงบประมาณจากการโจมตี
            variables.put("budget", currentBudget - (cost + 1));
        } else {
            System.out.println("Not enough budget to shoot.");
        }
    }

    // ฟังก์ชันเช็คตัวแปรพิเศษ
    public long getVariableValue(String varName) {
        return variables.getOrDefault(varName, 0L);
    }
}

