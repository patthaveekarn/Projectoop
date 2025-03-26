package project.kombat.model;


public class Board {

    public static final int ROWS = 8;

    public static final int COLS = 8;

    private Hex[][] hexes;

    public Board() {
        hexes = new Hex[ROWS][COLS];
        initializeBoard();  // สร้างช่องหกเหลี่ยมทั้งหมด
    }

    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                hexes[row][col] = new Hex(row, col);  // สร้างช่องใหม่พร้อมบอกพิกัด
            }
        }
    }

    public void displayBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Hex hex = hexes[row][col];
                System.out.print(hex.isOccupied() ? "[X] " : "[ ] ");  // ถ้ามีมินเนี่ยนก็แสดง X ถ้าว่างก็แสดงช่องว่าง
            }
            System.out.println();  // ขึ้นบรรทัดใหม่เมื่อจบแถว
        }
    }

    public Hex getHex(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            return null;  // ถ้าพิกัดเกินกระดานก็คืน null
        }
        return hexes[row][col];
    }

    public void setOccupied(int row, int col, boolean isOccupied) {
        Hex hex = getHex(row, col);
        if (hex != null) {
            hex.setOccupied(isOccupied);
        }
    }

    public boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
        Hex fromHex = getHex(fromRow, fromCol);
        Hex toHex = getHex(toRow, toCol);
        return fromHex != null && toHex != null && !toHex.isOccupied();  // ต้องอยู่ในกระดานและช่องปลายทางต้องว่าง
    }
}

