package constants;

import java.util.HashMap;
import java.util.Map;

public class Operators {

    public static char NOT = 0x00AC;
    public static char UNDER = 0x2291;
    public static char OVER = 0x2292;
    public static char UP = 0x2191;
    public static char DOWN = 0x2193;
    public static char AND = 0x2227;
    public static char OR = 0x2228;
    public static char IMPLICATION = 0x21D2;
    public static char REVERSE_IMPLICATION = 0x21D0;
    public static char EQUIVAL = 0x2261;
    public static char FUNNY_PLUS = 0x2295;
    public static char FUNNY_TIMES = 0x2297;
    public static char DOT = 0x2022;
    public static char STAR = 0x22C6;
    public static char NULL = 0x2205;

    public static Map<Character, Integer> precedence = new HashMap<Character, Integer>() {{
        put(EQUIVAL, 0);
        put(AND,1);
        put(OR,1);
        put(IMPLICATION, 2);
        put(REVERSE_IMPLICATION, 2);
        put(NOT, 3);
    }};



}
