package testruns.misctests;

import interfaces.INode;
import trees.QuantExprs;
import trees.QuantTrees;

/**
 * Created by joe on 03/01/17.
 */
public class QuantifiedExprTests {
    public static void main(String[] args) {
        INode expr = QuantExprs.allRiFi();
        INode otherExpr = QuantExprs.allRiFi();
        System.out.println(expr);
        System.out.println(expr.equals(otherExpr));

        System.out.println("##############################################");
        System.out.println("Or over For All: " + QuantTrees.orOverForAll());
    }
}
