package trees;

import constants.Operators;
import interfaces.INode;
import terminals.ArrayAndIndex;
import terminals.QuantifiedExpr;

import java.util.List;
import java.util.Vector;

/**
 * Created by joe on 03/01/17.
 */
public class QuantExprs {

    public static QuantifiedExpr test() {
        INode range = new ArrayAndIndex('r', 'i');
        INode term = new ArrayAndIndex('f', 'i');
        List<Character> dummys = new Vector() {{
            add('i');
        }};

        QuantifiedExpr expr = new QuantifiedExpr(Operators.FOR_ALL, dummys, range, term);


        return expr;
    }
}
