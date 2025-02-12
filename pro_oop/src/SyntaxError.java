
public  class SyntaxError extends RuntimeException{
    public SyntaxError(String s, int line){
        super(String.format("%s at line %d", s, line));
    }

    public static class StateRequire extends SyntaxError {
        public StateRequire(int line){
            super("need least one state", line);
        }
    }

    public static class Command404 extends SyntaxError {
        public Command404(String command, int line){
            super(String.format("command '%s' 404", command), line);
        }
    }

    public static class ReservedWord extends SyntaxError {
        public ReservedWord(String identifier, int line){
            super(String.format("reservedWord '%s'", identifier), line);
        }
    }

    public static class CommandIsFail extends SyntaxError {
        public CommandIsFail(String command, int line){
            super(String.format("command '%s' IsFail", command), line);
        }
    }

    public static class WrongDirection extends SyntaxError {
        public WrongDirection(String direction, int line) {
            super(String.format("Wrong direction '%s'", direction), line);
        }
    }

    public static class  WrongInfoExpression extends SyntaxError {
        public  WrongInfoExpression(String expressionNode, int line) {
            super(String.format("Wrong InfoExpression '%s'", expressionNode), line);
        }
    }
}