package project.kombat.evaluator;

public class BinaryArithmeticNode implements ExpressionNode {
    private final ExpressionNode left;
    private final String operator;
    private final ExpressionNode right;

    public BinaryArithmeticNode(ExpressionNode left, String operator, ExpressionNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public long evaluate() {
        long leftValue = left.evaluate();
        long rightValue = right.evaluate();
        switch (operator) {
            case "+": return leftValue + rightValue;
            case "-": return leftValue - rightValue;
            case "*": return leftValue * rightValue;
            case "/": return leftValue / rightValue;
            case "%": return leftValue % rightValue;
            case "^": return (long) Math.pow(leftValue, rightValue);
            default: throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}