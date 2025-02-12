import java.util.*;
import java.util.regex.*;

class MinionEvaluator {
    private Map<String, Long> variables = new HashMap<>();
    private static final Random random = new Random();
    private static final Pattern ASSIGNMENT_PATTERN = Pattern.compile("(?<var>[a-zA-Z][a-zA-Z0-9]*)\\s*=\\s*(?<expr>.+)");
    private static final Pattern IF_PATTERN = Pattern.compile("if\\s*\\((?<cond>.+)\\)\\s*then\\s*(?<thenStmt>.+)\\s*else\\s*(?<elseStmt>.+)");
    private static final Pattern WHILE_PATTERN = Pattern.compile("while\\s*\\((?<cond>.+)\\)\\s*(?<body>.+)");
    private static final Pattern MOVE_PATTERN = Pattern.compile("move\\s+(?<dir>up|down|upleft|upright|downleft|downright)");
    private static final Pattern SHOOT_PATTERN = Pattern.compile("shoot\\s+(?<dir>up|down|upleft|upright|downleft|downright)\\s+(?<expr>.+)");
    private static final Pattern DONE_PATTERN = Pattern.compile("done");
    private static final Pattern INFO_PATTERN = Pattern.compile("(?<expr>ally|opponent|nearby\\s+(?<dir>up|down|upleft|upright|downleft|downright))");

    public void evaluate(String script) {
        String[] lines = script.split("\\n");
        for (String line : lines) {
            line = line.split("#")[0].trim();
            if (!line.isEmpty()) {
                execute(line);
            }
        }
    }

    private void execute(String line) {
        Matcher match;
        if ((match = ASSIGNMENT_PATTERN.matcher(line)).matches()) {
            executeAssignment(match);
        } else if ((match = IF_PATTERN.matcher(line)).matches()) {
            executeIf(match);
        } else if ((match = WHILE_PATTERN.matcher(line)).matches()) {
            executeWhile(match);
        } else if ((match = MOVE_PATTERN.matcher(line)).matches()) {
            executeMove(match);
        } else if ((match = SHOOT_PATTERN.matcher(line)).matches()) {
            executeShoot(match);
        } else if ((match = INFO_PATTERN.matcher(line)).matches()) {
            executeInfo(match);
        } else if (DONE_PATTERN.matcher(line).matches()) {
            executeDone();
        } else {
            throw new IllegalArgumentException("Invalid statement: " + line);
        }
    }

    private void executeAssignment(Matcher match) {
        String var = match.group("var");
        long value = evaluateExpression(match.group("expr"));
        variables.put(var, value);
    }

    private void executeIf(Matcher match) {
        long cond = evaluateExpression(match.group("cond"));
        if (cond > 0) {
            execute(match.group("thenStmt"));
        } else {
            execute(match.group("elseStmt"));
        }
    }

    private void executeWhile(Matcher match) {
        long counter = 0;
        while (evaluateExpression(match.group("cond")) > 0 && counter < 10000) {
            execute(match.group("body"));
            counter++;
        }
    }

    private void executeMove(Matcher match) {
        String direction = match.group("dir");
        System.out.println("Moving " + direction);
    }

    private void executeShoot(Matcher match) {
        String direction = match.group("dir");
        long cost = evaluateExpression(match.group("expr"));
        System.out.println("Shooting " + direction + " with cost " + cost);
    }

    private void executeInfo(Matcher match) {
        String expr = match.group("expr");
        if (expr.equals("ally")) {
            System.out.println("Checking nearest ally");
        } else if (expr.equals("opponent")) {
            System.out.println("Checking nearest opponent");
        } else {
            System.out.println("Checking nearby direction: " + match.group("dir"));
        }
    }

    private void executeDone() {
        System.out.println("Ending turn");
    }

    private long evaluateExpression(String expr) {
        if (expr.equals("random")) {
            return random.nextInt(1000);
        }
        try {
            return Long.parseLong(expr.trim());
        } catch (NumberFormatException e) {
            return variables.getOrDefault(expr.trim(), 0L);
        }
    }

    public static void main(String[] args) {
        MinionEvaluator evaluator = new MinionEvaluator();
        String script = """
        t = t + 1  # keeping track of the turn number
        if (t - 5) then move up else move down
        while (t - 10) move upleft
        opponentLoc = opponent
        allyLoc = ally
        nearbyLoc = nearby down
        done
        """;
        evaluator.evaluate(script);
    }
}


