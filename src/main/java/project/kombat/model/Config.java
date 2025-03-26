package project.kombat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Getter
@Setter
public class Config {

    private long spawnCost;

    private long hexPurchaseCost;

    private long initBudget;
    
    private long initHp;
    
    private long turnBudget;
    
    private long maxBudget;
    
    private double interestPct;
    
    private int maxTurns;
    
    private int maxSpawns;
    
    private long moveCost;
    
    private long shootCost;

    public Config() {
        this.spawnCost = 100;
        this.hexPurchaseCost = 1000;
        this.initBudget = 10000;
        this.initHp = 100;
        this.turnBudget = 90;
        this.maxBudget = 23456;
        this.interestPct = 5.0;
        this.maxTurns = 69;
        this.maxSpawns = 47;
        this.moveCost = 1;
        this.shootCost = 2;
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

