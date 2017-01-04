package testruns.workertests;

import interfaces.INode;
import constants.Operators;
import trees.BoolTrees;
import workers.Remover;

/**
 * Created by joe on 23/10/16.
 */
public class RemoverTest {

    public static void main(String[] args) {

        Remover remover = new Remover();

        String XandYStr = "X " + Operators.AND + " Y";

        System.out.println("gr: " + BoolTrees.goldenRule());
        INode goldenRule = BoolTrees.goldenRule();

        System.out.println("removing " + goldenRule.children()[0]);
        //gotta be careful, that these 2 nodes belong to the same tree!! careful with copySubTree()
        INode goldenRuleWithoutXandY = remover.treeWithoutNode(goldenRule, goldenRule.children()[0]);
        System.out.println("Golder rule without: " + goldenRuleWithoutXandY);


    }



}
