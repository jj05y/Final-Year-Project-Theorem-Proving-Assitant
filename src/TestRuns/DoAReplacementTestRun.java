package TestRuns;

import Interfaces.INode;
import Trees.Trees;

/**
 * Created by joe on 14/11/16.
 */
public class DoAReplacementTestRun {

    public static void main (String[] args) {


        //Get a rule
        INode goldenRule = Trees.goldenRule();

        //get an expression
        INode expression = Trees.XandYequivalZandW();

        //find a valid substring of it (the node at the root of the valid substring
        INode subExpr = expression.children()[1];
        System.out.println(subExpr);

        //find an equivalent shape in the rule and get the look up table

        //get the rule without the shape

        //walk the tree (rule without shape) and rename using lookup table,
        //if the tree has something in it not in the look up table, need to add a new var, NOT in original tree

        //attatch that tree where we took the valid substring

        //done


    }
}
