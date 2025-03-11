package project.kombat.model;

public class Minion {
    private int hp;               // พลังชีวิตของมินเนียน
    private int attackPower;      // พลังโจมตีของมินเนียน
    private int defenseFactor;    // ค่าป้องกัน
    private int row;              // ตำแหน่งแถวในสนาม
    private int col;              // ตำแหน่งคอลัมน์ในสนาม
    private MinionType type;      // ประเภทของมินเนียน (ใช้ enum MinionType)

    // คอนสตรัคเตอร์สำหรับสร้างมินเนียนใหม่
    public Minion(int hp, int attackPower, int defenseFactor, int row, int col, MinionType type) {
        this.hp = hp;
        this.attackPower = attackPower;
        this.defenseFactor = defenseFactor;
        this.row = row;
        this.col = col;
        this.type = type;  // กำหนดประเภทของมินเนียน
    }

    // ฟังก์ชันเพื่อดึงพลังชีวิต (HP)
    public int getHp() {
        return hp;
    }

    // ฟังก์ชันในการลดพลังชีวิตเมื่อได้รับความเสียหาย
    public void takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage); // ลดพลังชีวิต แต่ไม่ให้ต่ำกว่า 0
    }

    // ฟังก์ชันเพื่อดึงพลังโจมตี
    public int getAttackPower() {
        return attackPower;
    }

    // ฟังก์ชันเพื่อดึงค่าป้องกัน
    public int getDefenseFactor() {
        return defenseFactor;
    }

    // ฟังก์ชันเพื่อดึงตำแหน่งแถวในสนาม
    public int getRow() {
        return row;
    }

    // ฟังก์ชันเพื่อดึงตำแหน่งคอลัมน์ในสนาม
    public int getCol() {
        return col;
    }

    // ฟังก์ชันในการเคลื่อนไหวมินเนียนไปยังตำแหน่งใหม่
    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

    // ฟังก์ชันเพื่อดึงประเภทของมินเนียน
    public MinionType getType() {
        return type;
    }

    // ฟังก์ชันในการตั้งค่าประเภทของมินเนียน
    public void setType(MinionType type) {
        this.type = type;
    }
}
