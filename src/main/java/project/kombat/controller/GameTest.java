package project.kombat.controller;

import project.kombat_3.evaluator.*;
import project.kombat_3.model.*;
import project.kombat_3.parser.*;

import java.util.List;

// คลาสนี้ใช้ทดสอบการทำงานของเกม โดยเฉพาะการแปลงสคริปต์เป็นคำสั่งและการรันคำสั่ง
public class GameTest {
    public static void main(String[] args) {
        // เขียนสคริปต์ตัวอย่างให้มินเนี่ยน
        // - บวก t ด้วย 3
        // - ลบ t ด้วย 1
        // - วนลูปตราบใดที่ 3 - m ไม่เป็น 0
        // - ถ้างบน้อยกว่า 100 ก็จบ ไม่งั้นทำต่อ
        String strategy =
                "t = t + 3" +
                "t = t - 1" +
                "while (3 - m):" +
                "if (budget - 100) then {} else done" ;
        
        // สคริปต์ที่ปิดไว้ (ยังไม่ได้ใช้)
        // - เก็บตำแหน่งของศัตรู
        // - ถ้าตำแหน่งหาร 10 ลบ 1 เป็น 0 ก็เดินลงซ้าย
        // - ไม่งั้นถ้ามีศัตรูก็ยิงขึ้นด้วยพลัง 10
//                "opponentLoc = opponent" +
//                "if (opponentLoc / 10 - 1) then move downleft" +
//                "else if (opponentLoc) shoot up 10" +
//                "done";

        // สร้างตัวแยกคำและตัวแปลภาษา
        Tokenizer tokenizer = new SimpleTokenizer(strategy);
        Parser parser = new ProcessParse(tokenizer);

        // แปลงสคริปต์เป็นคำสั่งที่คอมพิวเตอร์เข้าใจ
        List<ExecuteNode> commands = parser.parse();

        // สร้างเกมใหม่และเริ่มเล่นด้วยผู้เล่น 2 คน
        GameService gameService = new GameService();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        gameService.startGame(player1, player2);

        // รันคำสั่งที่แปลงมาจากสคริปต์
        gameService.processCommands(commands);

        // เช็คว่าเกมจบรึยัง
        System.out.println("Game over: " + gameService.isGameOver());

        // เช็คค่าตัวแปร t และ m ว่าเป็นเท่าไหร่
        GameState gameState = GameState.getInstance();
        System.out.println("Value of t: " + gameState.getVariable("t"));  // ค่าเวลา
        System.out.println("Value of m: " + gameState.getVariable("m"));  // ค่าเงิน
    }
}
