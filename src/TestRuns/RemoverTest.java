package TestRuns;

import Interfaces.INode;
import Constants.Operators;
import Trees.Trees;
import Workers.Remover;

/**
 * Created by joe on 23/10/16.
 */
public class RemoverTest {

    public static void main(String[] args) {

        Remover remover = new Remover();

        String XandYStr = "X " + Operators.AND + " Y";

        System.out.println("gr: " + Trees.goldenRule());
        INode goldenRule = Trees.goldenRule();

        System.out.println("removing " + goldenRule.children()[0]);
        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copySubTree()
        INode goldenRuleWithoutXandY = remover.treeWithoutNode(goldenRule, goldenRule.children()[0]);
        System.out.println("Golder rule without: " + goldenRuleWithoutXandY);


    }



}
