package TestRuns;

import Interfaces.INode;
import Trees.Trees;

/**
 * Created by joe on 12/12/16.
 */
public class WholeTreeCopyTest {

    public static void main(String[] args) {

        INode goldenRule = Trees.goldenRulePQ();
        System.out.println("gr: " + goldenRule);


        INode someSubtree = goldenRule.children()[0].children()[1].copyWholeTree();
        System.out.println("some sub tree copy: " + someSubtree);
        //find root of that subtree
        while (someSubtree.getParent()!= null) {
            someSubtree = someSubtree.getParent();
        }
        System.out.println("whole tree found from some subtree: " + someSubtree);
        System.out.println("is the new tree the same ref as the old tree? " + (someSubtree == goldenRule));
    }
}
