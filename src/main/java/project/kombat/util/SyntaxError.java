package project.kombat.util;

public class SyntaxError {

    public static class NodeException extends RuntimeException {
        public NodeException(String message) {
            super(message);
        }

        public static class LeftoverToken extends NodeException {
            public LeftoverToken(String token) {
                super("Leftover token: " + token);
            }
        }

        public static class InvalidExpression extends NodeException {
            public InvalidExpression(String expression, int line) {
                super("Invalid expression: " + expression + " at line " + line);
            }
        }

        public static class Command404 extends NodeException {
            public Command404(String command, int line) {
                super("Command 404: '" + command + "' at line " + line);
            }
        }

        public static class ReservedWord extends NodeException {
            public ReservedWord(String word, int line) {
                super("Reserved word used incorrectly: " + word + " at line " + line);
            }
        }

        public static class WrongDirection extends NodeException {
            public WrongDirection(String direction, int line) {
                super("Wrong direction: " + direction + " at line " + line);
            }
        }
    }
}
