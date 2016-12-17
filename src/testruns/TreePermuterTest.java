package testruns;

import constants.Operators;
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
/*
        System.out.println("Valid Substrings of: " + trees.XandYandZ());
        permuter.reportOn(trees.XandYandZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + trees.XandYequivalZ());
        permuter.reportOn(trees.XandYequivalZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + trees.XandYequivalZandW());
        permuter.reportOn(trees.XandYequivalZandW());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + trees.XandYequivalXandYequivalZ());
        permuter.reportOn(trees.XandYequivalXandYequivalZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + trees.XandYorZwithBrackets());
        permuter.reportOn(trees.XandYorZwithBrackets());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + trees.XandYorZ());
        permuter.reportOn(trees.XandYorZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + trees.goldenRule());
        permuter.reportOn(trees.goldenRule());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + trees.braker());
        permuter.reportOn(trees.braker());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + trees.andOverOr());
        permuter.reportOn(trees.andOverOr());
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("\n**********************************************************\n");
        System.out.println("\n**********************************************************\n");
        System.out.println("\n**********************************************************\n");

        System.out.println("Testing perms with equival as parent and matching op AND");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + trees.goldenRule());
        Set<String> uniques = new HashSet<>();
        for (INode n : permuter.nodesWithEquivAsParentAndMatchingOp(trees.goldenRule(), Operators.AND)){
            uniques.add(n.toString());
        }
        for (String s : uniques) {
            System.out.println(s);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
*/
        System.out.println("Testing perms with equival as parent and matching op EQUIVAL (just the ones shorter than GR)");

        start = System.currentTimeMillis();
        System.out.println("Valid Substrings of: " + Trees.goldenRule());
        Set<String> uniques = new HashSet<>();
        for (INode node : permuter.nodesWithEquivAsParentAndMatchingOp(Trees.goldenRule(), Operators.EQUIVAL)){
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
        for (INode node : permuter.idNodesWithEquivAsParent(Trees.goldenRule())){
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

    }

}
