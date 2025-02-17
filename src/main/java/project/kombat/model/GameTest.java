package project.kombat.model;


public class GameTest {
    public static void main(String[] args) {
        // สร้างผู้เล่นสองคน
        Player player1 = new Player("Player 1", 1000);  // งบประมาณเริ่มต้น 1000
        Player player2 = new Player("Player 2", 1000);  // งบประมาณเริ่มต้น 1000

        // สร้าง GameState (10 เทิร์น, 5% ดอกเบี้ย, สร้างมินเนียนสูงสุด 47 ตัว)
        GameState gameState = new GameState(player1, player2, 10, 0.05, 5);

        // แสดงสถานะเริ่มต้น
        System.out.println(player1.getName() + " starts with " + player1.getBudget() + " budget.");
        System.out.println(player2.getName() + " starts with " + player2.getBudget() + " budget.");

        // เริ่มเทิร์นที่ 1
        gameState.startTurn();

        // ตรวจสอบสถานะของสนามหลังจากเทิร์นแรก
        System.out.println("\n--- After Turn 1 ---");
        gameState.checkEndGame();

        // เริ่มเทิร์นที่ 2
        gameState.startTurn();

        // ตรวจสอบสถานะของสนามหลังจากเทิร์นที่สอง
        System.out.println("\n--- After Turn 2 ---");
        gameState.checkEndGame();

        // ทดสอบการสร้างมินเนียนและโจมตี
        Minion minion1 = new Minion(100, 30, 10, 0, 0); // มินเนียนของ Player 1
        Minion minion2 = new Minion(80, 25, 5, 7, 7);  // มินเนียนของ Player 2

        player1.addMinion(minion1);
        player2.addMinion(minion2);

        // แสดงจำนวนมินเนียน
        System.out.println("\n--- Minion counts ---");
        System.out.println(player1.getName() + " has " + player1.getMinions().size() + " minions.");
        System.out.println(player2.getName() + " has " + player2.getMinions().size() + " minions.");

        // ทดสอบการโจมตี
        System.out.println("\n--- Attack Test ---");
        gameState.attack(minion1, minion2);

        // แสดงสถานะของมินเนียนหลังการโจมตี
        System.out.println("Player 2's minion HP after attack: " + minion2.getHp());

        // ทดสอบการจบเกม
        System.out.println("\n--- Endgame Check ---");
        gameState.checkEndGame();
    }
}


