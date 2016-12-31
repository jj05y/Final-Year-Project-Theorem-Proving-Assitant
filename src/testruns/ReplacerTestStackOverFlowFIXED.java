package testruns;

import core.ExprAndHintandTransition;
import interfaces.INode;
import trees.Trees;
import workers.Replacer;

/**
 * Created by joe on 14/12/16.
 */
public class ReplacerTestStackOverFlowFIXED {

    public static void main (String[] args) {
        Replacer replacer = new Replacer();

        long start = System.currentTimeMillis();

        INode expr =  Trees.XandYorZwithBrackets();
        System.out.println("Expression is: " +expr);
        INode selection1 = expr;
        INode rule1 =Trees.goldenRule();
        System.out.println("Selection is: " + selection1);
        System.out.println("Using rule: " + rule1);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection1,rule1)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");




    }
}
