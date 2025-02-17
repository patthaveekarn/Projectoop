package project.kombat.model;


public class GameState {
    private Player player1;
    private Player player2;
    private Board board;
    private int turnCount;
    private int maxTurns;
    private double interestRate;
    private int maxSpawns;

    // คอนสตรัคเตอร์สำหรับ GameState
    public GameState(Player player1, Player player2, int maxTurns, double interestRate, int maxSpawns) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
        this.turnCount = 0;
        this.maxTurns = maxTurns;
        this.interestRate = interestRate;
        this.maxSpawns = maxSpawns;
    }

    // ฟังก์ชันเริ่มต้นเทิร์น
    public void startTurn() {
        if (turnCount < maxTurns) {
            // เพิ่มงบประมาณและคำนวณดอกเบี้ย
            updateBudgetInterest(player1);
            updateBudgetInterest(player2);

            // ให้ผู้เล่นทั้งสองสามารถสร้างมินเนียนได้ (ถ้างบประมาณเพียงพอ)
            if (player1.getMinions().size() < maxSpawns) {
                spawnMinion(player1, 0, 0); // Example spawn position (0, 0)
            }
            if (player2.getMinions().size() < maxSpawns) {
                spawnMinion(player2, 7, 7); // Example spawn position (7, 7)
            }

            // ให้มินเนียนทุกตัวทำกลยุทธ์ เช่น เคลื่อนไหว หรือโจมตี
            executeMinionActions(player1);
            executeMinionActions(player2);

            turnCount++;
        } else {
            checkEndGame();
        }
    }

    // ฟังก์ชันคำนวณดอกเบี้ยและอัปเดตงบประมาณ
    private void updateBudgetInterest(Player player) {
        double newBudget = player.getBudget() * (1 + interestRate);
        player.updateBudget((int) newBudget); // อัปเดตงบประมาณ
    }

    // ฟังก์ชันการสร้างมินเนียนใหม่
    public void spawnMinion(Player player, int row, int col) {
        if (player.getMinions().size() < maxSpawns && player.getBudget() >= 100) {  // เช็คจำนวนมินเนียน
            Minion minion = new Minion(100, 50, 10, row, col); // ตัวอย่างการตั้งค่ามินเนียน
            player.addMinion(minion);
            board.placeMinion(minion, row, col); // วางมินเนียนในสนาม
            player.updateBudget(-100);  // หักงบประมาณ
            System.out.println(player.getName() + " spawned a minion at (" + row + "," + col + ")");
        } else {
            System.out.println(player.getName() + " cannot spawn a minion. Either max spawns reached or insufficient budget.");
        }
    }

    // ฟังก์ชันการโจมตีมินเนียน
    public void attack(Minion attacker, Minion target) {
        if (target != null) {
            int damage = attacker.getAttackPower() - target.getDefenseFactor(); // คำนวณความเสียหาย
            if (damage > 0) {
                target.takeDamage(damage); // ลด HP ของมินเนียนที่ถูกโจมตี
                System.out.println(attacker.getRow() + "," + attacker.getCol() + " attacks " +
                        target.getRow() + "," + target.getCol() + " for " + damage + " damage.");
            } else {
                System.out.println("Attack was too weak to cause damage.");
            }
        } else {
            System.out.println("No target to attack.");
        }
    }


    // ฟังก์ชันที่ให้มินเนียนแต่ละตัวทำการเคลื่อนไหว หรือโจมตีตามกลยุทธ์
    private void executeMinionActions(Player player) {
        for (Minion minion : player.getMinions()) {
            // ตัวอย่างการให้มินเนียนทำการเคลื่อนไหว
            minion.move(minion.getRow() + 1, minion.getCol());  // ตัวอย่างการเคลื่อนที่
        }
    }

    // ฟังก์ชันตรวจสอบเงื่อนไขการจบเกม
    public void checkEndGame() {
        if (player1.getMinions().isEmpty()) {
            System.out.println(player2.getName() + " wins!");
        } else if (player2.getMinions().isEmpty()) {
            System.out.println(player1.getName() + " wins!");
        } else {
            determineWinner();
        }
    }

    // ฟังก์ชันคำนวณคะแนนของผู้เล่น
    private void determineWinner() {
        int player1Score = calculatePlayerScore(player1);
        int player2Score = calculatePlayerScore(player2);

        if (player1Score > player2Score) {
            System.out.println(player1.getName() + " wins!");
        } else if (player2Score > player1Score) {
            System.out.println(player2.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    // ฟังก์ชันคำนวณคะแนนของผู้เล่น
    private int calculatePlayerScore(Player player) {
        int score = 0;
        score += player.getMinions().size() * 10;  // คะแนนจากจำนวนมินเนียน
        for (Minion minion : player.getMinions()) {
            score += minion.getHp();  // คะแนนจาก HP ของมินเนียน
        }
        score += player.getBudget() / 10;  // คะแนนจากงบประมาณที่เหลือ
        return score;
    }

    // ฟังก์ชันค้นหามินเนียนที่อยู่ในทิศทางที่กำหนด
    public Minion getTargetMinion(Minion minion, String direction) {
        int row = minion.getRow();
        int col = minion.getCol();

        // ใช้ direction เพื่อหามินเนียนฝ่ายตรงข้าม
        switch (direction) {
            case "up":
                return board.getMinionAt(row - 1, col); // ค้นหามินเนียนข้างบน
            case "down":
                return board.getMinionAt(row + 1, col); // ค้นหามินเนียนข้างล่าง
            case "left":
                return board.getMinionAt(row, col - 1); // ค้นหามินเนียนทางซ้าย
            case "right":
                return board.getMinionAt(row, col + 1); // ค้นหามินเนียนทางขวา
            default:
                return null; // หากทิศทางไม่ถูกต้อง
        }
    }
}


