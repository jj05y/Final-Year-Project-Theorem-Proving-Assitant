package TestRuns;

import Interfaces.INode;
import Trees.Trees;
import Workers.Matcher;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by joe on 12/11/16.
 */
public class MatcherTest {

    public static void main(String[] args) {
        Matcher matcher = new Matcher();

 /*       System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + Trees.XandYandZ() + " in " + Trees.goldenRule());
        for (Matcher.Match m : matcher.match(Trees.XandYandZ(), Trees.goldenRule())){
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + Trees.XandYorZ() + " in " + Trees.goldenRule());
        for (Matcher.Match m : matcher.match(Trees.XandYorZ(), Trees.goldenRule())){
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + Trees.ZandW() + " in " + Trees.goldenRule());
        for (Matcher.Match m : matcher.match(Trees.ZandW(), Trees.goldenRule())){
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + Trees.YequivX() + " in " + Trees.goldenRule());
        for (Matcher.Match m : matcher.match(Trees.YequivX(), Trees.goldenRule())){
            System.out.println(m);
        }

        System.out.println("\n*******************************************************************");
        System.out.println("*******************************************************************");
        System.out.println("*******************************************************************\n");
*/
        System.out.println("Test1");
        System.out.println("Expr is: " + Trees.absZeroequivXandY());
        INode selection1 = Trees.absZeroequivXandY().children()[0];
        INode rule1 =Trees.absZero();
        Set<Matcher.Match> matches = matcher.match(selection1,rule1);
        System.out.println("Selection is: " + selection1);
        System.out.println("Using rule: " + rule1);
        System.out.println("Number Matches: " +matches.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches) {
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");


        System.out.println("Test2");
        System.out.println("Expr is: " + Trees.absZeroequivXandY());
        INode selection2 = Trees.absZeroequivXandY().children()[0];
        INode rule2 =Trees.goldenRulePQ();
        Set<Matcher.Match> matches2 = matcher.match(selection2,rule2);
        System.out.println("Selection is: " + selection2);
        System.out.println("Using rule: " + rule2);
        System.out.println("Number Matches: " +matches2.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches2) {
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");


        System.out.println("Test3");
        System.out.println("Expr is: " + Trees.weirdabsZeroequivXandY());
        INode selection3 = Trees.weirdabsZeroequivXandY().children()[0];
        INode rule3 =Trees.absZero();
        Set<Matcher.Match> matches3 = matcher.match(selection3,rule3);
        System.out.println("Selection is: " + selection3);
        System.out.println("Using rule: " + rule3);
        System.out.println("Number Matches: " +matches3.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches3) {
            System.out.println(m);
        }

        System.out.println("\n*******************************************************************\n");


        System.out.println("Test4");
        System.out.println("Expr is: " + Trees.weirdBrokenabsZeroequivXandY());
        INode selection4 = Trees.weirdBrokenabsZeroequivXandY().children()[0];
        INode rule4 =Trees.absZero();
        Set<Matcher.Match> matches4 = matcher.match(selection4,rule4);
        System.out.println("Selection is: " + selection4);
        System.out.println("Using rule: " + rule4);
        System.out.println("Number Matches: " +matches4.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches4) {
            System.out.println(m);
        }



        System.out.println("\n*******************************************************************\n");


        System.out.println("Test5");
        System.out.println("Expr is: " + Trees.weirdBrokenabsZeroequivXandY());
        INode selection5 = Trees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule5 =Trees.absZero();
        Set<Matcher.Match> matches5 = matcher.match(selection5,rule5);
        System.out.println("Selection is: " + selection5);
        System.out.println("Using rule: " + rule5);
        System.out.println("Number Matches: " +matches5.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches5) {
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");



        System.out.println("Test6");
        System.out.println("Expr is: " + Trees.weirdBrokenabsZeroequivXandY());
        INode selection6 = Trees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule6 =Trees.goldenRulePQ();
        Set<Matcher.Match> matches6 = matcher.match(selection6,rule6);
        System.out.println("Selection is: " + selection6);
        System.out.println("Using rule: " + rule6);
        System.out.println("Number Matches: " +matches6.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches6) {
            System.out.println(m);
        }

        System.out.println();
        Vector<Matcher.Match> mmm = new Vector<>(matches6);
        System.out.println(mmm.size());
        System.out.println(mmm.get(0));
        System.out.println(mmm.get(1));
        System.out.println(mmm.get(0).getRootOfExpr()==mmm.get(1).getRootOfExpr());




    }

}
