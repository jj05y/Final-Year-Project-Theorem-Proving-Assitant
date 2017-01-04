package constants;

import interfaces.INode;
import nodes.NodeForBrackets;
import terminals.Identifier;

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
    public static String STAR = ((char) 0x22C6) + "";
    public static String NULL = ((char) 0x2205) + "";
    public static String LANGLE = ((char) 0x27E8) + "";
    public static String RANGLE = ((char) 0x27E9) + "";
    public static String FOR_ALL = ((char) 0x2200) + "";
    public static String THERE_EXISTS = ((char) 0x2203) + "";
    public static String TRUE = ((char) 0x2ADF) + "";
    public static String FALSE = ((char) 0x2AED) + "";
    public static String QUANT = ((char) 0x211A) + "";


    public static Map<String, Integer> precedence = new HashMap<String, Integer>() {{
        put(EQUIVAL, 0);
        put(NOT_EQUIVAL, 1);
        put(IMPLICATION, 2);
        put(REVERSE_IMPLICATION, 3);
        put(OR, 4);
        put(AND, 4);
        put(NOT, 5);
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
