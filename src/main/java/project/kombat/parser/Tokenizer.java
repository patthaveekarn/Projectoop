package project.kombat.parser;

public interface Tokenizer {
    boolean hasNextToken();
    String peek();
    boolean peek(String regex);
    String consume();
    boolean consume(String regex);
    int getNewline();
}