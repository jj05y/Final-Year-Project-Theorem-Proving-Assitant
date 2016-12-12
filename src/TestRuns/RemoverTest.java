package TestRuns;

import Interfaces.INode;
import Constants.Operators;
import Trees.Trees;
import Workers.Finder;
import Workers.Remover;

import java.util.List;

/**
 * Created by joe on 23/10/16.
 */
public class RemoverTest {

    public static void main(String[] args) {

        Finder finder = new Finder();
        Remover remover = new Remover();

        String XandYStr = "X " + Operators.AND + " Y";

        System.out.println("\nLooking for " + XandYStr + " in: " + Trees.goldenRule());
        INode goldenRule = Trees.goldenRule();

        System.out.println("removing " + goldenRule.children()[0]);
        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copySubTree()
        INode goldenRuleWithoutXandY = remover.treeWithoutNode(goldenRule, goldenRule.children()[0]);
        System.out.println("Golder rule without y quiv x or y: " + goldenRuleWithoutXandY);


/*
        String X = "X";

        System.out.println("\nLooking for " + X + " in: " + Trees.goldenRule());
        INode goldenRule2 = Trees.goldenRule();

        INode rootOfX = finder.find(goldenRule2, X);
        System.out.println("found it! " + rootOfX);

        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copySubTree()
        List<INode> replacements2 = remover.treeWithoutNode(goldenRule2, rootOfX);


        System.out.println("\nReplacements for " + X + " from " + Trees.goldenRule());
        for (INode replacement : replacements2) {
            System.out.println(replacement.toString());
        }

        String XandYeqivalX = "X " + Operators.AND + " Y " + Operators.EQUIVAL + " X" ;

        System.out.println("\nLooking for " + X + " in: " + Trees.goldenRule());
        INode goldenRule3 = Trees.goldenRule();

        INode rootOfXandYequivalX = finder.find(goldenRule3, XandYeqivalX);
        System.out.println("found it! " + rootOfXandYequivalX);

        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copySubTree()
        List<INode> replacements3 = remover.treeWithoutNode(goldenRule3, rootOfXandYequivalX);


        System.out.println("\nReplacements for " + X + " from " + Trees.goldenRule());
        for (INode replacement : replacements3) {
            System.out.println(replacement.toString());
        }*/



    }



}
