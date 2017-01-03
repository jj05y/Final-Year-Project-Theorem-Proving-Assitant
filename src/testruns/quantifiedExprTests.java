package testruns;

import constants.Operators;
import interfaces.INode;
import quantifiedexpressions.QuantifiedExpressions;
import terminals.QuantifiedExpr;

/**
 * Created by joe on 03/01/17.
 */
public class quantifiedExprTests {
    public static void main(String[] args) {
        INode expr = QuantifiedExpressions.test();
        INode otherExpr = QuantifiedExpressions.test();
        System.out.println(expr.equals(otherExpr));
    }
}
