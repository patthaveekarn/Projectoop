package project.kombat.strategy.Parse;

import Expression.ExpressionNode;
import Tokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessParse implements Parser {
    protected final Tokenizer tkz;

    // คำสั่งที่รองรับใน KOMBAT
    private final List<String> commands = Arrays.asList("done", "move", "shoot");

    // คำที่จองไว้ (Reserved Words)
    private final List<String> reserved = Arrays.asList(
            "done", "move", "shoot", "if", "else", "while", "then",
            "row", "col", "budget", "int", "maxbudget", "spawnsleft", "random",
            "up", "down", "upleft", "upright", "downleft", "downright"
    );

    public ProcessParse(Tokenizer tkz) {
        if (!tkz.hasNextToken()) {
            throw new SyntaxError.StateRequire(tkz.getNewline());
        }
        this.tkz = tkz;
    }

    public List<ExecuteNode> parse() {
        List<ExecuteNode> statements = parsePlan();
        if (tkz.hasNextToken()) {
            throw new NodeException.LeftoverToken(tkz.peek());
        }
        return statements;
    }

    // Plan → Statement+
    private List<ExecuteNode> parsePlan() {
        List<ExecuteNode> plan = new ArrayList<>();
        while (tkz.hasNextToken()) {
            plan.add(parseStatement());
        }
        return plan;
    }

    // Statement → Command | BlockStatement | IfStatement | WhileStatement
    private ExecuteNode parseStatement() {
        if (tkz.peek("if")) {
            return parseIfStatement();
        } else if (tkz.peek("while")) {
            return parseWhileStatement();
        } else if (tkz.peek("{")) {
            return parseBlockStatement();
        } else {
            return parseCommand();
        }
    }

    private ExecuteNode parseBlockStatement() {
        List<ExecuteNode> statements = new ArrayList<>();
        tkz.consume("{");
        while (!tkz.peek("}") && tkz.hasNextToken()) {
            statements.add(parseStatement());
        }
        tkz.consume("}");
        return new BlockStatementNode(statements);
    }

    private ExecuteNode parseWhileStatement() {
        tkz.consume("while");
        tkz.consume("(");
        ExpressionNode condition = parseExpression();
        tkz.consume(")");
        ExecuteNode body = parseStatement();
        return new WhileStatementNode(condition, body);
    }

    private ExecuteNode parseIfStatement() {
        tkz.consume("if");
        tkz.consume("(");
        ExpressionNode condition = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        ExecuteNode trueBranch = parseStatement();
        tkz.consume("else");
        ExecuteNode falseBranch = parseStatement();
        return new IfStatementNode(condition, trueBranch, falseBranch);
    }

    private ExecuteNode parseCommand() {
        if (commands.contains(tkz.peek())) {
            return parseActionCommand();
        } else {
            return parseAssignmentStatement();
        }
    }

    // AssignmentStatement → <identifier> = Expression
    private ExecuteNode parseAssignmentStatement() {
        String identifier = parseIdentifier();

        if (reserved.contains(identifier)) {
            throw new SyntaxError.ReservedWord(identifier, tkz.getNewline());
        }

        if (!tkz.peek("=")) {
            throw new SyntaxError.Command404(identifier, tkz.getNewline());
        }

        tkz.consume("="); // Consume '='
        ExpressionNode expression = parseExpression();
        return new AssignmentStatementNode(identifier, expression);
    }

    private String parseIdentifier() {
        return tkz.consume();
    }

    // ActionCommand → DoneCommand | MoveCommand | ShootCommand
    private ExecuteNode parseActionCommand() {
        String command = tkz.consume();

        switch (command) {
            case "done":
                return new DoneCommand();
            case "move":
                return parseMoveCommand();
            case "shoot":
                return parseShootCommand();
            default:
                throw new SyntaxError.CommandIsFail(command, tkz.getNewline());
        }
    }

    // MoveCommand → move Direction
    private ExecuteNode parseMoveCommand() {
        DirectionNode direction = parseDirection();
        return new MoveCommand(direction);
    }

    // ShootCommand → shoot Direction Expression
    private ExecuteNode parseShootCommand() {
        DirectionNode direction = parseDirection();
        ExpressionNode power = parseExpression();
        return new AttackCommand(direction, power);
    }

    // Direction → up | down | upleft | upright | downleft | downright
    private DirectionNode parseDirection() {
        String dir = tkz.consume();
        switch (dir) {
            case "up":
                return DirectionNode.up;
            case "upleft":
                return DirectionNode.upleft;
            case "upright":
                return DirectionNode.upright;
            case "down":
                return DirectionNode.down;
            case "downleft":
                return DirectionNode.downleft;
            case "downright":
                return DirectionNode.downright;
            default:
                throw new SyntaxError.WrongDirection(dir, tkz.getNewline());
        }
    }

    // Expression → Expression + Term | Expression - Term | Term
    private ExpressionNode parseExpression() {
        ExpressionNode left = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            String op = tkz.consume();
            ExpressionNode right = parseTerm();
            left = new BinaryArithmeticNode(left, op, right);
        }
        return left;
    }

    // Term → Term * Factor | Term / Factor | Term % Factor | Factor
    private ExpressionNode parseTerm() {
        ExpressionNode left = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            String op = tkz.consume();
            ExpressionNode right = parseFactor();
            left = new BinaryArithmeticNode(left, op, right);
        }
        return left;
    }

    // Factor → Power ^ Factor | Power
    private ExpressionNode parseFactor() {
        ExpressionNode left = parsePower();
        if (tkz.peek("^")) {
            String op = tkz.consume();
            ExpressionNode right = parseFactor();
            left = new BinaryArithmeticNode(left, op, right);
        }
        return left;
    }

    // Power → <number> | <identifier> | ( Expression ) | InfoExpression
    private ExpressionNode parsePower() {
        if (Character.isDigit(tkz.peek().charAt(0))) {
            return new VariableExpressionNode(Integer.parseInt(tkz.consume()));
        } else if (reserved.contains(tkz.peek())) {
            return new VariableExpressionNode(tkz.consume());
        } else {
            throw new SyntaxError.InvalidExpression(tkz.peek(), tkz.getNewline());
        }
    }
}
