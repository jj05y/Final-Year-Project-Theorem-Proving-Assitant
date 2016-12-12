package TestRuns;

import Constants.Operators;
import Core.TreeAndSubTree;
import Interfaces.INode;
import Trees.Trees;
import Workers.TreePermuter;
import jdk.nashorn.internal.ir.IfNode;

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
        for (INode n : permuter.nodesWithEquivAsParentAndMatchingOp(Trees.goldenRule(), Operators.AND)){
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
        for (TreeAndSubTree tst : permuter.nodesWithEquivAsParentAndMatchingOp(Trees.goldenRule(), Operators.EQUIVAL)){
            uniques.add(tst.getSubTree().toString());
        }
        for (String s : uniques) {
            System.out.println(s);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");

    }

}
