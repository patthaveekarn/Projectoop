package project.kombat.model;

// คลาสนี้เป็นกระดานเกม เป็นตารางขนาด 8x8 ช่อง แต่ละช่องเป็นรูปหกเหลี่ยม
public class Board {
    // ขนาดของกระดาน 8 แถว
    public static final int ROWS = 8;
    // ขนาดของกระดาน 8 คอลัมน์
    public static final int COLS = 8;
    // เก็บช่องหกเหลี่ยมทั้งหมดในกระดาน
    private Hex[][] hexes;

    // สร้างกระดานใหม่ แล้วก็สร้างช่องหกเหลี่ยมให้ครบทุกช่อง
    public Board() {
        hexes = new Hex[ROWS][COLS];
        initializeBoard();  // สร้างช่องหกเหลี่ยมทั้งหมด
    }

    // สร้างช่องหกเหลี่ยมทุกช่องในกระดาน
    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                hexes[row][col] = new Hex(row, col);  // สร้างช่องใหม่พร้อมบอกพิกัด
            }
        }
    }

    // แสดงกระดานในคอนโซล ใช้ดีบั๊กดูว่ามันถูกมั้ย
    public void displayBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Hex hex = hexes[row][col];
                System.out.print(hex.isOccupied() ? "[X] " : "[ ] ");  // ถ้ามีมินเนี่ยนก็แสดง X ถ้าว่างก็แสดงช่องว่าง
            }
            System.out.println();  // ขึ้นบรรทัดใหม่เมื่อจบแถว
        }
    }

    // เอาช่องที่พิกัดที่ต้องการ ถ้าเกินกระดานก็คืน null
    public Hex getHex(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            return null;  // ถ้าพิกัดเกินกระดานก็คืน null
        }
        return hexes[row][col];
    }

    // บอกว่าช่องนี้มีมินเนี่ยนอยู่รึเปล่า
    public void setOccupied(int row, int col, boolean isOccupied) {
        Hex hex = getHex(row, col);
        if (hex != null) {
            hex.setOccupied(isOccupied);
        }
    }

    // เช็คว่าเดินจากช่องนึงไปอีกช่องนึงได้มั้ย ต้องไม่มีมินเนี่ยนขวางอยู่
    public boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
        Hex fromHex = getHex(fromRow, fromCol);
        Hex toHex = getHex(toRow, toCol);
        return fromHex != null && toHex != null && !toHex.isOccupied();  // ต้องอยู่ในกระดานและช่องปลายทางต้องว่าง
    }
}

