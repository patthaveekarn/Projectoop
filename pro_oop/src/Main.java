import java.util.List;

public class Main {
    public static void main(String[] args) {
        String strategyCode = "move up shoot down 10 if (budget > 100) then move right else move left";
        Parser parser = new Parser(strategyCode);

        List<Statement> strategy = parser.parse();
        GameState gameState = new GameState();
        Minion minion = new Minion(1, "Warrior", 0, 0, 100, 10, null);

        for (Statement stmt : strategy) {
            stmt.execute(gameState, minion);
        }
    }
}
