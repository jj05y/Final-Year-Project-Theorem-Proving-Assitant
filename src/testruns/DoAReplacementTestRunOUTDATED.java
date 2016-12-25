package testruns;

import interfaces.INode;
import trees.Trees;
import workers.Matcher;
import workers.Remover;
import workers.Renamer;

import java.util.Set;

/**
 * Created by joe on 14/11/16.
 */
public class DoAReplacementTestRunOUTDATED {

    public static void main(String[] args) {


        //Get a rule
        INode rule = Trees.goldenRulePQ();
        System.out.println("Using rule: " + rule);

        //get an expression
        INode expression = Trees.XandYequivalZandW();
        System.out.println("Using Expression " + expression + "\n");

        //selct a valid substring of it (the node at the root of the valid substring
        INode subExpr = expression.children()[0];
        System.out.println("selected sub expression is: " + subExpr + "\n");

        //find an equivalent shape in the rule and get the look up table
        Matcher matcher = new Matcher();
        Set<Matcher.Match> matches = matcher.match(subExpr, rule);
        System.out.println("matches for " + subExpr + " in " + rule);
        for (Matcher.Match match : matches) {
            System.out.println(match);
        }
        System.out.println();
        System.out.println("There are potentially many matches to be explored:");

        for (Matcher.Match match : matches) {
            System.out.println("*** Exploring match ***\n" + match);

            System.out.println();
            //get the rule without the subtree, (the matched one that we just found)
            Remover remover = new Remover();
            System.out.println("Removing " + match.getRootOfMatchedNode() + " from " + match.getRootOfExpr());
            INode ruleWithoutMatchedNode = remover.treeWithoutNode(match.getRootOfExpr(), match.getRootOfMatchedNode());
            System.out.println("Tree without it's subtree: " + ruleWithoutMatchedNode);

            //need to walk that tree and rename it
            Renamer renamer = new Renamer();
            INode renamedRuleWithoutMatchNode = renamer.renameIdsWithLookupTable(match.getRootOfExpr(), match.getLoopUpTable());
            System.out.println("renamed rule without matched node: " + renamedRuleWithoutMatchNode);

            System.out.println();

            //attach that tree where we took the valid substring
            //TODO this a bit messys, relying on parent of subexpr

            //need a copy of subExpr for each match
            INode copyOfSubExpr = subExpr.copyWholeTree();
            INode parentOfSubExpr = copyOfSubExpr.getParent();
            if (copyOfSubExpr.isRoot()) {
                parentOfSubExpr = renamedRuleWithoutMatchNode;
            } else {
                if (parentOfSubExpr.children()[0] == copyOfSubExpr) { //subexpression was a left child
                    parentOfSubExpr.children()[0] = renamedRuleWithoutMatchNode;
                } else if (parentOfSubExpr.children().length > 1 && parentOfSubExpr.children()[1] == copyOfSubExpr) { // subexpresssion was a right child
                    parentOfSubExpr.children()[1] = renamedRuleWithoutMatchNode;
                }
            }
            System.out.println("New expression: " + parentOfSubExpr.getRoot() + "\n\n");
        }


        //TODO if the tree has something in it not in the look up table, need to add a new var, NOT in original tree, no clue where, not here i think :/


    }
}
