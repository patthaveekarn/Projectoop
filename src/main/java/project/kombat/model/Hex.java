package project.kombat.model;

public class Hex {
    private Minion minion;  // เก็บมินเนียนที่อยู่ในช่องนี้ (อาจจะไม่มีมินเนียนก็ได้)

    // คอนสตรัคเตอร์
    public Hex() {
        this.minion = null;  // เริ่มต้นด้วยการไม่มีมินเนียน
    }

    // ฟังก์ชันในการตั้งค่ามินเนียนในช่อง
    public void placeMinion(Minion minion) {
        this.minion = minion;
    }

    // ฟังก์ชันในการลบมินเนียนจากช่อง
    public void removeMinion() {
        this.minion = null;
    }

    // ฟังก์ชันเพื่อดึงมินเนียนในช่องนี้
    public Minion getMinion() {
        return minion;
    }

    // ฟังก์ชันตรวจสอบว่าช่องนี้มีมินเนียนหรือไม่
    public boolean isOccupied() {
        return minion != null;
    }
}
