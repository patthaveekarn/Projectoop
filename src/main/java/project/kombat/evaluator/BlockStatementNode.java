package project.kombat.evaluator;

import lombok.Getter;

import java.util.List;

public class BlockStatementNode implements ExecuteNode {
    @Getter
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