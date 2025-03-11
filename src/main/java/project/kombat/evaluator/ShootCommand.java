package project.kombat.evaluator;

import project.kombat.parser.ExecuteNode;

public class ShootCommand implements ExecuteNode {
    private final DirectionNode direction;
    private final ExpressionNode power;

    public ShootCommand(DirectionNode direction, ExpressionNode power) {
        this.direction = direction;
        this.power = power;
    }

    @Override
    public void execute() {
        // Logic สำหรับการโจมตีมินิออน
        System.out.println("Shooting " + direction + " with power " + power.evaluate());
    }
}