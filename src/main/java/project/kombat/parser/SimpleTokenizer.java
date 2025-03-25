package project.kombat.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// คลาสนี้ทำหน้าที่แยกสคริปต์เป็นคำๆ (token) เช่น "move up" จะแยกเป็น ["move", "up"]
public class SimpleTokenizer implements Tokenizer {
    // สคริปต์ที่จะแยกคำ
    private final String input;
    
    // ตำแหน่งปัจจุบันที่กำลังอ่าน
    private int index;
    
    // ลิสต์ของคำที่แยกได้
    private List<String> tokens;
    
    // บรรทัดปัจจุบันที่กำลังอ่าน
    private int lineNumber;
    
    // คำสงวนที่ใช้ในภาษา (คำสั่งต่างๆ)
    private static final Set<String> RESERVED_WORDS = new HashSet<>();

    // เพิ่มคำสงวนทั้งหมดที่ใช้ในภาษา
    static {
        RESERVED_WORDS.add("ally");      // พวกเดียวกัน
        RESERVED_WORDS.add("done");      // จบ
        RESERVED_WORDS.add("down");      // ลง
        RESERVED_WORDS.add("downleft");  // ลงซ้าย
        RESERVED_WORDS.add("downright"); // ลงขวา
        RESERVED_WORDS.add("else");      // มิฉะนั้น
        RESERVED_WORDS.add("if");        // ถ้า
        RESERVED_WORDS.add("move");      // เดิน
        RESERVED_WORDS.add("nearby");    // ใกล้ๆ
        RESERVED_WORDS.add("opponent");  // ศัตรู
        RESERVED_WORDS.add("shoot");     // ยิง
        RESERVED_WORDS.add("then");      // แล้ว
        RESERVED_WORDS.add("up");        // ขึ้น
        RESERVED_WORDS.add("upleft");    // ขึ้นซ้าย
        RESERVED_WORDS.add("upright");   // ขึ้นขวา
        RESERVED_WORDS.add("while");     // ขณะที่
        RESERVED_WORDS.add("row");       // แถว
        RESERVED_WORDS.add("col");       // คอลัมน์
        RESERVED_WORDS.add("budget");    // งบประมาณ
        RESERVED_WORDS.add("int");       // จำนวนเต็ม
        RESERVED_WORDS.add("maxbudget"); // งบประมาณสูงสุด
        RESERVED_WORDS.add("spawnsleft"); // จำนวนมินเนี่ยนที่สร้างได้
        RESERVED_WORDS.add("random");    // สุ่ม
    }

    // สร้าง tokenizer ใหม่และแยกคำทันที
    public SimpleTokenizer(String input) {
        this.input = input;
        this.index = 0;
        this.tokens = new ArrayList<>();
        this.lineNumber = 1;
        tokenize();  // แยกคำทันทีที่สร้าง
    }

    // แยกสคริปต์เป็นคำๆ
    private void tokenize() {
        // แยกสคริปต์เป็นบรรทัด
        String[] lines = input.split("\n");
        
        for (String line : lines) {
            // ลบคอมเมนต์ออก (ข้อความหลัง #)
            int commentIndex = line.indexOf('#');
            if (commentIndex >= 0) {
                line = line.substring(0, commentIndex);
            }
            
            // ลบช่องว่างหัวท้าย ถ้าเป็นบรรทัดว่างก็ข้ามไป
            line = line.trim();
            if (line.isEmpty()) {
                lineNumber++;
                continue;
            }

            // แยกคำโดยใช้ regex ที่จับได้ทั้ง:
            // - ช่องว่าง
            // - ตัวแปร (ตัวอักษรตามด้วยตัวเลขได้)
            // - ตัวเลข
            // - เครื่องหมายคณิตศาสตร์
            // - วงเล็บและปีกกา
            // - เครื่องหมายเปรียบเทียบและอื่นๆ
            Matcher matcher = Pattern.compile(
                "\\s+|" +                     // ช่องว่าง
                "[a-zA-Z_][a-zA-Z0-9_]*|" +  // ตัวแปร
                "[0-9]+|" +                   // ตัวเลข
                "[+\\-*/%^=]|" +             // เครื่องหมายคณิตศาสตร์
                "[(){}]|" +                   // วงเล็บและปีกกา
                "<=|>=|==|!=|<|>|:"          // เครื่องหมายเปรียบเทียบและอื่นๆ
            ).matcher(line);
            
            // เก็บคำที่แยกได้ใส่ลิสต์
            while (matcher.find()) {
                String token = matcher.group().trim();
                if (!token.isEmpty()) {
                    tokens.add(token);
                }
            }
            
            lineNumber++;  // นับบรรทัด
        }
        
        System.out.println("Tokens: " + tokens);  // แสดงคำที่แยกได้ทั้งหมด
    }

    // เช็คว่ายังมีคำเหลืออยู่มั้ย
    @Override
    public boolean hasNextToken() {
        return index < tokens.size();
    }

    // ดูคำถัดไปโดยไม่เลื่อนตำแหน่ง
    @Override
    public String peek() {
        if (!hasNextToken()) {
            throw new SyntaxError("No more tokens", lineNumber);
        }
        return tokens.get(index);
    }

    // ดูคำที่อยู่ถัดไปอีกตัวโดยไม่เลื่อนตำแหน่ง
    @Override
    public String peekNext() {
        if (index + 1 >= tokens.size()) {
            throw new SyntaxError("No more tokens", lineNumber);
        }
        return tokens.get(index + 1);
    }

    // เอาคำถัดไปและเลื่อนตำแหน่ง
    @Override
    public String consume() {
        String token = peek();
        index++;
        return token;
    }

    // เช็คว่าคำถัดไปตรงกับที่คาดไว้มั้ย ถ้าตรงก็เลื่อนตำแหน่ง
    @Override
    public boolean consume(String expected) {
        if (!hasNextToken()) {
            return false;
        }
        String token = peek();
        if (token.equals(expected)) {
            index++;
            return true;
        }
        return false;
    }

    // ดูว่าตอนนี้อยู่บรรทัดที่เท่าไหร่
    @Override
    public int getNewline() {
        return lineNumber;
    }
}

