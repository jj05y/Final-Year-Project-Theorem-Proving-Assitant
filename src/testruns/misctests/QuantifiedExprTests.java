package testruns.misctests;

import interfaces.INode;
import trees.QuantExprs;
import trees.QuantTrees;
import workers.TreePermuter;

import java.util.List;
import java.util.Set;

/**
 * Created by joe on 03/01/17.
 */
public class QuantifiedExprTests {
    public static void main(String[] args) {
        INode expr = QuantExprs.allRiFi();
        System.out.println("For all r.i, f.i: " + expr);

        System.out.println("\n##############################################\n");
        System.out.println("Or over For All: " + QuantTrees.orOverForAll());

        System.out.println("\n##############################################\n");
        INode copy = expr.copyWholeTree();
        System.out.println("copy of allRiFi: " + copy);


        System.out.println("\n##############################################\n");
        INode copyTree = QuantTrees.orOverForAll().children()[0].copyWholeTree();
        System.out.println("copy of a whole expression: " + copyTree.getRoot());

        System.out.println("\n##############################################\n");
        Set<INode> subExpressions = (new TreePermuter()).goAllPerms(QuantTrees.orOverForAll());
        System.out.println("subexpresions or :" + QuantTrees.orOverForAll());
        for (INode n : subExpressions) {
            System.out.println(n);
        }

        System.out.println("\n##############################################\n");

        System.out.println("Checking equality of XorFi and XorFi");
        System.out.println(QuantTrees.XorFi().equals(QuantTrees.XorFi()));
    }
}
