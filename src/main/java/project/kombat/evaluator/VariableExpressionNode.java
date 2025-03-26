package project.kombat.evaluator;

import project.kombat.model.GameState;

public class VariableExpressionNode implements ExpressionNode {
    private final String name;

    public VariableExpressionNode(String name) {
        this.name = name;
    }

    @Override
    public long evaluate() {
        GameState gameState = GameState.getInstance();  // ดึงค่า instance ของ GameState
        return gameState.getVariable(name);  // ดึงค่าตัวแปรจาก GameState
    }
}
