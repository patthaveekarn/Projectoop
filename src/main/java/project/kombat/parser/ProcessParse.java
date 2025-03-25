package project.kombat.parser;

import project.kombat_3.evaluator.*;

import java.util.ArrayList;
import java.util.List;

// คลาสนี้ทำหน้าที่แปลงสคริปต์เป็นคำสั่งที่คอมพิวเตอร์เข้าใจ โดยใช้หลักการ recursive descent parsing
public class ProcessParse implements Parser {
    // ตัวแยกคำในสคริปต์
    private final Tokenizer tokenizer;

    // สร้าง parser ใหม่ด้วย tokenizer ที่ให้มา
    public ProcessParse(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    // แปลงสคริปต์ทั้งหมดเป็นลิสต์ของคำสั่ง
    @Override
    public List<ExecuteNode> parse() {
        List<ExecuteNode> statements = new ArrayList<>();

        // วนลูปแปลงทีละคำสั่งจนกว่าจะหมดสคริปต์
        while (tokenizer.hasNextToken()) {
            String token = tokenizer.peek();
            if ("move".equals(token)) {
                statements.add(parseMoveCommand());  // คำสั่งเดิน
            } else if ("shoot".equals(token)) {
                statements.add(parseShootCommand());  // คำสั่งยิง
            } else if ("done".equals(token)) {
                statements.add(new DoneCommand());  // คำสั่งจบ
                tokenizer.consume();
            } else if ("while".equals(token)) {
                statements.add(parseWhileStatement());  // คำสั่งวนลูป
            } else if ("if".equals(token)) {
                statements.add(parseIfStatement());  // คำสั่งเงื่อนไข
            } else if ("{".equals(token)) {
                statements.add(parseBlockStatement());  // บล็อกของคำสั่ง
            } else {
                statements.add(parseAssignmentStatement());  // คำสั่งกำหนดค่า
            }
        }

        return statements;
    }

    // แปลงคำสั่งกำหนดค่า (เช่น x = 5)
    private ExecuteNode parseAssignmentStatement() {
        String identifier = tokenizer.consume();  // ชื่อตัวแปร
        tokenizer.consume("=");  // เครื่องหมายเท่ากับ
        ExpressionNode expression = parseExpression();  // นิพจน์ที่จะกำหนดค่า
        return new AssignmentStatementNode(identifier, expression);
    }

    // แปลงคำสั่งเดิน (เช่น move up)
    private ExecuteNode parseMoveCommand() {
        tokenizer.consume();  // กิน move
        String directionToken = tokenizer.consume();  // ทิศทาง
        DirectionNode direction = DirectionNode.fromString(directionToken);
        return new MoveCommand(direction);
    }

    // แปลงคำสั่งยิง (เช่น shoot up 10)
    private ExecuteNode parseShootCommand() {
        tokenizer.consume();  // กิน shoot
        String directionToken = tokenizer.consume();  // ทิศทาง
        DirectionNode direction = DirectionNode.fromString(directionToken);
        ExpressionNode power = new NumberExpressionNode(Long.parseLong(tokenizer.consume()));  // พลังยิง
        return new ShootCommand(direction, power);
    }

    // แปลงคำสั่งวนลูป (เช่น while (x > 0) move up)
    private ExecuteNode parseWhileStatement() {
        tokenizer.consume();  // กิน while
        tokenizer.consume("(");  // วงเล็บเปิด
        ExpressionNode condition = parseExpression();  // เงื่อนไข
        tokenizer.consume(")");  // วงเล็บปิด
        ExecuteNode body = parseStatement();  // คำสั่งที่จะทำวนลูป
        return new WhileStatementNode(condition, body);
    }

    // แปลงคำสั่งทั่วไป (ใช้ในคำสั่ง while และ if)
    private ExecuteNode parseStatement() {
        String token = tokenizer.peek();
        if ("move".equals(token)) {
            return parseMoveCommand();
        } else if ("shoot".equals(token)) {
            return parseShootCommand();
        } else if ("done".equals(token)) {
            tokenizer.consume();
            return new DoneCommand();
        } else if ("while".equals(token)) {
            return parseWhileStatement();
        } else if ("{".equals(token)) {
            return parseBlockStatement();
        } else if (token.matches("[a-zA-Z][a-zA-Z0-9]*") && tokenizer.peekNext().equals("=")) {
            return parseAssignmentStatement();
        } else {
            throw new SyntaxError("Invalid statement: " + token, tokenizer.getNewline());
        }
    }

    // แปลงคำสั่งเงื่อนไข (เช่น if (x > 0) then move up else shoot down 10)
    private ExecuteNode parseIfStatement() {
        tokenizer.consume();  // กิน if
        tokenizer.consume("(");  // วงเล็บเปิด
        ExpressionNode condition = parseExpression();  // เงื่อนไข
        tokenizer.consume(")");  // วงเล็บปิด
        tokenizer.consume("then");  // then
        ExecuteNode trueBranch = parseStatement();  // คำสั่งถ้าเงื่อนไขเป็นจริง
        tokenizer.consume("else");  // else
        ExecuteNode falseBranch = parseStatement();  // คำสั่งถ้าเงื่อนไขเป็นเท็จ
        return new IfStatementNode(condition, trueBranch, falseBranch);
    }

    // แปลงบล็อกของคำสั่ง (เช่น { move up shoot down 10 })
    private ExecuteNode parseBlockStatement() {
        tokenizer.consume("{");  // ปีกกาเปิด
        List<ExecuteNode> statements = new ArrayList<>();
        
        // เก็บคำสั่งทั้งหมดในบล็อกจนกว่าจะเจอปีกกาปิด
        while (tokenizer.hasNextToken() && !"}".equals(tokenizer.peek())) {
            statements.add(parseStatement());
        }
        
        tokenizer.consume("}");  // ปีกกาปิด
        return new BlockStatementNode(statements);
    }

    // แปลงนิพจน์ข้อมูล (เช่น ally, opponent, nearby up)
    private ExpressionNode parseInfoExpression() {
        String token = tokenizer.consume();
        if ("ally".equals(token)) {
            return new InfoExpressionNode("ally", DirectionNode.UP);  // ข้อมูลพวกเดียวกัน
        } else if ("opponent".equals(token)) {
            return new InfoExpressionNode("opponent", DirectionNode.UP);  // ข้อมูลศัตรู
        } else if ("nearby".equals(token)) {
            String directionToken = tokenizer.consume();  // ทิศทาง
            DirectionNode direction = DirectionNode.fromString(directionToken);
            return new InfoExpressionNode("nearby", direction);  // ข้อมูลรอบๆ
        } else {
            throw new SyntaxError("Invalid info expression: " + token, tokenizer.getNewline());
        }
    }

    // แปลงนิพจน์ทั่วไป (บวก ลบ)
    private ExpressionNode parseExpression() {
        ExpressionNode left = parseTerm();  // เทอมซ้าย
        while (tokenizer.hasNextToken()) {
            String token = tokenizer.peek();
            if ("+".equals(token) || "-".equals(token)) {
                tokenizer.consume();  // กินเครื่องหมาย
                String operator = token;
                ExpressionNode right = parseTerm();  // เทอมขวา
                left = new BinaryArithmeticNode(left, operator, right);
            } else {
                break;
            }
        }
        return left;
    }

    // แปลงเทอม (คูณ หาร มอดูโล)
    private ExpressionNode parseTerm() {
        ExpressionNode left = parseFactor();  // แฟกเตอร์ซ้าย
        while (tokenizer.hasNextToken()) {
            String token = tokenizer.peek();
            if ("*".equals(token) || "/".equals(token) || "%".equals(token)) {
                tokenizer.consume();  // กินเครื่องหมาย
                String operator = token;
                ExpressionNode right = parseFactor();  // แฟกเตอร์ขวา
                left = new BinaryArithmeticNode(left, operator, right);
            } else {
                break;
            }
        }
        return left;
    }

    // แปลงแฟกเตอร์ (ยกกำลัง)
    private ExpressionNode parseFactor() {
        ExpressionNode factor = parsePower();  // ตัวตั้ง
        if (tokenizer.hasNextToken() && "^".equals(tokenizer.peek())) {
            tokenizer.consume();  // กินเครื่องหมาย ^
            ExpressionNode exponent = parseFactor();  // เลขชี้กำลัง
            factor = new BinaryArithmeticNode(factor, "^", exponent);
        }
        return factor;
    }

    // แปลงพาวเวอร์ (ตัวเลข ตัวแปร วงเล็บ ข้อมูล)
    private ExpressionNode parsePower() {
        String token = tokenizer.peek();
        if ("ally".equals(token) || "opponent".equals(token) || "nearby".equals(token)) {
            return parseInfoExpression();  // ข้อมูลพิเศษ
        }
        
        token = tokenizer.consume();
        if (isNumeric(token)) {
            return new NumberExpressionNode(Long.parseLong(token));  // ตัวเลข
        } else if (token.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            return new VariableExpressionNode(token);  // ตัวแปร
        } else if ("(".equals(token)) {
            ExpressionNode expression = parseExpression();  // นิพจน์ในวงเล็บ
            tokenizer.consume(")");  // วงเล็บปิด
            return expression;
        } else {
            throw new SyntaxError("Invalid token for power: " + token, tokenizer.getNewline());
        }
    }

    // เช็คว่าสตริงเป็นตัวเลขมั้ย
    private boolean isNumeric(String token) {
        try {
            Long.parseLong(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
