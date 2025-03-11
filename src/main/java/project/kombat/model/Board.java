package project.kombat.model;

public class Board {
    private final int SIZE = 8; // ขนาดของสนาม 8x8
    private Hex[][] grid;    // เปลี่ยนจาก Minion เป็น Hex

    // คอนสตรัคเตอร์เริ่มต้น
    public Board() {
        this.grid = new Hex[SIZE][SIZE];  // สร้างสนาม 8x8
        initializeBoard();  // เริ่มต้นทุกช่องให้เป็น Hex ว่าง
    }

    // ฟังก์ชันเริ่มต้นสนามทั้งหมด
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Hex();  // สร้าง Hex ใหม่ในแต่ละช่อง
            }
        }
    }

    // ฟังก์ชันในการวางมินเนียนในช่องที่กำหนด
    public void placeMinion(Minion minion, int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            grid[row][col].placeMinion(minion);  // วางมินเนียนในช่อง (row, col)
        } else {
            System.out.println("Invalid position on the board.");
        }
    }

    // ฟังก์ชันในการลบมินเนียนจากช่องที่กำหนด
    public void removeMinion(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            grid[row][col].removeMinion();  // ลบมินเนียนในช่องนั้น
        } else {
            System.out.println("Invalid position on the board.");
        }
    }

    // ฟังก์ชันในการดึงมินเนียนจากช่องที่กำหนด
    public Minion getMinionAt(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            return grid[row][col].getMinion();  // คืนค่ามินเนียนในตำแหน่ง (row, col)
        } else {
            System.out.println("Invalid position on the board.");
            return null;
        }
    }

    // ฟังก์ชันในการแสดงสถานะของสนาม (8x8)
    public void displayBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].isOccupied()) {
                    System.out.print("[M] ");  // ถ้ามีมินเนียน
                } else {
                    System.out.print("[ ] ");  // ถ้าช่องนั้นว่างเปล่า
                }
            }
            System.out.println();  // เปลี่ยนบรรทัดหลังจากแสดงแต่ละแถว
        }
    }
}
