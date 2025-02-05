import java.util.*;
import java.util.regex.*;

public class Tokenizer {
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "\\b(done|move|shoot|if|else|while|then|\\d+|\\w+|[-+*/^=(){}<>%!])\\b"
    );

    private List<String> tokens;
    private int index = 0;

    public Tokenizer(String code) {
        Matcher matcher = TOKEN_PATTERN.matcher(code);
        tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
    }

    public String nextToken() {
        return hasNext() ? tokens.get(index++) : null;
    }

    public String peek() {
        return hasNext() ? tokens.get(index) : null;
    }

    public boolean hasNext() {
        return index < tokens.size();
    }
}
