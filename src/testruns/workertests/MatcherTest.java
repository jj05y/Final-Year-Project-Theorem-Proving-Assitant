package testruns.workertests;

import interfaces.INode;
import trees.BoolTrees;
import workers.Matcher;

import java.util.Set;
import java.util.Vector;

/**
 * Created by joe on 12/11/16.
 */
public class MatcherTest {

    public static void main(String[] args) {
        Matcher matcher = new Matcher();

 /*       System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + trees.XandYandZ() + " in " + trees.goldenRule());
        for (Matcher.Match m : matcher.match(trees.XandYandZ(), trees.goldenRule())){
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + trees.XandYorZ() + " in " + trees.goldenRule());
        for (Matcher.Match m : matcher.match(trees.XandYorZ(), trees.goldenRule())){
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + trees.ZandW() + " in " + trees.goldenRule());
        for (Matcher.Match m : matcher.match(trees.ZandW(), trees.goldenRule())){
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + trees.YequivX() + " in " + trees.goldenRule());
        for (Matcher.Match m : matcher.match(trees.YequivX(), trees.goldenRule())){
            System.out.println(m);
        }

        System.out.println("\n*******************************************************************");
        System.out.println("*******************************************************************");
        System.out.println("*******************************************************************\n");
*/
        System.out.println("Test1");
        System.out.println("Expr is: " + BoolTrees.absZeroequivXandY());
        INode selection1 = BoolTrees.absZeroequivXandY().children()[0];
        INode rule1 = BoolTrees.absZero();
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
        System.out.println("Expr is: " + BoolTrees.absZeroequivXandY());
        INode selection2 = BoolTrees.absZeroequivXandY().children()[0];
        INode rule2 = BoolTrees.goldenRulePQ();
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
        System.out.println("Expr is: " + BoolTrees.weirdabsZeroequivXandY());
        INode selection3 = BoolTrees.weirdabsZeroequivXandY().children()[0];
        INode rule3 = BoolTrees.absZero();
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
        System.out.println("Expr is: " + BoolTrees.weirdBrokenabsZeroequivXandY());
        INode selection4 = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0];
        INode rule4 = BoolTrees.absZero();
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
        System.out.println("Expr is: " + BoolTrees.weirdBrokenabsZeroequivXandY());
        INode selection5 = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule5 = BoolTrees.absZero();
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
        System.out.println("Expr is: " + BoolTrees.weirdBrokenabsZeroequivXandY());
        INode selection6 = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule6 = BoolTrees.goldenRulePQ();
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


        System.out.println("\n*******************************************************************\n");



        System.out.println("Test7");
        System.out.println("Expr is: " + BoolTrees.edgeCaseOne());
        INode selection7 = BoolTrees.edgeCaseOne().children()[0].children()[0];
        INode rule7 = BoolTrees.goldenRulePQ();
        Set<Matcher.Match> matches7 = matcher.match(selection7,rule7);
        System.out.println("Selection is: " + selection7);
        System.out.println("Using rule: " + rule7);
        System.out.println("Number Matches: " +matches7.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches7) {
            System.out.println(m);
        }

        System.out.println("\n*******************************************************************\n");



        System.out.println("Test8");
        System.out.println("Expr is: " + BoolTrees.PandQandW());
        INode selection8 = BoolTrees.PandQandW().children()[0];
        INode rule8 = BoolTrees.XandYimplX();
        Set<Matcher.Match> matches8 = matcher.match(selection8,rule8);
        System.out.println("Selection is: " + selection8);
        System.out.println("Using rule: " + rule8);
        System.out.println("Number Matches: " +matches8.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches8) {
            System.out.println(m);
        }
        System.out.println("\n*******************************************************************\n");



        System.out.println("Test9");
        System.out.println("Expr is: " + BoolTrees.XorX());
        INode selection9 = BoolTrees.XorX();
        INode rule9 = BoolTrees.orIdem();
        Set<Matcher.Match> matches9 = matcher.match(selection9,rule9);
        System.out.println("Selection is: " + selection9);
        System.out.println("Using rule: " + rule9);
        System.out.println("Number Matches: " +matches9.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches9) {
            System.out.println(m);
        }

    }

}
