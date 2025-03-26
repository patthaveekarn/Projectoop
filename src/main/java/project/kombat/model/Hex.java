package project.kombat.model;

import lombok.Getter;
import lombok.Setter;

public class Hex {
    @Setter
    @Getter
    private int row;
    
    @Setter
    @Getter
    private int col;
    
    private boolean isOccupied;

    public Hex(int row, int col) {
        this.row = row;
        this.col = col;
        this.isOccupied = false;  // ตอนสร้างใหม่ยังไม่มีมินเนี่ยน
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    @Override
    public String toString() {
        return "Hex{" +
                "row=" + row +
                ", col=" + col +
                ", isOccupied=" + isOccupied +
                '}';
    }
}
