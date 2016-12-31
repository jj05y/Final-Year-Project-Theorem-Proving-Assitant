package testruns;

import constants.Operators;
import core.MatchAndTransition;
import interfaces.INode;
import trees.Trees;
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

        System.out.println("Valid Substrings of: " + Trees.XandYandZ());
        permuter.reportOn(Trees.XandYandZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.XandYequivalZ());
        permuter.reportOn(Trees.XandYequivalZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.XandYequivalZandW());
        permuter.reportOn(Trees.XandYequivalZandW());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.XandYequivalXandYequivalZ());
        permuter.reportOn(Trees.XandYequivalXandYequivalZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.XandYorZwithBrackets());
        permuter.reportOn(Trees.XandYorZwithBrackets());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.XandYorZ());
        permuter.reportOn(Trees.XandYorZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.goldenRule());
        permuter.reportOn(Trees.goldenRule());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.braker());
        permuter.reportOn(Trees.braker());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.andOverOr());
        permuter.reportOn(Trees.andOverOr());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");
        System.out.println("\n**********************************************************\n");
        System.out.println("\n**********************************************************\n");


        System.out.println("Testing perms with equival as parent and matching op AND");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.goldenRule());
        Set<String> uniques = new HashSet<>();
        for (MatchAndTransition n : permuter.nodesWithJoinersAsParentAndMatchingOp(Trees.goldenRule(), Operators.AND)){
            uniques.add(n.toString());
        }
        for (String s : uniques) {
            System.out.println(s);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("Testing perms with equival as parent and matching op EQUIVAL (just the ones shorter than GR)");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.goldenRule());
        uniques = new HashSet<>();
        for (MatchAndTransition node : permuter.nodesWithJoinersAsParentAndMatchingOp(Trees.goldenRule(), Operators.EQUIVAL)){
            uniques.add(node.toString());
        }
        for (String s : uniques) {
            System.out.println(s);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("###########################################################################################");

        System.out.println("Testing perms with equival as parent and ID as a child");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.goldenRule());
        uniques = new HashSet<>();
        for (MatchAndTransition node : permuter.idNodesWithJoinerAsParent(Trees.goldenRule())){
            uniques.add(node.toString());
        }
        for (String s : uniques) {
            System.out.println(s);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("###########################################################################################");
        System.out.println("unique strings");
        for (String s :permuter.getListOfValidSubStrings(Trees.XandYandZ())) {
            System.out.println(s);
        }

        System.out.println("###########################################################################################");

        INode newTellChildTest = Trees.equivAssoc();
        System.out.println(newTellChildTest);
        System.out.println(newTellChildTest.children()[0].children()[0].getParent().getParent());

    }

}
