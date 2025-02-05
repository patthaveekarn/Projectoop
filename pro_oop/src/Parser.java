import java.util.*;

public class Parser {
    private Tokenizer tokenizer;

    public Parser(String code) {
        this.tokenizer = new Tokenizer(code);
    }

    public List<Statement> parse() {
        List<Statement> statements = new ArrayList<>();
        while (tokenizer.hasNext()) {
            statements.add(parseStatement());
        }
        return statements;
    }

    private Statement parseStatement() {
        String token = tokenizer.nextToken();

        if (token.equals("move")) {
            String dir = tokenizer.nextToken();
            return new MoveStatement(Direction.valueOf(dir.toUpperCase()));
        }
        else if (token.equals("shoot")) {
            String dir = tokenizer.nextToken();
            int power = Integer.parseInt(tokenizer.nextToken());
            return new ShootStatement(Direction.valueOf(dir.toUpperCase()), power);
        }
        else if (token.equals("if")) {
            tokenizer.nextToken(); // (
            String condition = tokenizer.nextToken();
            tokenizer.nextToken(); // )
            tokenizer.nextToken(); // then
            List<Statement> thenBlock = new ArrayList<>();
            while (!tokenizer.peek().equals("else")) {
                thenBlock.add(parseStatement());
            }
            tokenizer.nextToken(); // else
            List<Statement> elseBlock = new ArrayList<>();
            while (tokenizer.hasNext() && !tokenizer.peek().equals("if")) {
                elseBlock.add(parseStatement());
            }
            return new IfStatement(condition, thenBlock, elseBlock);
        }

        throw new RuntimeException("Unknown command: " + token);
    }
}
