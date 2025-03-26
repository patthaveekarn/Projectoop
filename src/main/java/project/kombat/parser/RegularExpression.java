package project.kombat.parser;

import java.util.regex.Pattern;

public class RegularExpression {
    public static final String ACTION_REGEX = "done|move|shoot";
    public static final String DONE_REGEX = "done";      // คำสั่งจบการทำงาน
    public static final String MOVE_REGEX = "move";      // คำสั่งเคลื่อนที่
    public static final String SHOOT_REGEX = "shoot";    // คำสั่งยิง

    public static final String DIRECTION_REGEX = "up|down|upleft|upright|downleft|downright";
    public static final String UP_REGEX = "up";             // ขึ้นบน
    public static final String DOWN_REGEX = "down";         // ลงล่าง
    public static final String UPLEFT_REGEX = "upleft";     // ขึ้นซ้าย
    public static final String UPRIGHT_REGEX = "upright";   // ขึ้นขวา
    public static final String DOWNLEFT_REGEX = "downleft"; // ลงซ้าย
    public static final String DOWNRIGHT_REGEX = "downright"; // ลงขวา

    public static final String RESERVED_REGEX = "done|move|shoot|if|else|while|then|row|col|budget|int|maxbudget|spawnsleft|random|up|down|upleft|upright|downleft|downright|opponent|nearby";

    public static final String SPECIAL_VAR_REGEX = "row|col|budget|int|maxbudget|spawnsleft|random";

    public static final String IF_REGEX = "if";       // เงื่อนไข if
    public static final String THEN_REGEX = "then";   // คำสั่งหลังเงื่อนไข
    public static final String ELSE_REGEX = "else";   // ทำเมื่อเงื่อนไขไม่เป็นจริง
    public static final String WHILE_REGEX = "while"; // วนลูปเมื่อเงื่อนไขเป็นจริง

    public static final String INFOEXPRESSION_REGEX = "opponent|nearby";
    public static final String OPPONENT_REGEX = "opponent"; // เช็คตำแหน่งศัตรู
    public static final String NEARBY_REGEX = "nearby";    // เช็คว่ามีอะไรอยู่ใกล้ๆ

    public static final String OPERATOR_REGEX = "[-+*/%^]";

    public static final String ASSIGN_REGEX = "=";

    public static final String PARENTHESES_REGEX = "[(){}]";

    public static final String NUMBER_REGEX = "[0-9]+";

    public static final String IDENTIFIER_REGEX = "^[a-zA-Z][a-zA-Z0-9]*$";

    public static final String RANDOM_REGEX = "random";

    public static final String ALL_REGEX = "([-+*/%^=])|([(){}])|([0-9]+)|([a-zA-Z][a-zA-Z0-9]*)|(<=|>=|==|!=|<|>)";

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
