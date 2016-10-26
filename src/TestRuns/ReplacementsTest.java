package TestRuns;

import Interfaces.INode;
import Operators.Operators;
import Trees.Trees;
import Workers.Finder;
import Workers.NewTreePermuter;
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


        INode goldenRule = Trees.goldenRule();

        INode rootOfXandYStr = finder.find(goldenRule, XandYStr);
        System.out.println(goldenRule);
        System.out.println(rootOfXandYStr);
        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copy()
        List<INode> replacements = Replacer.replace(goldenRule, rootOfXandYStr);
        System.out.println("found it! " + rootOfXandYStr);


        System.out.println("\nReplacements for " + XandYStr + " from " + Trees.goldenRule());
        for (INode replacement : replacements) {
            System.out.println(replacement.toString());
        }


        String X = "X";

        System.out.println("\n");
        INode goldenRule2 = Trees.goldenRule();

        INode rootOfX = finder.find(goldenRule2, X);
        System.out.println("found it! " + rootOfX);

        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copy()
        List<INode> replacements2 = Replacer.replace(goldenRule2, rootOfX);


        System.out.println("\nReplacements for " + X + " from " + Trees.goldenRule());
        for (INode replacement : replacements2) {
            System.out.println(replacement.toString());
        }

    }



}
