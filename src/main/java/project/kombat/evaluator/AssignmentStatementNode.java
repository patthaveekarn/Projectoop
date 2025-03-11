package project.kombat.evaluator;

import project.kombat.parser.ExecuteNode;

public class AssignmentStatementNode implements ExecuteNode {
    private final String identifier;
    private final ExpressionNode expression;

    public AssignmentStatementNode(String identifier, ExpressionNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void execute() {
        // Logic สำหรับการกำหนดค่า
        System.out.println("Assigning " + expression.evaluate() + " to " + identifier);
    }
}