package testruns;

import core.ExprAndHint;
import interfaces.INode;
import trees.Trees;
import workers.Replacer;

/**
 * Created by joe on 14/12/16.
 */
public class ReplacerTestExtraBrackets {

    public static void main (String[] args) {
        Replacer replacer = new Replacer();

        long start = System.currentTimeMillis();


        System.out.println("Expression is: " + Trees.weirdBrokenabsZeroequivXandY());
        INode selection6 = Trees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule6 =Trees.goldenRulePQ();
        System.out.println("Selection is: " + selection6);
        System.out.println("Using rule: " + rule6);
        System.out.println("New Expression Options: ");
        for (ExprAndHint replacement : replacer.getReplacements(selection6,rule6)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");

    }
}
