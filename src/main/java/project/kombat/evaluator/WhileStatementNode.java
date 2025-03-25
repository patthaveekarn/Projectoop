package project.kombat.evaluator;

import lombok.Getter;

@Getter
public class WhileStatementNode implements ExecuteNode {
    private final ExpressionNode condition;
    private final ExecuteNode body;

    public WhileStatementNode(ExpressionNode condition, ExecuteNode body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute() {
        while (condition.evaluate() != 0) {
            body.execute();
        }
    }
}