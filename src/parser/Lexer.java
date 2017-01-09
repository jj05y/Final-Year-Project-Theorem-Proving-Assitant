package parser;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by joe on 29/12/16.
 */
public class Lexer {
    private StringTokenizer tokenizer;
    private int symbol = NONE;
    public static final int EOF = -2;
    public static final int NONE = 0;
    public static final int OR = 1;
    public static final int AND = 2;
    public static final int NOT = 3;
    public static final int TRUE = 4;
    public static final int FALSE = 5;
    public static final int LPAR = 6;
    public static final int RPAR = 7;
    public static final int IMPL = 8;
    public static final int FF = 9;
    public static final int EQUIV = 10;
    public static final int NOT_EQUIV = 11;
    public static final int ID = 12;
    public static final int COLON = 13;
    public static final int LANGLE = 14;
    public static final int RANGLE = 15;
    public static final int EXISTS = 16;
    public static final int FORALL = 17;
    public static final int ARRAY_AND_INDEX = 18;


    private String id;
    private String quant;


    public Lexer(String input) {
        tokenizer = new StringTokenizer(input);
    }

    public int nextSymbol() {
        String token;
        if (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            switch (token) {
                case "and":
                    symbol = AND;
                    break;
                case "or":
                    symbol = OR;
                    break;
                case "=>":
                    symbol = IMPL;
                    break;
                case "<=":
                    symbol = FF;
                    break;
                case "(":
                    symbol = LPAR;
                    break;
                case ")":
                    symbol = RPAR;
                    break;
                case "==":
                    symbol = EQUIV;
                    break;
                case "!==":
                    symbol = NOT_EQUIV;
                    break;
                case "true":
                    symbol = TRUE;
                    break;
                case "false":
                    symbol = FALSE;
                    break;
                case "!":
                    symbol = NOT;
                    break;
                case ">":
                    symbol = RANGLE;
                    break;
                case "<":
                    quant = "";
                    while (!((token = tokenizer.nextToken()).equals(">"))) {
                        quant += token + " ";
                    }
                    symbol = LANGLE;
                    break;
                case ":":
                    symbol = COLON;
                    break;
                case "exists":
                    symbol = EXISTS;
                    break;
                case "forall":
                    symbol = FORALL;
                    break;
                default:
                    if (token.contains(".")) {
                        id = token;
                        symbol = ARRAY_AND_INDEX;
                    } else {
                        id = token;
                        symbol = ID;
                    }
            }
            return symbol;
        } else {
            return EOF;
        }
    }

    public String getId() {
        return id;
    }
    public String getQuant() {
        return quant;
    }
}

