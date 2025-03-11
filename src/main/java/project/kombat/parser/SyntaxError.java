package project.kombat.parser;

public class SyntaxError extends RuntimeException {
    public SyntaxError(String message, int line) {
        super(String.format("%s at line %d", message, line));
    }

    public static class StateRequire extends SyntaxError {
        public StateRequire(int line) {
            super("Need at least one state", line);
        }
    }

    public static class Command404 extends SyntaxError {
        public Command404(String command, int line) {
            super(String.format("Command '%s' not found", command), line);
        }
    }

    public static class ReservedWord extends SyntaxError {
        public ReservedWord(String identifier, int line) {
            super(String.format("Reserved word '%s' cannot be used as an identifier", identifier), line);
        }
    }

    public static class CommandIsFail extends SyntaxError {
        public CommandIsFail(String command, int line) {
            super(String.format("Command '%s' failed", command), line);
        }
    }

    public static class WrongDirection extends SyntaxError {
        public WrongDirection(String direction, int line) {
            super(String.format("Wrong direction '%s'", direction), line);
        }
    }

    public static class InvalidExpression extends SyntaxError {
        public InvalidExpression(String expression, int line) {
            super(String.format("Invalid expression '%s'", expression), line);
        }
    }

    public static class LeftoverToken extends SyntaxError {
        public LeftoverToken(String token, int line) {
            super(String.format("Leftover token '%s'", token), line);
        }
    }
}