package TestRuns;

import Interfaces.INode;
import Theorems.ArbIDExpr;
import Theorems.Expression;
import Trees.Trees;
import Workers.ExpressionBuilder;

/**
 * Created by joe on 28/10/16.
 */
public class ArbIDTest {

    public static void main(String[] args) {

        INode node = Trees.XandYequivalXandYequivalZ();
        Expression expr = new Expression(node);
        for (ArbIDExpr arb : expr.getArbIDExprList()) {
            System.out.println("Original Names:  " + arb.getRoot());
            System.out.println("Arbitrary Names: " + ExpressionBuilder.getArbExpression(arb.getRoot()));
            System.out.println();
        }
    }
}
