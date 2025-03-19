package project.kombat.parser;

import java.util.regex.Pattern;

public class RegularExpression {
    public static final String ACTION_REGEX = "done|move|shoot";
    public static final String DONE_REGEX = "done";
    public static final String MOVE_REGEX = "move";
    public static final String SHOOT_REGEX = "shoot";

    public static final String DIRECTION_REGEX = "up|down|upleft|upright|downleft|downright";
    public static final String UP_REGEX = "up";
    public static final String DOWN_REGEX = "down";
    public static final String UPLEFT_REGEX = "upleft";
    public static final String UPRIGHT_REGEX = "upright";
    public static final String DOWNLEFT_REGEX = "downleft";
    public static final String DOWNRIGHT_REGEX = "downright";

    public static final String RESERVED_REGEX = "done|move|shoot|if|else|while|then|row|col|budget|int|maxbudget|spawnsleft|random|up|down|upleft|upright|downleft|downright|opponent|nearby";

    public static final String SPECIAL_VAR_REGEX = "row|col|budget|int|maxbudget|spawnsleft|random";

    public static final String IF_REGEX = "if";
    public static final String THEN_REGEX = "then";
    public static final String ELSE_REGEX = "else";
    public static final String WHILE_REGEX = "while";

    public static final String INFOEXPRESSION_REGEX = "opponent|nearby";
    public static final String OPPONENT_REGEX = "opponent";
    public static final String NEARBY_REGEX = "nearby";

    public static final String OPERATOR_REGEX = "[-+*/%^]";

    public static final String ASSIGN_REGEX = "=";

    public static final String PARENTHESES_REGEX = "[(){}]";

    public static final String NUMBER_REGEX = "[0-9]+";

    public static final String IDENTIFIER_REGEX = "^[a-zA-Z][a-zA-Z0-9]*$";

    public static final String RANDOM_REGEX = "random";

    public static final String ALL_REGEX = "([-+*/%^])|(=)|([(){}])|([0-9]+)|([a-zA-Z][a-zA-Z0-9]*)|([^ \\r\\n]+)";

    public static final String SPLIT_REGEX = "[\\s]+|(?<=[=+\\-*/%(){}^])|(?=[=+\\-*/%(){}^])";

    public static final Pattern DIRECTION_PATTERN = Pattern.compile(DIRECTION_REGEX);
    public static final Pattern ACTION_PATTERN = Pattern.compile(ACTION_REGEX);
    public static final Pattern IF_PATTERN = Pattern.compile(IF_REGEX);
    public static final Pattern THEN_PATTERN = Pattern.compile(THEN_REGEX);
    public static final Pattern ELSE_PATTERN = Pattern.compile(ELSE_REGEX);
    public static final Pattern WHILE_PATTERN = Pattern.compile(WHILE_REGEX);
    public static final Pattern INFOEXPRESSION_PATTERN = Pattern.compile(INFOEXPRESSION_REGEX);
    public static final Pattern OPPONENT_PATTERN = Pattern.compile(OPPONENT_REGEX);
    public static final Pattern NEARBY_PATTERN = Pattern.compile(NEARBY_REGEX);
    public static final Pattern OPERATOR_PATTERN = Pattern.compile(OPERATOR_REGEX);
    public static final Pattern ASSIGN_PATTERN = Pattern.compile(ASSIGN_REGEX);
    public static final Pattern PARENTHESES_PATTERN = Pattern.compile(PARENTHESES_REGEX);
    public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
    public static final Pattern IDENTIFIER_PATTERN = Pattern.compile(IDENTIFIER_REGEX);
    public static final Pattern RANDOM_PATTERN = Pattern.compile(RANDOM_REGEX);
    public static final Pattern ALL_PATTERN = Pattern.compile(ALL_REGEX);
    public static final Pattern SPLIT_PATTERN = Pattern.compile(SPLIT_REGEX);
}
