import java.util.*;

abstract class Statement {
    abstract void execute(GameState gameState, Minion minion);
}

class MoveStatement extends Statement {
    private Direction direction;

    public MoveStatement(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void execute(GameState gameState, Minion minion) {
        new MoveAction(direction).execute(gameState, minion);
    }
}

class ShootStatement extends Statement {
    private Direction direction;
    private int power;

    public ShootStatement(Direction direction, int power) {
        this.direction = direction;
        this.power = power;
    }

    @Override
    public void execute(GameState gameState, Minion minion) {
        new ShootAction(direction, power).execute(gameState, minion);
    }
}

class IfStatement extends Statement {
    private String condition;
    private List<Statement> thenBlock;
    private List<Statement> elseBlock;

    public IfStatement(String condition, List<Statement> thenBlock, List<Statement> elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public void execute(GameState gameState, Minion minion) {
        boolean result = evaluateCondition(condition, gameState, minion);
        List<Statement> block = result ? thenBlock : elseBlock;
        for (Statement stmt : block) {
            stmt.execute(gameState, minion);
        }
    }

    private boolean evaluateCondition(String condition, GameState gameState, Minion minion) {
        return condition.equals("budget > 100") && gameState.getBudget() > 100;
    }
}
