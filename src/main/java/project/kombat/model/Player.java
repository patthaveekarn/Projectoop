package project.kombat.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// คลาสนี้เก็บข้อมูลของผู้เล่นแต่ละคน เช่น ชื่อ เงิน มินเนี่ยน พื้นที่ที่ครอบครอง
@Getter
public class Player {
    // ไอดีของผู้เล่น (1 หรือ 2)
    @Getter
    private final int id;
    
    // ชื่อผู้เล่น
    @Setter
    private String name;
    
    // เงินที่มีอยู่
    @Setter
    private long budget;
    
    // มินเนี่ยนทั้งหมดที่มี
    private List<Minion> minions;
    
    // ช่องที่สามารถสร้างมินเนี่ยนได้
    private List<Hex> spawnableHexes;
    
    // ช่องที่เป็นเจ้าของ
    private List<Hex> ownedHexes;

    // constructor แบบง่าย ใส่แค่ชื่อ เงินเริ่มต้นเป็น 0
    public Player(String name) {
        this(name, 0);  // เรียก constructor หลัก
    }

    // constructor หลัก ใส่ทั้งชื่อและเงินเริ่มต้น
    public Player(String name, long initialBudget) {
        this.id = name.equals("Player 1") ? 1 : 2;  // ตั้งไอดีตามชื่อ
        this.name = name;
        this.budget = initialBudget;
        this.minions = new ArrayList<>();
        this.spawnableHexes = new ArrayList<>();
        this.ownedHexes = new ArrayList<>();
        initializeSpawnableHexes();  // ตั้งค่าพื้นที่สร้างมินเนี่ยนเริ่มต้น
    }

    // ตั้งค่าพื้นที่สร้างมินเนี่ยนเริ่มต้น ผู้เล่น 1 อยู่บนซ้าย ผู้เล่น 2 อยู่ล่างขวา
    private void initializeSpawnableHexes() {
        if (id == 1) {
            // พื้นที่ผู้เล่น 1 (บนซ้าย)
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    spawnableHexes.add(new Hex(row, col));
                }
            }
        } else {
            // พื้นที่ผู้เล่น 2 (ล่างขวา)
            for (int row = 5; row < 8; row++) {
                for (int col = 5; col < 8; col++) {
                    spawnableHexes.add(new Hex(row, col));
                }
            }
        }
    }

    // เพิ่มมินเนี่ยนใหม่
    public void addMinion(Minion minion) {
        minions.add(minion);
    }

    // ลบมินเนี่ยนออก (เช่น ตอนตาย)
    public void removeMinion(Minion minion) {
        minions.remove(minion);
    }

    // เช็คว่าสร้างมินเนี่ยนที่ช่องนี้ได้มั้ย
    public boolean canSpawnMinion(Hex hex) {
        return spawnableHexes.contains(hex) && !hex.isOccupied();
    }

    // เพิ่มช่องที่สามารถสร้างมินเนี่ยนได้
    public void addSpawnableHex(Hex hex) {
        spawnableHexes.add(hex);
    }

    // คำนวณ HP รวมของมินเนี่ยนทั้งหมด
    public long getTotalMinionHp() {
        return minions.stream().mapToLong(Minion::getHp).sum();
    }

    // เช็คว่าเป็นผู้เล่นคนเดียวกันมั้ย ดูจากชื่อ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);  // เปรียบเทียบชื่อผู้เล่น
    }

    // คำนวณ hash code จากชื่อ (ต้องมีคู่กับ equals)
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // หักเงินออกจากงบประมาณ
    public void reduceBudget(long amount) {
        this.budget -= amount;
    }
}
