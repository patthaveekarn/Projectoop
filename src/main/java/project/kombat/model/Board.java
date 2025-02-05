package src.main.java.project.kombat.model;


public class Board {
    private final int SIZE = 8; // ขนาดของสนาม 8x8
    private Minion[][] grid;    // 2D Array สำหรับเก็บมินเนียนในแต่ละช่อง

    // คอนสตรัคเตอร์เริ่มต้น
    public Board() {
        this.grid = new Minion[SIZE][SIZE];  // สร้างสนาม 8x8
    }

    // ฟังก์ชันในการวางมินเนียนในช่องที่กำหนด
    public void placeMinion(Minion minion, int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            grid[row][col] = minion;  // วางมินเนียนในตำแหน่ง (row, col)
        } else {
            System.out.println("Invalid position on the board.");
        }
    }

    // ฟังก์ชันในการลบมินเนียนจากช่องที่กำหนด
    public void removeMinion(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            grid[row][col] = null;  // ลบมินเนียนในช่องนั้น
        } else {
            System.out.println("Invalid position on the board.");
        }
    }

    // ฟังก์ชันในการดึงมินเนียนจากช่องที่กำหนด
    public Minion getMinionAt(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            return grid[row][col];  // คืนค่ามินเนียนในตำแหน่ง (row, col)
        } else {
            System.out.println("Invalid position on the board.");
            return null;
        }
    }

    // ฟังก์ชันในการแสดงสถานะของสนาม (8x8)
    public void displayBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Minion minion = grid[i][j];
                if (minion == null) {
                    System.out.print("[ ] ");  // ถ้าช่องนั้นว่างเปล่า
                } else {
                    System.out.print("[M] ");  // ถ้ามีมินเนียน
                }
            }
            System.out.println();  // เปลี่ยนบรรทัดหลังจากแสดงแต่ละแถว
        }
    }
}
