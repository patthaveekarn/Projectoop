package project.kombat.model;

import lombok.Getter;
import lombok.Setter;

// คลาสนี้เป็นมินเนี่ยน หรือตัวละครที่ผู้เล่นควบคุม มีค่าพลังชีวิต การป้องกัน และตำแหน่งบนกระดาน
@Getter
public class Minion {
    // ชื่อของมินเนี่ยน
    @Setter
    private String name;
    
    // พลังชีวิตของมินเนี่ยน ถ้าหมดก็ตาย
    @Setter
    private long hp;
    
    // ค่าการป้องกัน ใช้ลดความเสียหายที่ได้รับ
    @Setter
    private long defense;
    
    // ตำแหน่งแถวบนกระดาน (0-7)
    @Setter
    private int row;
    
    // ตำแหน่งคอลัมน์บนกระดาน (0-7)
    @Setter
    private int col;
    
    // อายุของมินเนี่ยน นับเป็นเทิร์น
    private int age;
    
    // กลยุทธ์ที่มินเนี่ยนจะทำในแต่ละเทิร์น
    @Setter
    private MinionStrategy strategy;

    // สร้างมินเนี่ยนใหม่ด้วยชื่อ พลังชีวิต และการป้องกัน
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

