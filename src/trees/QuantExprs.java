package trees;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import nodes.QuantifiedExpr;

import java.util.List;
import java.util.Vector;

/**
 * Created by joe on 03/01/17.
 */
public class QuantExprs {

    public static QuantifiedExpr allRiFi() {
        INode range = new ArrayAndIndex('r', 'i');
        INode term = new ArrayAndIndex('f', 'i');
        List<Character> dummys = new Vector() {{
            add('i');
        }};

        QuantifiedExpr expr = new QuantifiedExpr(Operators.FOR_ALL, dummys, range, term);
        expr.tellChildAboutParent();

        return expr;
    }
    public static QuantifiedExpr allSiFi() {
        INode range = new ArrayAndIndex('s', 'i');
        INode term = new ArrayAndIndex('f', 'i');
        List<Character> dummys = new Vector() {{
            add('i');
        }};

        QuantifiedExpr expr = new QuantifiedExpr(Operators.FOR_ALL, dummys, range, term);

        return expr;
    }

    public static QuantifiedExpr allRiFiOrX() {
        INode range = new ArrayAndIndex('r', 'i');
        INode term = new BinaryOperator(Operators.OR, new Identifier('X'), new ArrayAndIndex('f', 'i'));
        term.tellChildAboutParent();
        List<Character> dummys = new Vector() {{
            add('i');
        }};

        QuantifiedExpr expr = new QuantifiedExpr(Operators.FOR_ALL, dummys, range, term);

        return expr;
    }
}
