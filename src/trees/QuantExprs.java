package trees;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import terminals.QuantifiedExpr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by joe on 03/01/17.
 */
public class QuantExprs {

    public static QuantifiedExpr allRiFi() {
        INode range = new ArrayAndIndex("r", "i");
        INode term = new ArrayAndIndex("f", "i");
        Set<String> dummys = new HashSet() {{
            add("i");
        }};

        QuantifiedExpr expr = new QuantifiedExpr(Operators.FOR_ALL, dummys, range, term);
        expr.tellChildAboutParent();

        return expr;
    }
    public static QuantifiedExpr allSiFi() {
        INode range = new ArrayAndIndex("s", "i");
        INode term = new ArrayAndIndex("f", "i");
        Set<String> dummys = new HashSet() {{
            add("i");
        }};

        QuantifiedExpr expr = new QuantifiedExpr(Operators.FOR_ALL, dummys, range, term);

        return expr;
    }

    public static QuantifiedExpr allRiXorFi() {
        INode range = new ArrayAndIndex("r", "i");
        INode term = new BinaryOperator(Operators.OR, new Identifier("X"), new ArrayAndIndex("f", "i"));
        term.tellChildAboutParent();
        Set<String> dummys = new HashSet() {{
            add("i");
        }};

        QuantifiedExpr expr = new QuantifiedExpr(Operators.FOR_ALL, dummys, range, term);

        return expr;
    }
    public static QuantifiedExpr allRiYorFi() {
        INode range = new ArrayAndIndex("r", "i");
        INode term = new BinaryOperator(Operators.OR, new Identifier("Y"), new ArrayAndIndex("f", "i"));
        term.tellChildAboutParent();
        Set<String> dummys = new HashSet() {{
            add("i");
        }};

        QuantifiedExpr expr = new QuantifiedExpr(Operators.FOR_ALL, dummys, range, term);

        return expr;
    }

    public static QuantifiedExpr emptyRangeQuant() {
        INode range =new Identifier("");
        INode term = new ArrayAndIndex("f","i");
        Set<String> dummys = new HashSet() {{add("i");}};

        return new QuantifiedExpr(Operators.FOR_ALL, dummys, range, term);
    }
}
