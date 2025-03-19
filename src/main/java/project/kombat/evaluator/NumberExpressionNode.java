package project.kombat.evaluator;

public class NumberExpressionNode implements ExpressionNode {
    private final long value;

    public NumberExpressionNode(long value) {
        this.value = value;
    }

    @Override
    public long evaluate() {
        return value;
    }
}