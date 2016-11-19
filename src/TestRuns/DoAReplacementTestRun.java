package TestRuns;

import Core.LazySet;
import Core.TreeAndSubTree;
import Interfaces.INode;
import Trees.Trees;
import Workers.Finder;
import Workers.Matcher;
import Workers.Remover;
import Workers.Renamer;

import java.util.List;
import java.util.Set;

/**
 * Created by joe on 14/11/16.
 */
public class DoAReplacementTestRun {

    public static void main (String[] args) {


        //Get a rule
        INode rule = Trees.goldenRulePQ();
        System.out.println("Using rule: " + rule);

        //get an expression
        INode expression = Trees.XandYequivalZandW();
        System.out.println("Using Expression " + expression + "\n");

        //selct a valid substring of it (the node at the root of the valid substring
        INode subExpr = Trees.XandY();
        System.out.println("selected sub expression is: " + subExpr + "\n");

        //find an equivalent shape in the rule and get the look up table
        Matcher matcher = new Matcher();
        Set<Matcher.Match> matches = matcher.match(subExpr,rule);
        System.out.println("matches for " + subExpr + " in " + rule);
        for (Matcher.Match match : matches){
            System.out.println(match);
        }
        System.out.println();
        System.out.println("There are many paths to be explored:");

        //find the node in the rule that has the match
        Finder finder = new Finder();
        for (Matcher.Match match : matches) {
            System.out.println("*** Exploring match: " + match + " ***");

            Set<TreeAndSubTree> treeAndSubTrees = finder.find(rule, match.getRootOfMatchedNode());
            System.out.println("Trees and Subtrees when looking for " + match + " in " + rule);
            System.out.println();

            for (TreeAndSubTree treeAndSubTree : treeAndSubTrees) {
                System.out.println(treeAndSubTree);

                //get the rule without the subtree, (the matched one that we just found)
                Remover remover = new Remover();
                INode ruleWithoutMAtchedNode = remover.treeWithoutNode(rule,treeAndSubTree.getSubTree());
                System.out.println("Tree without it's subtree: " + ruleWithoutMAtchedNode);

                //need to walk that tree and rename it
                Renamer renamer = new Renamer();
                INode renamedRuleWithoutMatchNode = renamer.renameIdsWithLookupTable(ruleWithoutMAtchedNode, match.getLoopUpTable());
                System.out.println("renamed rule without matched node: " + renamedRuleWithoutMatchNode);

                //need to stick that renamed node, onto the original expression,
                INode copyOfExpr = expression.copy();
                LazySet<TreeAndSubTree> subExprInExpr = finder.find(copyOfExpr, subExpr);
                for (TreeAndSubTree treeAndSubTree1 : subExprInExpr) {
                    if (treeAndSubTree1.getSubTree().getParent().children()[0] == treeAndSubTree1.getSubTree()) {
                        treeAndSubTree1.getSubTree().getParent().children()[0] = renamedRuleWithoutMatchNode;
                    } else {
                        if (treeAndSubTree1.getSubTree().children().length>1 && treeAndSubTree1.getSubTree().getParent().children()[1] == treeAndSubTree1.getSubTree()){
                            treeAndSubTree1.getSubTree().getParent().children()[1] = renamedRuleWithoutMatchNode;
                         }
                    }
                    System.out.println(treeAndSubTree1.getTree());
                }
                System.out.println();
            }
            System.out.println();


        }






        //walk the tree (rule without shape) and rename using lookup table,
        //if the tree has something in it not in the look up table, need to add a new var, NOT in original tree

        //attatch that tree where we took the valid substring

        //done


    }
}
