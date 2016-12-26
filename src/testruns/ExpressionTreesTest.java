package testruns;

import com.sun.org.apache.xpath.internal.SourceTree;
import constants.Operators;
import interfaces.INode;
import trees.Trees;
import workers.TreePermuter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by joe on 30/10/16.
 */
public class ExpressionTreesTest {

    public static void main(String[] args) {

        TreePermuter permuter = new TreePermuter();


        INode expression = Trees.braker();

        long start = System.currentTimeMillis();
        List<INode> slowList = permuter.getTreesForExpression(expression);
        long slow = System.currentTimeMillis()-start;
        start = System.currentTimeMillis();
        List<INode> fastList = permuter.getEfficientTreesForExpression(expression);
        long fast = System.currentTimeMillis()-start;

        System.out.println("Slow list: " + slow + "ms");
        for (INode node : slowList) {
            System.out.println(node);
        }
        System.out.println("\nFast List: " + fast + "ms");
        for (INode node : fastList) {
            System.out.println(node);
        }

        //are they the same?
        if (fastList.size() != slowList.size()) {
            System.out.println("Not the same :(");
        } else {
            boolean winning = true;
            for (int i = 0; i < fastList.size(); i++ ){
                INode fastOne = fastList.get(i);
                INode slowOne = slowList.get(i);
                if (!fastOne.equals(slowOne)){
                    winning = false;
                }
            }
            System.out.println("\nAre the tree's in the list the same?: " + winning);

        }


    }

}
