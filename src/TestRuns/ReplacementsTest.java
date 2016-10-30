package TestRuns;

import Interfaces.INode;
import Constants.Operators;
import Trees.Trees;
import Workers.Finder;
import Workers.Replacer;

import java.util.List;

/**
 * Created by joe on 23/10/16.
 */
public class ReplacementsTest {

    public static void main(String[] args) {

        Finder finder = new Finder();
        Replacer replacer = new Replacer();

        String XandYStr = "X " + Operators.AND + " Y";

        System.out.println("\nLooking for " + XandYStr + " in: " + Trees.goldenRule());
        INode goldenRule = Trees.goldenRule();

        INode rootOfXandYStr = finder.find(goldenRule, XandYStr);
        System.out.println(goldenRule);
        System.out.println(rootOfXandYStr);
        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copy()
        List<INode> replacements = replacer.replace(goldenRule, rootOfXandYStr);
        System.out.println("found it! " + rootOfXandYStr);


        System.out.println("\nReplacements for " + XandYStr + " from " + Trees.goldenRule());
        for (INode replacement : replacements) {
            System.out.println(replacement.toString());
        }


        String X = "X";

        System.out.println("\nLooking for " + X + " in: " + Trees.goldenRule());
        INode goldenRule2 = Trees.goldenRule();

        INode rootOfX = finder.find(goldenRule2, X);
        System.out.println("found it! " + rootOfX);

        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copy()
        List<INode> replacements2 = replacer.replace(goldenRule2, rootOfX);


        System.out.println("\nReplacements for " + X + " from " + Trees.goldenRule());
        for (INode replacement : replacements2) {
            System.out.println(replacement.toString());
        }

        String XandYeqivalX = "X " + Operators.AND + " Y " + Operators.EQUIVAL + " X" ;

        System.out.println("\nLooking for " + X + " in: " + Trees.goldenRule());
        INode goldenRule3 = Trees.goldenRule();

        INode rootOfXandYequivalX = finder.find(goldenRule3, XandYeqivalX);
        System.out.println("found it! " + rootOfXandYequivalX);

        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copy()
        List<INode> replacements3 = replacer.replace(goldenRule3, rootOfXandYequivalX);


        System.out.println("\nReplacements for " + X + " from " + Trees.goldenRule());
        for (INode replacement : replacements3) {
            System.out.println(replacement.toString());
        }



    }



}
