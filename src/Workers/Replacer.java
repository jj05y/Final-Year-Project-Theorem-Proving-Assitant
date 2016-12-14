package Workers;

import Core.LazySet;
import Interfaces.INode;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by joe on 14/12/16.
 */
public class Replacer {

    public Set<INode> getReplacements(INode subExpr, INode rule) {
        Set<INode> replacements = new LazySet<>();

        Matcher matcher = new Matcher();
        Set<Matcher.Match> matches = matcher.match(subExpr, rule);

        for (Matcher.Match match : matches) {
            //get the rule without the subtree, (the matched one that we just found)
            System.out.println("444 " + match);
            Remover remover = new Remover();
            INode ruleWithoutMatchedNode = remover.treeWithoutNode(match.getRootOfExpr(), match.getRootOfMatchedNode());

            //need to walk that tree and rename it
            Renamer renamer = new Renamer();
            INode renamedRuleWithoutMatchNode = renamer.renameIdsWithLookupTable(ruleWithoutMatchedNode, match.getLoopUpTable());


            //TODO this a bit messy, relying on parent of subexpr
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
            replacements.add(parentOfSubExpr.getRoot());
        }
        return replacements;
    }
}
