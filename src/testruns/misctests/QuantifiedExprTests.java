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
        System.out.println("For all r.i, f.i: " + expr);

        System.out.println("\n##############################################\n");
        System.out.println("Or over For All: " + QuantTrees.orOverForAll());
    }
}
