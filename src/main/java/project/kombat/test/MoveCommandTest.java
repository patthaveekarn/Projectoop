package project.kombat.test;

import org.junit.jupiter.api.Test;
import project.kombat.evaluator.DirectionNode;
import project.kombat.evaluator.MoveCommand;

class MoveCommandTest {

    @Test
    void testMoveCommand() {
        DirectionNode direction = DirectionNode.up;
        MoveCommand command = new MoveCommand(direction);
        command.execute();
    }
}

