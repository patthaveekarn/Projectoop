package project.kombat.strategy.Eval;
import project.kombat.model.*;


public class EvalTest {
    public static void main(String[] args) {
        // สร้างผู้เล่นสองคน
        Player player1 = new Player("Player 1", 1000);  // งบประมาณเริ่มต้นที่ 1000
        Player player2 = new Player("Player 2", 1000);  // งบประมาณเริ่มต้นที่ 1000

        // สร้าง GameState (10 เทิร์น, 5% ดอกเบี้ย, สร้างมินเนียนสูงสุด 5 ตัว)
        GameState gameState = new GameState(player1, player2, 10, 0.05, 5);

        // สร้าง Evaluator สำหรับประมวลผลกลยุทธ์
        project.kombat.strategy.Eval.Evaluator evaluator = new project.kombat.strategy.Eval.Evaluator();

        // สร้างมินเนียน
        Minion minion = new Minion(100, 30, 10, 0, 0); // มินเนียนเริ่มต้นที่ตำแหน่ง (0, 0)

        // ทดสอบการประมวลผลกลยุทธ์การเคลื่อนไหว
        evaluator.evaluateStrategy("move up", minion, gameState);  // ทดสอบการเคลื่อนไหว
        evaluator.evaluateStrategy("shoot right 10", minion, gameState);  // ทดสอบการโจมตี

        // แสดงสถานะมินเนียนหลังการประมวลผล
        System.out.println("Minion position: (" + minion.getRow() + ", " + minion.getCol() + ")");
        System.out.println("Remaining budget: " + evaluator.getVariableValue("budget"));
    }
}
