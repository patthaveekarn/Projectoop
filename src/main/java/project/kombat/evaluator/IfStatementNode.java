package project.kombat.evaluator;

import project.kombat.parser.ExecuteNode;

public class IfStatementNode implements ExecuteNode {
    private final ExpressionNode condition;
    private final ExecuteNode trueBranch;
    private final ExecuteNode falseBranch;

    public IfStatementNode(ExpressionNode condition, ExecuteNode trueBranch, ExecuteNode falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public void execute() {
        if (condition.evaluate() > 0) {
            trueBranch.execute();
        } else {
            falseBranch.execute();
        }
    }
}