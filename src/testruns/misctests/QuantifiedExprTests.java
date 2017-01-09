package testruns.misctests;

import interfaces.INode;
import terminals.QuantifiedExpr;
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
        System.out.println("range: " + ((QuantifiedExpr)expr).getRange() );
        System.out.println("range parent: " + ((QuantifiedExpr)expr).getRange().getParent() );
        System.out.println("term: " + ((QuantifiedExpr)expr).getTerm() );
        System.out.println("term parent: " + ((QuantifiedExpr)expr).getTerm().getParent() );

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

        System.out.println("\n##############################################\n");

        System.out.println("Copying a quant from it's range");
        INode term = ((QuantifiedExpr) QuantTrees.QuantEquivX().children()[0]).getTerm();
        INode termsRoot = term.getRoot();
        System.out.println("term of quant: " + term);
        System.out.println("root of term: " + termsRoot);
        System.out.println("CopyTime");
        term = term.copyWholeTree();
        termsRoot = term.getRoot();
        System.out.println("term of quant copy: " + term);
        System.out.println("root of copy of term: " + termsRoot);

        System.out.println("\n##############################################\n");
        System.out.println("empty range");
        System.out.println(QuantExprs.emptyRangeQuant());
        System.out.println(QuantTrees.emptyImpliesTerm());

    }
}
