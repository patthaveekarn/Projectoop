package project.kombat.parser;

import java.util.Arrays;
import java.util.List;

public class SimpleTokenizer implements Tokenizer {
    private final List<String> tokens;
    private int index;
    private int line;

    public SimpleTokenizer(String input) {
        this.tokens = Arrays.asList(input.split("\\s+"));
        this.index = 0;
        this.line = 1;
    }

    @Override
    public boolean hasNextToken() {
        return index < tokens.size();
    }

    @Override
    public String peek() {
        return hasNextToken() ? tokens.get(index) : null;
    }

    @Override
    public boolean peek(String regex) {
        return hasNextToken() && tokens.get(index).matches(regex);
    }

    @Override
    public String consume() {
        if (!hasNextToken()) {
            throw new SyntaxError("Unexpected end of input", line);
        }
        return tokens.get(index++);
    }

    @Override
    public boolean consume(String regex) {
        if (peek(regex)) {
            index++;
            return true;
        }
        return false;
    }

    @Override
    public int getNewline() {
        return line;
    }
}