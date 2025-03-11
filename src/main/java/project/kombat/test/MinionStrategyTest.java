package project.kombat.test;

import project.kombat.parser.ExecuteNode;
import project.kombat.parser.ProcessParse;
import project.kombat.parser.SimpleTokenizer;

import java.util.List;

public class MinionStrategyTest {
    public static void main(String[] args) {
        // กลยุทธ์ของมินิออน
        String strategy = "t = t + 1\n" +
                "m = 0\n" +
                "while (3 - m) {\n" +
                "    if (budget - 100) then {} else done\n" +
                "    opponentLoc = opponent\n" +
                "    if (opponentLoc / 10 - 1) then\n" +
                "        if (opponentLoc % 10 - 5) then move downleft\n" +
                "        else if (opponentLoc % 10 - 4) then move down\n" +
                "        else if (opponentLoc % 10 - 3) then move downright\n" +
                "        else if (opponentLoc % 10 - 2) then move right\n" +
                "        else if (opponentLoc % 10 - 1) then move upright\n" +
                "        else move up\n" +
                "    else if (opponentLoc) then\n" +
                "        if (opponentLoc % 10 - 5) then {\n" +
                "            cost = 10 ^ (nearby upleft % 100 + 1)\n" +
                "            if (budget - cost) then shoot upleft cost else {}\n" +
                "        }\n" +
                "        else if (opponentLoc % 10 - 4) then {\n" +
                "            cost = 10 ^ (nearby downleft % 100 + 1)\n" +
                "            if (budget - cost) then shoot downleft cost else {}\n" +
                "        }\n" +
                "        else if (opponentLoc % 10 - 3) then {\n" +
                "            cost = 10 ^ (nearby down % 100 + 1)\n" +
                "            if (budget - cost) then shoot down cost else {}\n" +
                "        }\n" +
                "        else if (opponentLoc % 10 - 2) then {\n" +
                "            cost = 10 ^ (nearby downright % 100 + 1)\n" +
                "            if (budget - cost) then shoot downright cost else {}\n" +
                "        }\n" +
                "        else if (opponentLoc % 10 - 1) then {\n" +
                "            cost = 10 ^ (nearby upright % 100 + 1)\n" +
                "            if (budget - cost) then shoot upright cost else {}\n" +
                "        }\n" +
                "        else {\n" +
                "            cost = 10 ^ (nearby up % 100 + 1)\n" +
                "            if (budget - cost) then shoot up cost else {}\n" +
                "        }\n" +
                "    else {\n" +
                "        try = 0\n" +
                "        while (3 - try) {\n" +
                "            success = 1\n" +
                "            dir = random % 6\n" +
                "            if ((dir - 4) * (nearby upleft % 10 + 1) ^ 2) then move upleft\n" +
                "            else if ((dir - 3) * (nearby downleft % 10 + 1) ^ 2) then move downleft\n" +
                "            else if ((dir - 2) * (nearby down % 10 + 1) ^ 2) then move down\n" +
                "            else if ((dir - 1) * (nearby downright % 10 + 1) ^ 2) then move downright\n" +
                "            else if (dir * (nearby upright % 10 + 1) ^ 2) then move upright\n" +
                "            else if ((nearby up % 10 + 1) ^ 2) then move up\n" +
                "            else success = 0\n" +
                "            if (success) then try = 3 else try = try + 1\n" +
                "        }\n" +
                "        m = m + 1\n" +
                "    }\n" +
                "}";

        // สร้าง Tokenizer และ Parser
        SimpleTokenizer tokenizer = new SimpleTokenizer(strategy);
        ProcessParse parser = new ProcessParse(tokenizer);

        // แยกและประเมินกลยุทธ์
        List<ExecuteNode> commands = parser.parse();

        // รันคำสั่ง
        for (ExecuteNode command : commands) {
            command.execute();
        }
    }
}