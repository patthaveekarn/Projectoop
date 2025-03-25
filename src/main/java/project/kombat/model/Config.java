package project.kombat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// คลาสนี้เก็บการตั้งค่าต่างๆ ของเกม เช่น ราคา พลังชีวิต งบประมาณ ฯลฯ
@Getter
@Setter
public class Config {
    // ราคาในการสร้างมินเนี่ยนใหม่
    private long spawnCost;
    
    // ราคาในการซื้อช่องหกเหลี่ยม
    private long hexPurchaseCost;
    
    // งบประมาณเริ่มต้นของผู้เล่น
    private long initBudget;
    
    // พลังชีวิตเริ่มต้นของมินเนี่ยน
    private long initHp;
    
    // งบประมาณที่ได้เพิ่มในแต่ละเทิร์น
    private long turnBudget;
    
    // งบประมาณสูงสุดที่เก็บได้
    private long maxBudget;
    
    // เปอร์เซ็นต์ดอกเบี้ยที่ได้จากเงินที่มี
    private double interestPct;
    
    // จำนวนเทิร์นสูงสุดที่เล่นได้
    private int maxTurns;
    
    // จำนวนมินเนี่ยนสูงสุดที่สร้างได้
    private int maxSpawns;
    
    // ค่าใช้จ่ายในการเดิน
    private long moveCost;
    
    // ค่าใช้จ่ายในการยิง
    private long shootCost;

    // สร้าง config ใหม่ด้วยค่าเริ่มต้น
    public Config() {
        // ตั้งค่าเริ่มต้นทั้งหมด
        this.spawnCost = 100;        // สร้างมินเนี่ยน 100 บาท
        this.hexPurchaseCost = 1000; // ซื้อช่อง 1000 บาท
        this.initBudget = 10000;     // เริ่มต้นมี 10000 บาท
        this.initHp = 100;           // มินเนี่ยนมี HP 100
        this.turnBudget = 90;        // ได้เงินเพิ่มเทิร์นละ 90
        this.maxBudget = 23456;      // เก็บเงินได้สูงสุด 23456
        this.interestPct = 5.0;      // ดอกเบี้ย 5%
        this.maxTurns = 69;          // เล่นได้ 69 เทิร์น
        this.maxSpawns = 47;         // สร้างมินเนี่ยนได้ 47 ตัว
        this.moveCost = 1;           // เดินครั้งละ 1 บาท
        this.shootCost = 2;          // ยิงครั้งละ 2 บาท
    }

    // โหลดค่าจากไฟล์ properties มาใช้แทนค่าเริ่มต้น
    public void loadFromFile(String filename) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filename)) {
            props.load(fis);
            
            // อ่านค่าจากไฟล์ ถ้าไม่มีก็ใช้ค่าเดิม
            this.spawnCost = Long.parseLong(props.getProperty("spawn_cost", String.valueOf(this.spawnCost)));
            this.hexPurchaseCost = Long.parseLong(props.getProperty("hex_purchase_cost", String.valueOf(this.hexPurchaseCost)));
            this.initBudget = Long.parseLong(props.getProperty("init_budget", String.valueOf(this.initBudget)));
            this.initHp = Long.parseLong(props.getProperty("init_hp", String.valueOf(this.initHp)));
            this.turnBudget = Long.parseLong(props.getProperty("turn_budget", String.valueOf(this.turnBudget)));
            this.maxBudget = Long.parseLong(props.getProperty("max_budget", String.valueOf(this.maxBudget)));
            this.interestPct = Double.parseDouble(props.getProperty("interest_pct", String.valueOf(this.interestPct)));
            this.maxTurns = Integer.parseInt(props.getProperty("max_turns", String.valueOf(this.maxTurns)));
            this.maxSpawns = Integer.parseInt(props.getProperty("max_spawns", String.valueOf(this.maxSpawns)));
            this.moveCost = Long.parseLong(props.getProperty("move_cost", String.valueOf(this.moveCost)));
            this.shootCost = Long.parseLong(props.getProperty("shoot_cost", String.valueOf(this.shootCost)));
        } catch (IOException e) {
            System.err.println("ไม่สามารถโหลดไฟล์การตั้งค่าได้: " + e.getMessage());
        }
    }

    // เมธอดสำหรับดึงค่าต่างๆ (ใช้ @Getter แทนแล้ว)
    public long getSpawnCost() { return spawnCost; }
    public long getHexPurchaseCost() { return hexPurchaseCost; }
    public long getInitBudget() { return initBudget; }
    public long getInitHp() { return initHp; }
    public long getTurnBudget() { return turnBudget; }
    public long getMaxBudget() { return maxBudget; }
    public double getInterestPct() { return interestPct; }
    public int getMaxTurns() { return maxTurns; }
    public int getMaxSpawns() { return maxSpawns; }
    public long getMoveCost() { return moveCost; }
    public long getShootCost() { return shootCost; }
}

