package TestRuns;

import Interfaces.INode;
import Trees.Trees;
import Workers.Matcher;
import Workers.Replacer;

import java.util.Set;

/**
 * Created by joe on 14/12/16.
 */
public class ReplacerTest {

    public static void main (String[] args) {
        Replacer replacer = new Replacer();

        System.out.println("Test1");
        System.out.println("Expr is: " + Trees.absZeroequivXandY());
        INode selection1 = Trees.absZeroequivXandY().children()[0];
        INode rule1 =Trees.absZero();
        System.out.println("Selection is: " + selection1);
        System.out.println("Using rule: " + rule1);
        System.out.println("Replacement Options: ");
        for (INode replacement : replacer.getReplacements(selection1,rule1)) System.out.println(replacement);

        System.out.println("\n*******************************************************************\n");


        System.out.println("Test2");
        System.out.println("Expr is: " + Trees.absZeroequivXandY());
        INode selection2 = Trees.absZeroequivXandY().children()[0];
        INode rule2 =Trees.goldenRulePQ();
        System.out.println("Selection is: " + selection2);
        System.out.println("Using rule: " + rule2);
        System.out.println("Replacement Options: ");
        for (INode replacement : replacer.getReplacements(selection2,rule2)) System.out.println(replacement);




        System.out.println("\n*******************************************************************\n");


        System.out.println("Test3");
        System.out.println("Expr is: " + Trees.weirdabsZeroequivXandY());
        INode selection3 = Trees.weirdabsZeroequivXandY().children()[0];
        INode rule3 =Trees.absZero();
        System.out.println("Selection is: " + selection3);
        System.out.println("Using rule: " + rule3);
        System.out.println("Replacement Options: ");
        for (INode replacement : replacer.getReplacements(selection3,rule3)) System.out.println(replacement);



        System.out.println("\n*******************************************************************\n");


        System.out.println("Test4");
        System.out.println("Expr is: " + Trees.weirdBrokenabsZeroequivXandY());
        INode selection4 = Trees.weirdBrokenabsZeroequivXandY().children()[0];
        INode rule4 =Trees.absZero();
        System.out.println("Selection is: " + selection4);
        System.out.println("Using rule: " + rule4);
        System.out.println("Replacement Options: ");
        for (INode replacement : replacer.getReplacements(selection4,rule4)) System.out.println(replacement);


    }
}
