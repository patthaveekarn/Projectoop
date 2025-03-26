package project.kombat.Controller;

import project.kombat.evaluator.*;
import project.kombat.model.*;
import project.kombat.parser.*;

import java.util.List;

public class GameTest {
    public static void main(String[] args) {

        String strategy =
                "t = t + 3" +
                "t = t - 1" +
                "while (3 - m):" +
                "if (budget - 100) then {} else done" ;

        Tokenizer tokenizer = new SimpleTokenizer(strategy);
        Parser parser = new ProcessParse(tokenizer);


        List<ExecuteNode> commands = parser.parse();


        GameService gameService = new GameService();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        gameService.startGame(player1, player2);


        gameService.processCommands(commands);


        System.out.println("Game over: " + gameService.isGameOver());


        GameState gameState = GameState.getInstance();
        System.out.println("Value of t: " + gameState.getVariable("t"));  // ค่าเวลา
        System.out.println("Value of m: " + gameState.getVariable("m"));  // ค่าเงิน
    }
}
