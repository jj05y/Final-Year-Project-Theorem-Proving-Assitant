package testruns.workertests;

import beans.ExprAndHintandTransition;
import interfaces.INode;
import trees.BoolTrees;
import workers.Replacer;
import workers.TreePermuter;

import java.util.List;

/**
 * Created by joe on 14/12/16.
 */
public class ReplacerTestHangOnChooseOption {

    public static void main (String[] args) {
        Replacer replacer = new Replacer();

        long start = System.currentTimeMillis();

        System.out.println("Expression is: " + BoolTrees.XandYorZwithBrackets());
        INode selection1 = BoolTrees.XandYorZwithBrackets().children()[1];
        INode rule1 = BoolTrees.goldenRulePQ();
        System.out.println("Selection is: " + selection1);
        System.out.println("Using rule: " + rule1);
        System.out.println("New Expression Options: ");
        INode choice= null;
        for (ExprAndHintandTransition replacement : replacer.getReplacements(selection1,rule1)) {
            System.out.println(replacement);
            choice = replacement.getExpression();
        }


        System.out.println("Time: " + (System.currentTimeMillis()-start)+"ms");
        System.out.println("getting trees for: " + choice);
        List<INode> list = ((new TreePermuter()).getTreesForExpression(choice));
        System.out.println(list.size());


    }
}
