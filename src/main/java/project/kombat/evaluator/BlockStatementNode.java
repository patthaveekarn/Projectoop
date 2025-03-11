package project.kombat.evaluator;

import project.kombat.parser.ExecuteNode;

import java.util.List;

public class BlockStatementNode implements ExecuteNode {
    private final List<ExecuteNode> statements;

    public BlockStatementNode(List<ExecuteNode> statements) {
        this.statements = statements;
    }

    @Override
    public void execute() {
        for (ExecuteNode statement : statements) {
            statement.execute();
        }
    }
}