package testruns.workertests;

import core.ExprAndHintandTransition;
import interfaces.INode;
import trees.BoolTrees;
import workers.Replacer;

/**
 * Created by joe on 14/12/16.
 */
public class ReplacerTestExtraBrackets {

    public static void main (String[] args) {
        Replacer replacer = new Replacer();

        long start = System.currentTimeMillis();


        System.out.println("Expression is: " + BoolTrees.weirdBrokenabsZeroequivXandY());
        INode selection6 = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule6 = BoolTrees.goldenRulePQ();
        System.out.println("Selection is: " + selection6);
        System.out.println("Using rule: " + rule6);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection6,rule6)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");

    }
}
