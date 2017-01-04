package testruns.workertests;

import interfaces.INode;
import trees.BoolTrees;
import trees.QuantExprs;
import trees.QuantTrees;
import workers.Matcher;

import java.util.Set;
import java.util.Vector;

/**
 * Created by joe on 12/11/16.
 */
public class MatcherQuantTest {

    public static void main(String[] args) {
        Matcher matcher = new Matcher();

        System.out.println("Test1");
        INode expr = QuantTrees.XorAllRiXorFi();
        System.out.println("Expr is: " + expr);
        INode selection1 = expr.children()[1];
        INode rule1 = QuantTrees.orOverForAll();

        System.out.println("Selection is: " + selection1);
        System.out.println("Using rule: " + rule1);

        Set<Matcher.Match> matches1 = matcher.match(selection1,rule1);
        System.out.println("Number Matches: " +matches1.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches1) {
            System.out.println(m);
        }

        System.out.println("\n##############################################################################\n");


        System.out.println("Test2");
        INode expr2 = QuantTrees.exprWithTwoQuants();
        System.out.println("Expr is: " + expr2);
        INode selection2 = expr2.children()[0];
        INode rule2 = QuantTrees.ruleWithTwoQuants();

        System.out.println("Selection is: " + selection2);
        System.out.println("Using rule: " + rule2);

        Set<Matcher.Match> matches2 = matcher.match(selection2,rule2);
        System.out.println("Number Matches: " +matches2.size());
        System.out.println("Matches and their lookup table:");
        for (Matcher.Match m : matches2) {
            System.out.println(m);
        }

    }

}
