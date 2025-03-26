package project.kombat.parser;

public interface Tokenizer {

    boolean hasNextToken();

    String peek();

    String peekNext();

    String consume();

    boolean consume(String regex);

    int getNewline();
}
