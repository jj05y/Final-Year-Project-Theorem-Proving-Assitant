package parser;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import nodes.UnaryOperator;
import terminals.Identifier;

/**
 * Created by joe on 29/12/16.
 * <p>
 * precendence equiv <  and & or < impl & ff < negation
 * <p>
 * Expr := Or { <EQUIV> Or }
 * Or ::= And { <OR> And }
 * And ::= Impl { <AND> Impl }
 * Impl ::= FF { <IMPL> FF }
 * FF ::= Factor { <FF> Factor }
 * Factor ::= <ID> | <NOT> Factor | <LPAR> Expression <RPAR>
 * <p>
 * <ID> ::= [A-Z]
 * <OR> ::= '|'
 * <AND> ::= '&'
 * <NOT> ::= '!'
 * <IMPL> ::= '=>'
 * <FF> ::= '<='
 */
public class Parser {

    private Lexer lexer;
    private INode root;
    private int symbol;

    public Parser(String expression) {
        lexer = new Lexer(expression);
    }

    public INode go() {
        expr();
        root.tellChildAboutParent();
        return root;
    }

    public void expr() {
        or();
        while (symbol == Lexer.EQUIV) {
            INode n = new BinaryOperator(Operators.EQUIVAL, null, null);
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
        impl();
        while (symbol == Lexer.AND) {
            INode n = new BinaryOperator(Operators.AND, null, null);
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
        factor();
        while (symbol == Lexer.FF) {
            INode n = new BinaryOperator(Operators.REVERSE_IMPLICATION, null, null);
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
            //TODO literal node
        } else if (symbol == lexer.FALSE) {
            //TODO literal node
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
            symbol = lexer.nextSymbol(); //expecting it to be RPAR
        } else {
            //TODO raise exception borked
        }

    }

}

