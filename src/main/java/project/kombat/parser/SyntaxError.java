package project.kombat.parser;

// คลาสนี้เก็บ error ทั้งหมดที่อาจเกิดขึ้นตอนแยกคำและแปลภาษา
public class SyntaxError extends RuntimeException {
    // constructor หลัก รับข้อความ error และเลขบรรทัดที่เกิด error
    public SyntaxError(String message, int line) {
        super(String.format("%s at line %d", message, line));
    }

    // error เมื่อไม่มี state ในโค้ด (ต้องมีอย่างน้อย 1 state)
    public static class StateRequire extends SyntaxError {
        public StateRequire(int line) {
            super("Need at least one state", line);
        }
    }

    // error เมื่อไม่เจอคำสั่งที่ระบุ
    public static class Command404 extends SyntaxError {
        public Command404(String command, int line) {
            super(String.format("Command '%s' not found", command), line);
        }
    }

    // error เมื่อพยายามใช้คำสงวนเป็นชื่อตัวแปร
    public static class ReservedWord extends SyntaxError {
        public ReservedWord(String identifier, int line) {
            super(String.format("Reserved word '%s' cannot be used as an identifier", identifier), line);
        }
    }

    // error เมื่อคำสั่งทำงานไม่สำเร็จ
    public static class CommandIsFail extends SyntaxError {
        public CommandIsFail(String command, int line) {
            super(String.format("Command '%s' failed", command), line);
        }
    }

    // error เมื่อระบุทิศทางไม่ถูกต้อง
    public static class WrongDirection extends SyntaxError {
        public WrongDirection(String direction, int line) {
            super(String.format("Wrong direction '%s'", direction), line);
        }
    }

    // error เมื่อนิพจน์ไม่ถูกต้องตามไวยากรณ์
    public static class InvalidExpression extends SyntaxError {
        public InvalidExpression(String expression, int line) {
            super(String.format("Invalid expression '%s'", expression), line);
        }
    }

    // error เมื่อมีโทเค็นเหลือที่ไม่ได้ใช้
    public static class LeftoverToken extends SyntaxError {
        public LeftoverToken(String token, int line) {
            super(String.format("Leftover token '%s'", token), line);
        }
    }
}
