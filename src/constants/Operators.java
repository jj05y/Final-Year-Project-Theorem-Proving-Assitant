package constants;

import interfaces.INode;
import nodes.NodeForBrackets;
import terminals.Identifier;

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
    public static char NOT_EQUIVAL = 0x2262;
    public static char FUNNY_PLUS = 0x2295;
    public static char FUNNY_TIMES = 0x2297;
    public static char DOT = 0x2022;
    public static char STAR = 0x22C6;
    public static char NULL = 0x2205;
    public static char LANGLE = 0x27E8;
    public static char RANGLE = 0x27E9;
    public static char FOR_ALL = 0x2200;
    public static char THERE_EXISTS = 0x2203;
    public static char TRUE = 0x2ADF;
    public static char FALSE = 0x2AED;
    public static char QUANT = 0x211A;


    public static Map<Character, Integer> precedence = new HashMap<Character, Integer>() {{
        put(EQUIVAL, 0);
        put(NOT_EQUIVAL, 1);
        put(IMPLICATION, 2);
        put(REVERSE_IMPLICATION, 3);
        put(OR,4);
        put(AND,4);
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

        return  lowestPrecedence;
    }



}
