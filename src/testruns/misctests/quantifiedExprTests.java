package testruns.misctests;

import interfaces.INode;
import trees.QuantExprs;

/**
 * Created by joe on 03/01/17.
 */
public class quantifiedExprTests {
    public static void main(String[] args) {
        INode expr = QuantExprs.test();
        INode otherExpr = QuantExprs.test();
        System.out.println(expr.equals(otherExpr));
    }
}
