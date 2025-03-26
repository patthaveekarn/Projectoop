package project.kombat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Minion {
    @Setter
    private String name;
    
    @Setter
    private long hp;
    
    @Setter
    private long defense;
    
    @Setter
    private int row;
    
    @Setter
    private int col;
    
    private int age;
    
    @Setter
    private MinionStrategy strategy;

    public Minion(String name, long hp, long defense) {
        this.name = name;
        this.hp = hp;
        this.defense = defense;
        this.age = 0;  // เริ่มต้นอายุที่ 0
    }

    // เพิ่มอายุขึ้น 1 เทิร์น
    public void incrementAge() {
        age++;
    }

    // ลดพลังชีวิตตามความเสียหายที่ได้รับ แต่ไม่ให้ต่ำกว่า 0
    public void reduceHp(long damage) {
        this.hp = Math.max(0, this.hp - damage);  // ถ้า hp - damage < 0 ก็ให้เป็น 0
    }
}

