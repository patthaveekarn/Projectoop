package project.kombat.parser;

import project.kombat.evaluator.ExecuteNode;

import java.util.List;

public interface Parser {
    List<ExecuteNode> parse();  // จะคืนค่ารายการของ ExecuteNode ที่แสดงถึงคำสั่งที่แยกออกมา
}

