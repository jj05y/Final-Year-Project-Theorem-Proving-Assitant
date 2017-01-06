package testruns.workertests;

import constants.Operators;
import beans.MatchAndTransition;
import interfaces.INode;
import nodes.BinaryOperator;
import trees.BoolTrees;
import workers.TreePermuter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by joe on 30/10/16.
 */
public class TreePermuterTest {

    public static void main(String[] args) {

        TreePermuter permuter = new TreePermuter();

        long start = System.currentTimeMillis();

        System.out.println("Valid Substrings of: " + BoolTrees.XandYandZ());
        permuter.reportOn(BoolTrees.XandYandZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.XandYequivalZ());
        permuter.reportOn(BoolTrees.XandYequivalZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.XandYequivalZandW());
        permuter.reportOn(BoolTrees.XandYequivalZandW());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.XandYequivalXandYequivalZ());
        permuter.reportOn(BoolTrees.XandYequivalXandYequivalZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.XandYorZwithBrackets());
        permuter.reportOn(BoolTrees.XandYorZwithBrackets());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.XandYorZ());
        permuter.reportOn(BoolTrees.XandYorZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.goldenRule());
        permuter.reportOn(BoolTrees.goldenRule());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.braker());
        permuter.reportOn(BoolTrees.braker());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.andOverOr());
        permuter.reportOn(BoolTrees.andOverOr());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");
        System.out.println("\n**********************************************************\n");
        System.out.println("\n**********************************************************\n");


        System.out.println("Testing perms with equival as parent and matching op AND");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.goldenRule());
        Set<String> uniques = new HashSet<>();
        for (MatchAndTransition n : permuter.nodesWithJoinersAsParentAndMatchingOp(BoolTrees.goldenRule(), Operators.AND)){
            uniques.add(n.toString());
        }
        for (String s : uniques) {
            System.out.println(s);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("Testing perms with equival as parent and matching op EQUIVAL (just the ones shorter than GR)");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.goldenRule());
        uniques = new HashSet<>();
        for (MatchAndTransition node : permuter.nodesWithJoinersAsParentAndMatchingOp(BoolTrees.goldenRule(), Operators.EQUIVAL)){
            uniques.add(node.toString());
        }
        for (String s : uniques) {
            System.out.println(s);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("###########################################################################################");

        System.out.println("Testing perms with equival as parent and ID as a child");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + BoolTrees.goldenRule());
        uniques = new HashSet<>();
        for (MatchAndTransition node : permuter.idNodesWithJoinerAsParent(BoolTrees.goldenRule())){
            uniques.add(node.toString());
        }
        for (String s : uniques) {
            System.out.println(s);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");



        System.out.println("###########################################################################################");

        INode newTellChildTest = BoolTrees.equivAssoc();
        System.out.println(newTellChildTest);
        System.out.println(newTellChildTest.children()[0].children()[0].getParent().getParent());


        System.out.println("###########################################################################################");
        System.out.println("testing commutative perms around equival");
        System.out.println("golden rule:");
        start = System.currentTimeMillis();
        for (INode n : permuter.getTreesForExpressionWithCommutativeOptions(BoolTrees.goldenRule())) {
            System.out.println(n);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms\n");
        System.out.println("goldenrule+1:");
        start = System.currentTimeMillis();
        for (INode n : permuter.getTreesForExpressionWithCommutativeOptions(new BinaryOperator(Operators.EQUIVAL, BoolTrees.goldenRule(), BoolTrees.absZeroequivXandY()))) {
            System.out.println(n);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms\n");



    }

}
