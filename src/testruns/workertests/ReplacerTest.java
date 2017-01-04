package testruns.workertests;

import beans.ExprAndHintandTransition;
import interfaces.INode;
import trees.BoolTrees;
import workers.Replacer;

/**
 * Created by joe on 14/12/16.
 */
public class ReplacerTest {

    public static void main (String[] args) {
        Replacer replacer = new Replacer();

        long start = System.currentTimeMillis();

        System.out.println("Test1");
        System.out.println("Expression is: " + BoolTrees.absZeroequivXandY());
        INode selection1 = BoolTrees.absZeroequivXandY().children()[0];
        INode rule1 = BoolTrees.absZero();
        System.out.println("Selection is: " + selection1);
        System.out.println("Using rule: " + rule1);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection1,rule1)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");

        System.out.println("\n*******************************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Test2");
        System.out.println("Expression is: " + BoolTrees.absZeroequivXandY());
        INode selection2 = BoolTrees.absZeroequivXandY().children()[0];
        INode rule2 = BoolTrees.goldenRulePQ();
        System.out.println("Selection is: " + selection2);
        System.out.println("Using rule: " + rule2);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection2,rule2)) System.out.println(replacement);


        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");


        System.out.println("\n*******************************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Test3");
        System.out.println("Expression is: " + BoolTrees.weirdabsZeroequivXandY());
        INode selection3 = BoolTrees.weirdabsZeroequivXandY().children()[0];
        INode rule3 = BoolTrees.absZero();
        System.out.println("Selection is: " + selection3);
        System.out.println("Using rule: " + rule3);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection3,rule3)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");


        System.out.println("\n*******************************************************************\n");

        start = System.currentTimeMillis();

        System.out.println("Test4");
        System.out.println("Expression is: " + BoolTrees.weirdBrokenabsZeroequivXandY());
        INode selection4 = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0];
        INode rule4 = BoolTrees.absZero();
        System.out.println("Selection is: " + selection4);
        System.out.println("Using rule: " + rule4);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection4,rule4)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");

        System.out.println("\n*******************************************************************\n");

        start = System.currentTimeMillis();

        System.out.println("Test5");
        System.out.println("Expression is: " + BoolTrees.weirdBrokenabsZeroequivXandY());
        INode selection5 = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule5 = BoolTrees.absZero();
        System.out.println("Selection is: " + selection5);
        System.out.println("Using rule: " + rule5);
        System.out.println("New Expression Options: ");
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection5,rule5)) System.out.println(replacement);

        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");

        System.out.println("\n*******************************************************************\n");

        start = System.currentTimeMillis();

        System.out.println("Test6");
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
