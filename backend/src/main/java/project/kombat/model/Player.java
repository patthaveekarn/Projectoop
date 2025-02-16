package src.main.java.project.kombat.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;  // ชื่อผู้เล่น
    private int budget;   // งบประมาณของผู้เล่น
    private List<Minion> minions;  // รายการมินเนียนที่ผู้เล่นมี

    // คอนสตรัคเตอร์สำหรับสร้างผู้เล่นใหม่
    public Player(String name, int initialBudget) {
        this.name = name;
        this.budget = initialBudget;
        this.minions = new ArrayList<>();  // เริ่มต้นด้วยรายการมินเนียนที่ว่าง
    }

    // ฟังก์ชัน getter สำหรับชื่อผู้เล่น
    public String getName() {
        return name;
    }

    // ฟังก์ชัน getter สำหรับงบประมาณ
    public int getBudget() {
        return budget;
    }

    // ฟังก์ชัน getter สำหรับรายการมินเนียน
    public List<Minion> getMinions() {
        return minions;
    }

    // ฟังก์ชันเพื่อเพิ่มมินเนียนในรายการ
    public void addMinion(Minion minion) {
        minions.add(minion);
    }

    // ฟังก์ชันเพื่ออัปเดตงบประมาณ
    public void updateBudget(int amount) {
        this.budget += amount;
    }

    // ฟังก์ชันตรวจสอบว่าผู้เล่นมีมินเนียนเหลืออยู่หรือไม่
    public boolean hasMinions() {
        return !minions.isEmpty();  // ถ้ามินเนียนไม่ว่างแสดงว่าผู้เล่นมีมินเนียน
    }

    // ฟังก์ชันตรวจสอบว่าเงื่อนไขในการสร้างมินเนียนใหม่เป็นไปตามที่กำหนดหรือไม่
    public boolean canSpawnMinion(int spawnCost) {
        return this.budget >= spawnCost;  // ถ้างบประมาณเพียงพอให้สร้างมินเนียน
    }
}
