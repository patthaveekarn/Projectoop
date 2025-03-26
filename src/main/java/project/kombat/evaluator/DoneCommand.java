package project.kombat.evaluator;

public class DoneCommand implements ExecuteNode {
    @Override
    public void execute() {
        // Logic สำหรับการจบเทิร์น
        System.out.println("Done");
    }
}