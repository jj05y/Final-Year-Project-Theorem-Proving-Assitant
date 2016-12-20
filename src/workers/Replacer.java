package workers;

import core.ExprAndHint;
import core.LazySet;
import interfaces.INode;

import java.util.Set;

/**
 * Created by joe on 14/12/16.
 */
public class Replacer {

    public Set<ExprAndHint> getReplacements(INode subExpr, INode rule) {
        Set<ExprAndHint> replacements = new LazySet<>();

        Matcher matcher = new Matcher();
        Set<Matcher.Match> matches = matcher.match(subExpr, rule);

        for (Matcher.Match match : matches) {
            //get the rule without the subtree, (the matched one that we just found)
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
            replacements.add(new ExprAndHint(match.getLoopUpTable(),parentOfSubExpr.getRoot()));
        }
        return replacements;
    }
}
