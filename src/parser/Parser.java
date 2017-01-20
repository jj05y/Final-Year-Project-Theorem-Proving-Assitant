package parser;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import nodes.UnaryOperator;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import terminals.Literal;
import terminals.QuantifiedExpr;

import java.util.*;

/**
 * Created by joe on 29/12/16.
 * <p>
 * precendence equiv <  and & or < impl & ff < negation
 * <p>
 * Expr := NotEq { <EQUIV> NotEq }
 * NotEq := Impl { <NOT_EQUIVAL> Impl }
 * Impl ::= FF { <IMPL> FF }
 * FF ::= Or { <FF> Or }
 * Or ::= And { <OR> And }
 * And ::= Equals { <AND> Equals }
 * Equals ::= NotEquals { <NOT_EQUALS> NotEquals }
 * NotEquals ::= Lt { <EQUAL> Lt }
 * Lt ::= Gt { <LT> Gt }
 * Gt ::= Lte { <GT> Lte }
 * Lte ::= Gte { <LTE> Gte }
 * Gte ::= Over { <GTE> Over }
 * Over ::= Under { <OVER> Under }
 * Under ::= Up { <UNDER> Up }
 * Up ::= Down { <Max> Down }
 * Down ::= Add { <MIN> Add }
 * Add ::= Minus { <PLUS> Minus }
 * Minus ::= Factor { <MINUS> Factor }
 * Factor ::= <ID> | <NOT> Factor | <LPAR> Expression <RPAR> | <ARRAY_AND_INDEX>
 * | <LANGLE> <EXISTS> <ID> <COLON> Expression <COLON> Expression  <RANGLE>
 * | <LANGLE> <FORALL> <ID> <COLON> Expression <COLON> Expression  <RANGLE>
 *
 */
public class Parser {

    private Lexer lexer;
    private INode root;
    private int symbol;

    public Parser(String expression) {
        lexer = new Lexer(expression);
    }

    public INode getTree() {
        expr();
        if (root != null) root.tellChildAboutParent();
        return lexer.nextSymbol() == lexer.EOF ? root : null;
    }

    public void expr() {
        NotEq();
        while (symbol == Lexer.EQUIV) {
            INode n = new BinaryOperator(Operators.EQUIVAL, null, null);
            n.children()[0] = root;
            NotEq();
            n.children()[1] = root;
            root = n;
        }
    }

    public void NotEq() {
        impl();
        while (symbol == Lexer.NOT_EQUIV) {
            INode n = new BinaryOperator(Operators.NOT_EQUIVAL, null, null);
            n.children()[0] = root;
            impl();
            n.children()[1] = root;
            root = n;
        }
    }

    public void impl() {
        ff();
        while (symbol == Lexer.IMPL) {
            INode n = new BinaryOperator(Operators.IMPLICATION, null, null);
            n.children()[0] = root;
            ff();
            n.children()[1] = root;
            root = n;
        }
    }

    public void ff() {
        or();
        while (symbol == Lexer.FF) {
            INode n = new BinaryOperator(Operators.REVERSE_IMPLICATION, null, null);
            n.children()[0] = root;
            or();
            n.children()[1] = root;
            root = n;
        }
    }


    public void or() {
        and();
        while (symbol == Lexer.OR) {
            INode n = new BinaryOperator(Operators.OR, null, null);
            n.children()[0] = root;
            and();
            n.children()[1] = root;
            root = n;
        }
    }

    public void and() {
        equals();
        while (symbol == Lexer.AND) {
            INode n = new BinaryOperator(Operators.AND, null, null);
            n.children()[0] = root;
            equals();
            n.children()[1] = root;
            root = n;
        }
    }

    private void equals() {
        notEquals();
        while (symbol == Lexer.EQUALS) {
            INode n = new BinaryOperator(Operators.EQUALS, null, null);
            n.children()[0] = root;
            notEquals();
            n.children()[1] = root;
            root = n;
        }
    }

    private void notEquals() {
        lt();
        while (symbol == Lexer.NOT_EQUALS) {
            INode n = new BinaryOperator(Operators.NOT_EQUALS, null, null);
            n.children()[0] = root;
            lt();
            n.children()[1] = root;
            root = n;
        }
    }
    private void lt() {
        gt();
        while (symbol == Lexer.LT) {
            INode n = new BinaryOperator(Operators.LT, null, null);
            n.children()[0] = root;
            gt();
            n.children()[1] = root;
            root = n;
        }
    }
    private void gt() {
        lte();
        while (symbol == Lexer.GT) {
            INode n = new BinaryOperator(Operators.GT, null, null);
            n.children()[0] = root;
            lte();
            n.children()[1] = root;
            root = n;
        }
    }

