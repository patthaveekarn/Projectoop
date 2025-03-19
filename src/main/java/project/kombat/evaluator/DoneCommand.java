package project.kombat.evaluator;

import project.kombat.parser.ExecuteNode;

public class DoneCommand implements ExecuteNode {
    @Override
    public void execute() {
        // Logic สำหรับการจบเทิร์น
        System.out.println("Done");
    }
}