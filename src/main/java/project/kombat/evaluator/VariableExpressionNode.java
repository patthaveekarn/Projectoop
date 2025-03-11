package project.kombat.evaluator;

public class VariableExpressionNode implements ExpressionNode {
    private final String name;

    public VariableExpressionNode(String name) {
        this.name = name;
    }

    @Override
    public long evaluate() {
        return 0; // ควรปรับให้คืนค่าจริงจาก context
    }
}