package testruns.workertests;

import beans.ExprAndHintandTransition;
import interfaces.INode;
import terminals.QuantifiedExpr;
import trees.BoolTrees;
import trees.QuantTrees;
import workers.Replacer;

/**
 * Created by joe on 14/12/16.
 */
public class ReplacerQuantTest {

    public static void main (String[] args) {
        Replacer replacer = new Replacer();

        long start = System.currentTimeMillis();

        System.out.println("Test1");
        INode expr = QuantTrees.XorAllRiXorFi();
        System.out.println("Expression is: " + expr);
        INode selection1 = expr.children()[1];
        INode rule1 = QuantTrees.orOverForAll();
        System.out.println("Selection is: " + selection1);
        System.out.println("Using rule: " + rule1);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection1,rule1)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");

        System.out.println("\n#####################################################################\n");

        System.out.println("Test2");
        INode expr2 = QuantTrees.exprWithTwoQuants();
        System.out.println("Expression is: " + expr2);
        INode selection2 = expr2.children()[0];
        INode rule2 = QuantTrees.ruleWithTwoQuants();
        System.out.println("Selection is: " + selection2);
        System.out.println("Using rule: " + rule2);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection2,rule2)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");


        System.out.println("\n#####################################################################\n");

        System.out.println("Test3");
        INode expr3 = QuantTrees.XorAllRiXorFi();
        System.out.println("Expression is: " + expr3);
        INode selection3 = ((QuantifiedExpr)expr3.children()[1]).getTerm();
        INode rule3 = BoolTrees.orSym();
        System.out.println("Selection is: " + selection3);
        System.out.println("Using rule: " + rule3);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection3,rule3)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");



    }
}