    private void lte() {
        gte();
        while (symbol == Lexer.LTE) {
            INode n = new BinaryOperator(Operators.LTE, null, null);
            n.children()[0] = root;
            gte();
            n.children()[1] = root;
            root = n;
        }
    }

    private void gte() {
        over();
        while (symbol == Lexer.GTE) {
            INode n = new BinaryOperator(Operators.GTE, null, null);
            n.children()[0] = root;
            over();
            n.children()[1] = root;
            root = n;
        }
    }

    private void over() {
        under();
        while (symbol == Lexer.OVER) {
            INode n = new BinaryOperator(Operators.OVER, null, null);
            n.children()[0] = root;
            under();
            n.children()[1] = root;
            root = n;
        }
    }
    private void under() {
        up();
        while (symbol == Lexer.UNDER) {
            INode n = new BinaryOperator(Operators.UNDER, null, null);
            n.children()[0] = root;
            up();
            n.children()[1] = root;
            root = n;
        }
    }

    private void up() {
        down();
        while (symbol == Lexer.UP) {
            INode n = new BinaryOperator(Operators.UP, null, null);
            n.children()[0] = root;
            down();
            n.children()[1] = root;
            root = n;
        }
    }

    private void down() {
        add();
        while (symbol == Lexer.DOWN) {
            INode n = new BinaryOperator(Operators.DOWN, null, null);
            n.children()[0] = root;
            add();
            n.children()[1] = root;
            root = n;
        }
    }

    private void add() {
        minus();
        while (symbol == Lexer.PLUS) {
            INode n = new BinaryOperator(Operators.PLUS, null, null);
            n.children()[0] = root;
            minus();
            n.children()[1] = root;
            root = n;
        }
    }
    private void minus() {
        factor();
        while (symbol == Lexer.MINUS) {
            INode n = new BinaryOperator(Operators.MINUS, null, null);
            n.children()[0] = root;
            factor();
            n.children()[1] = root;
            root = n;
        }
    }


    public void factor() {
        symbol = lexer.nextSymbol();
        if (symbol == lexer.ID) {
            root = new Identifier(lexer.getId());
            symbol = lexer.nextSymbol();
        } else if (symbol == lexer.TRUE) {
            root = new Literal(true);
            symbol = lexer.nextSymbol();
        } else if (symbol == lexer.FALSE) {
            root = new Literal(false);
            symbol = lexer.nextSymbol();
        } else if (symbol == lexer.NOT) {
            INode n = new UnaryOperator(Operators.NOT, null);
            factor();
            n.children()[0] = root;
            root = n;
        } else if (symbol == lexer.LPAR) {
            INode n = new NodeForBrackets(null);
            expr();
            n.children()[0] = root;
            root = n;
            symbol = lexer.nextSymbol();//RPAR
        } else if (symbol == lexer.LFLOOR) {
            INode n = new NodeForBrackets(null, Operators.LFLOOR);
            equals();
            n.children()[0] = root;
            root = n;
            symbol = lexer.nextSymbol(); //FLOOR
        } else if (symbol == lexer.LCEILING) {
            INode n = new NodeForBrackets(null, Operators.LCEILING);
            equals();
            n.children()[0] = root;
            root = n;
            symbol = lexer.nextSymbol(); //RCEILING
        } else if (symbol == Lexer.ARRAY_AND_INDEX) {
            String id = lexer.getId();
            root = new ArrayAndIndex(id.split("\\.")[0], id.split("\\.")[1]);
            symbol = lexer.nextSymbol();
        } else if (symbol == Lexer.COLON) {
            root = new Identifier("");
        } else if (symbol == Lexer.LANGLE) {

            symbol = lexer.nextSymbol();
            String quantifier = "";
            switch (symbol) {
                case Lexer.EXISTS:
                    quantifier = Operators.THERE_EXISTS;
                    break;
                case Lexer.FORALL:
                    quantifier = Operators.FOR_ALL;
                    break;
                default:
                    quantifier = Operators.FOR_ALL;

            }
            symbol = lexer.nextSymbol();
            //expecting id which has all the dummies
            Set<String> dummyList = new HashSet<>();
            String dummies = lexer.getId();
            for (String s : dummies.split(",")) {
                dummyList.add(s);
            }

            //expecting :
            symbol = lexer.nextSymbol();

            //now the range can be an expression

            expr();
            INode range = root; //should that be the expression at this point? :/ mayb


            expr();
            INode term = root;


            root = new QuantifiedExpr(quantifier, dummyList, range, term);
            symbol = lexer.nextSymbol();//consume rangle
        } else {
            //there are no more!
        }

    }







}

