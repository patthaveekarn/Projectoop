package project.kombat.model;

import lombok.Getter;
import project.kombat_3.evaluator.ExecuteNode;
import project.kombat_3.parser.*;

import java.util.List;

// คลาสนี้เก็บกลยุทธ์ของมินเนี่ยน เป็นสคริปต์ที่บอกว่ามินเนี่ยนจะทำอะไรในแต่ละเทิร์น
@Getter
public class MinionStrategy {
    // ชื่อของกลยุทธ์
    private final String name;
    
    // ค่าการป้องกันที่จะให้มินเนี่ยน
    private final long defense;
    
    // สคริปต์ที่เขียนเป็นโค้ดบอกว่าจะให้มินเนี่ยนทำอะไร
    private final String strategyScript;
    
    // คำสั่งที่แปลงจากสคริปต์แล้ว พร้อมจะเอาไปรัน
    private List<ExecuteNode> commands;

    // สร้างกลยุทธ์ใหม่ด้วยชื่อ ค่าการป้องกัน และสคริปต์
    public MinionStrategy(String name, long defense, String strategyScript) {
        this.name = name;
        this.defense = defense;
        this.strategyScript = strategyScript;
        this.commands = null;  // ยังไม่ได้แปลงสคริปต์
    }

    // แปลงสคริปต์เป็นคำสั่งที่คอมพิวเตอร์เข้าใจ
    public void compile() {
        // สร้างตัวแยกคำและตัวแปลภาษา
        Tokenizer tokenizer = new SimpleTokenizer(strategyScript);
        Parser parser = new ProcessParse(tokenizer);

        // แปลงสคริปต์เป็นคำสั่งที่รันได้
        this.commands = parser.parse();
    }

    // รันคำสั่งที่แปลงไว้ ถ้ายังไม่ได้แปลงก็แปลงก่อน
    public void execute() {
        if (commands == null) {
            compile();  // แปลงสคริปต์ก่อนถ้ายังไม่ได้แปลง
        }

        // TODO: ดำเนินการคำสั่งผ่าน GameService (ยังไม่ได้ทำ)
    }
}