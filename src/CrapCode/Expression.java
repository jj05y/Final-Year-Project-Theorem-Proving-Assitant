/*
package CrapCode;

import Interfaces.INode;
import Terminals.Identifier;
import Workers.TreePermuter;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

*/
/**
 * Created by joe on 19/09/16.
 *//*

public class Expression {

    private INode root;
    //a map, key'd by sub expression, that has a list of valid replacements for each subexpression
    private List<INode> permutations;
    private HashMap<Expression, Vector<Expression>> substitutes;

    private List<ArbIDExpr> arbIDExprList;


    public Expression(INode root) {
        this.root = root;
        permutations = (new TreePermuter()).permuteJustOneTier(root.copy());
        arbIDExprList = new Vector<>();
        makeListOfArbIDExpr();
    }

    private void makeListOfArbIDExpr() {
        for (INode n : permutations) {
            //for each depth in each perm, grab every id at that level?
            //TODO
            int depth = 0;
            ArbIDExpr arb = new ArbIDExpr(n.copy());
            arbIDExprList.add(arb);
        }
    }


    private int depth(INode node) {
        if (node instanceof Identifier) {
            return 0;
        } else if (node.children().length == 1) {
            return 1+depth(node.children()[0]);
        } else {
            return Math.max(1+depth(node.children()[0]), 1+depth(node.children()[1]));
        }
    }


    //at every node (inlcuding root) we have to create an arbIDExpr
    //walk the tree, at every node, create that arb Id Expr WITH that node being and ID, AND explored


    public List<ArbIDExpr> getArbIDExprList() {
        return arbIDExprList;
    }
}
*/
