package project.kombat.evaluator;

import project.kombat.parser.ExecuteNode;

public class MoveCommand implements ExecuteNode {
    private final DirectionNode direction;

    public MoveCommand(DirectionNode direction) {
        this.direction = direction;
    }

    @Override
    public void execute() {
        // Logic สำหรับการเคลื่อนที่มินิออน
        System.out.println("Moving " + direction);
    }
}