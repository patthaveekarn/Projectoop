package project.kombat.model;

import lombok.Getter;
import lombok.Setter;

// คลาสนี้แทนช่องหกเหลี่ยมแต่ละช่องบนกระดาน เก็บพิกัดและสถานะว่ามีมินเนี่ยนอยู่มั้ย
public class Hex {
    // พิกัดแถวบนกระดาน (0-7)
    @Setter
    @Getter
    private int row;
    
    // พิกัดคอลัมน์บนกระดาน (0-7)
    @Setter
    @Getter
    private int col;
    
    // เก็บว่าช่องนี้มีมินเนี่ยนอยู่รึเปล่า
    private boolean isOccupied;

    // สร้างช่องใหม่ด้วยพิกัด เริ่มต้นจะว่างเสมอ
    public Hex(int row, int col) {
        this.row = row;
        this.col = col;
        this.isOccupied = false;  // ตอนสร้างใหม่ยังไม่มีมินเนี่ยน
    }

    // เช็คว่าช่องนี้มีมินเนี่ยนอยู่มั้ย
    public boolean isOccupied() {
        return isOccupied;
    }

    // บอกว่าช่องนี้มีมินเนี่ยนหรือว่าง
    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    // แปลงเป็นข้อความสำหรับดีบั๊ก
    @Override
    public String toString() {
        return "Hex{" +
                "row=" + row +
                ", col=" + col +
                ", isOccupied=" + isOccupied +
                '}';
    }
}
