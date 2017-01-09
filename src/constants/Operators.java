package constants;

import interfaces.INode;
import nodes.NodeForBrackets;
import terminals.Identifier;
import workers.Matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Operators {

    public static String NOT = ((char) 0x00AC) + "";
    public static String UNDER = ((char) 0x2291) + "";
    public static String OVER = ((char) 0x2292) + "";
    public static String UP = ((char) 0x2191) + "";
    public static String DOWN = ((char) 0x2193) + "";
    public static String AND = ((char) 0x2227) + "";
    public static String OR = ((char) 0x2228) + "";
    public static String IMPLICATION = ((char) 0x21D2) + "";
    public static String REVERSE_IMPLICATION = ((char) 0x21D0) + "";
    public static String EQUIVAL = ((char) 0x2261) + "";
    public static String NOT_EQUIVAL = ((char) 0x2262) + "";
    public static String FUNNY_PLUS = ((char) 0x2295) + "";
    public static String FUNNY_TIMES = ((char) 0x2297) + "";
    public static String DOT = ((char) 0x2022) + "";
    public static String STAR = ((char) 0x2BCE) + "";
    public static String NULL = ((char) 0x2205) + "";
    public static String LANGLE = ((char) 0x27E8) + "";
    public static String RANGLE = ((char) 0x27E9) + "";
    public static String FOR_ALL = ((char) 0x2200) + "";
    public static String THERE_EXISTS = ((char) 0x2203) + "";
    public static String TRUE = "true";
    public static String FALSE = "false";
    public static String QUANT = ((char) 0x211A) + "";
    public static String LCEILING = ((char) 0x2308) + "";
    public static String RCEILING = ((char) 0x2309) + "";
    public static String LFLOOR = ((char) 0x230A) + "";
    public static String RFLOOR = ((char) 0x230B) + "";
    public static String LPAR = "(";
    public static String RPAR = ")";
    public static String EQUALS = "=";
    public static String LT = "<";
    public static String LTE = ((char) 0x2264) + "";
    public static String GT = "<";
    public static String GTE = ((char) 0x2265) + "";
    public static String PLUS = "+";
    public static String MINUS = "-";
    public static String NOT_EQUALS = ((char) 0x2260) + "" ;



    public static Map<String, Integer> precedence = new HashMap<String, Integer>() {{
        //boolean
        put(EQUIVAL, 0);
        put(NOT_EQUIVAL, 1);
        put(IMPLICATION, 2);
        put(REVERSE_IMPLICATION, 3);
        put(OR, 4);
        put(AND, 4);
        put(NOT, 5);

        //numerical
        put(EQUALS,5);
        put(LT,6);
        put(LTE,6);
        put(GT,6);
        put(GTE,6);
        put(UNDER,6);
        put(OVER,6);
        put(UP, 7);
        put(DOWN, 7);
        put(PLUS,8);
        put(MINUS,8);
    }};

    public static Map<String, String> bracketPair = new HashMap<String, String>() {{
        put(LPAR, RPAR);
        put(LCEILING, RCEILING);
        put(LFLOOR, RFLOOR);
    }};

    public static int findLowestPrecendence(INode node, int lowestPrecedence) {
        if (node instanceof Identifier || node instanceof NodeForBrackets) {
            return lowestPrecedence;
        }

        Integer nodePrecedence = Operators.precedence.get(node.getNodeChar());
        if (nodePrecedence != null) {
            if (nodePrecedence < lowestPrecedence) lowestPrecedence = nodePrecedence;
        } else {
            //TODO raise exception "no precedence defined"
        }

        return lowestPrecedence;
    }



}
