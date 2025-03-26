package project.kombat.evaluator;

import lombok.Getter;
import project.kombat.model.GameState;

public class AssignmentStatementNode implements ExecuteNode {
    @Getter
    private final String identifier;  // ตัวแปรที่ถูกกำหนดค่า
    private final ExpressionNode expression;  // นิพจน์ที่ใช้กำหนดค่า

    public AssignmentStatementNode(String identifier, ExpressionNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void execute() {
        long value = expression.evaluate();  // Evaluate the expression
        GameState.getInstance().setVariable(identifier, value);  // Set the variable in GameState
        System.out.println("AssignedStatement " + identifier + " = " + value);
    }
}

