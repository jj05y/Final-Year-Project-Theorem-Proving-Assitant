package workers;

import constants.Operators;
import beans.ExprAndHintandTransition;
import gui.core.AlertMessage;
import gui.core.State;
import interfaces.ITerminal;
import terminals.Literal;
import terminals.QuantifiedExpr;
import util.LazySet;
import interfaces.INode;
import nodes.NodeForBrackets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by joe on 14/12/16.
 */
public class Replacer {


    public Set<ExprAndHintandTransition> getReplacements(INode subExpr, INode rule, boolean withPopUpForExtras) {
        Set<ExprAndHintandTransition> replacements = new LazySet<>();
        Matcher matcher = new Matcher();

        Set<Matcher.Match> matches = new LazySet<>();
        for (INode rulePerm : (new TreePermuter()).getTreesForExpressionWithCommutativeOptions(rule)) {
            matches.addAll(matcher.match(subExpr, rulePerm));
        }
        for (Matcher.Match match : matches) {
            //System.out.println("MATCH:" + match);
            //get the rule without the subtree, (the matched one that we just found)
            Remover remover = new Remover();
            INode ruleWithoutMatchedNode = remover.treeWithoutNode(match.getRootOfExpr(), match.getRootOfMatchedNode());

            Set<INode> unknownMappings =findUnknowns(ruleWithoutMatchedNode, match.getLoopUpTable());
           if (withPopUpForExtras && !unknownMappings.isEmpty()){
               HashMap<String, INode> extraMappings = (new AlertMessage(unknownMappings, match, rule)).getGetExtraMappings();
                for (String key : extraMappings.keySet()) {
                    match.getLoopUpTable().put(key, extraMappings.get(key));
                }
            }
            //need to walk that tree and rename it
            Renamer renamer = new Renamer();
            INode renamedRuleWithoutMatchNode = renamer.renameIdsWithLookupTable(ruleWithoutMatchedNode, match.getLoopUpTable());

            //TODO this a bit messy, relying on parent of subexpr
            //need a copy of subExpr for each match
            INode copyOfSubExpr = subExpr.copyWholeTree();
            INode parentOfSubExpr = copyOfSubExpr.getParent();

            if (copyOfSubExpr.isRoot()) {

                parentOfSubExpr = renamedRuleWithoutMatchNode.copySubTree();
            } else {
                //brackets are needed if the inserted "new bit" contains an operator with lower
                // precedence than that of the operator at the node to which the new bit it is attached
                if (!(parentOfSubExpr instanceof NodeForBrackets) && !(parentOfSubExpr instanceof QuantifiedExpr)) { //no need for brackets if they're already there
                    String opAtNodeToWhichNewBitIsAttached = parentOfSubExpr.getNodeChar();

                    //find lowest precedence in new bit (renamed rule without matched node)
                    int lowestPrecedence = Operators.findLowestPrecendence(renamedRuleWithoutMatchNode, Integer.MAX_VALUE);

                    //now check if lowest precedence is less than that of opAtNodeToWhichNewBitIsAttached
                    if (lowestPrecedence <= Operators.precedence.get(opAtNodeToWhichNewBitIsAttached)) {
                        //add brackets
                        renamedRuleWithoutMatchNode = new NodeForBrackets(renamedRuleWithoutMatchNode, renamedRuleWithoutMatchNode.getParent());
                    }
                }

                if (parentOfSubExpr instanceof QuantifiedExpr) {
                    QuantifiedExpr quant = ((QuantifiedExpr) parentOfSubExpr);
                    if (quant.getRange() == copyOfSubExpr) {
                        quant.setRange(renamedRuleWithoutMatchNode);
                    } else if (quant.getTerm() == copyOfSubExpr) {
                        quant.setTerm(renamedRuleWithoutMatchNode);
                    } else {
                        //TODO exception
                    }
                } else {
                    if (parentOfSubExpr.children()[0] == copyOfSubExpr) { //subexpression was a left child
                        parentOfSubExpr.children()[0] = renamedRuleWithoutMatchNode;
                    } else if (parentOfSubExpr.children().length > 1 && parentOfSubExpr.children()[1] == copyOfSubExpr) { // subexpresssion was a right child
                        parentOfSubExpr.children()[1] = renamedRuleWithoutMatchNode;
                    } else {
                        //TODO exception
                    }

                }
            }

            replacements.add(new ExprAndHintandTransition(match.getLoopUpTable(), parentOfSubExpr.getRoot(), match.getTransition()));
        }

        return replacements;
    }

    private Set<INode> findUnknowns(INode node, Map<String, INode> lookUpTable) {
        Set<INode> unknowns = new LazySet<>();
        Set<INode> nodesInNode = new LazySet<>();
        walkForUnknownNodes(node, nodesInNode);
        for (INode n : nodesInNode) {
            if (!lookUpTable.containsKey(n.toString())) {
                unknowns.add(n);
            }
        }
        return unknowns;
    }

    private void walkForUnknownNodes(INode node, Set<INode> nodes) {
        if (node instanceof ITerminal) {

            if (node instanceof QuantifiedExpr || node instanceof Literal) {
                //do nothing
                return;
            }

            nodes.add(node);
            return;
        }

        walkForUnknownNodes(node.children()[0],nodes);
        if (node.children().length>1) walkForUnknownNodes(node.children()[1], nodes);


        return;

    }



}
