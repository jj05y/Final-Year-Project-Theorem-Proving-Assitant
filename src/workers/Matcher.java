package workers;

import terminals.ArrayAndIndex;
import util.LazySet;
import beans.MatchAndTransition;
import interfaces.INode;
import interfaces.ITerminal;
import terminals.Identifier;
import terminals.Literal;
import terminals.QuantifiedExpr;

import java.util.*;

/**
 * Created by joe on 12/11/16.
 */
public class Matcher {

    //node is the subExpr, the users selection
    //attempting to match it in that rule
    public Set<Match> match(INode node, INode rule) {

        Set<Match> validMatches = new LazySet<>();

        if (node instanceof ITerminal) {
            Set<MatchAndTransition> matchAndTransitions = null;

            //if node instance of Literal (true or false), need to find instances of that literal.
            if (node instanceof Literal) {
                String literal = node.getNodeChar();
                matchAndTransitions = ((new TreePermuter().literalNodesWithJoinerAsParent(rule, literal)));
            }

            //if node is an ID, then valid matches are instances of a single identifier node with equiv OR IMPL OR FF as a parent,
            // i'll get them all, they will be millions, eugh
            if (node instanceof Identifier) {
                matchAndTransitions = ((new TreePermuter()).idNodesWithJoinerAsParent(rule));
            }


            if (node instanceof QuantifiedExpr) {
                Set<MatchAndTransition> potentials = ((new TreePermuter()).quantNodesWithJoinerAsParent(rule));
                matchAndTransitions = new LazySet<>();
                for (MatchAndTransition mat : potentials) {
                    if (checkArePotentialMatchingQuantsActualMatches(node, mat)) matchAndTransitions.add(mat);
                }
            }

            if (matchAndTransitions != null) {
                for (MatchAndTransition matchAndTransition : matchAndTransitions) {
                    HashMap<String, INode> lookupTable = new HashMap<>();
                    lookupTable.put(matchAndTransition.getMatch().getNodeChar(), node);
                    validMatches.add(new Match(matchAndTransition.getMatch().getRoot(), matchAndTransition.getMatch(), lookupTable, matchAndTransition.getTransition()));
                }
            }

            return validMatches;
        }

        //need to find every subexpression of the rule with equival OR IMPL OR FF as parent and matching nodeChar at rootOfMatchedNode.

        Set<MatchAndTransition> potentialMatchesAndTransitions = (new TreePermuter()).nodesWithJoinersAsParentAndMatchingOp(rule, node.getNodeChar());
        //for each of the potential matches, need to walk and see if it matches and build a lookup table
        for (MatchAndTransition potentialMatchAndTransition : potentialMatchesAndTransitions) {
            HashMap<String, INode> lookUpTable = walkToMatch(potentialMatchAndTransition.getMatch(), node, new HashMap<>());

            if (lookUpTable != null) {
                validMatches.add(new Match(potentialMatchAndTransition.getMatch().getRoot(), potentialMatchAndTransition.getMatch(), lookUpTable, potentialMatchAndTransition.getTransition()));
            }
        }

        return validMatches;

    }

    private boolean checkArePotentialMatchingQuantsActualMatches(INode node, MatchAndTransition matchInRule) {

        QuantifiedExpr rule = (QuantifiedExpr) matchInRule.getMatch();
        QuantifiedExpr selection = (QuantifiedExpr) node;
        //if node matches the rule, add it to the set of matches and transitions


        //what needs to match
        //quantifier
        if (!(rule.getOp().equals(selection.getOp()))) return false;

        //list of dummies
        if (rule.getDummys().size() != selection.getDummys().size()) return false;

        //range tree && term tree

        return checkQuantTree(selection.getRange(), rule.getRange(), new HashMap<>(), new HashMap<>()) &&
                checkQuantTree(selection.getTerm(), rule.getTerm(), new HashMap<>(), new HashMap<>());


    }

    private boolean checkQuantTree(INode selectionTree, INode ruleTree, HashMap<String, String> idMap, HashMap<ArrayAndIndex, ArrayAndIndex> ariMap) {
        if (selectionTree instanceof Identifier && ruleTree instanceof Identifier) {
            if (idMap.containsKey(selectionTree.getNodeChar())) {
                return ruleTree.getNodeChar().equals(idMap.get(selectionTree.getNodeChar()));
            } else {
                idMap.put(selectionTree.getNodeChar(), ruleTree.getNodeChar());
                return true;
            }
        }
        if (selectionTree instanceof Literal && ruleTree instanceof Literal) {
            return selectionTree.getNodeChar().equals(ruleTree.getNodeChar());
        }
        if (selectionTree instanceof ArrayAndIndex && ruleTree instanceof ArrayAndIndex) {
            if (ariMap.containsKey(selectionTree)) {
                return ruleTree.equals(ariMap.get(selectionTree));
            } else {
                ariMap.put((ArrayAndIndex) selectionTree, (ArrayAndIndex) ruleTree);
                return true;
            }
        }


        //now we know it's an operator node
        if (!(selectionTree.getNodeChar().equals(ruleTree.getNodeChar()))) return false;
        if (selectionTree.children().length != ruleTree.children().length) return false;

        boolean leftChild = checkQuantTree(selectionTree.children()[0], ruleTree.children()[0], idMap, ariMap);
        if (selectionTree.children().length > 1) {
            boolean rightChild = checkQuantTree(selectionTree.children()[1], ruleTree.children()[1], idMap, ariMap);
            return leftChild && rightChild;
        } else {
            return leftChild;
        }

    }

    //node is the user selection
    //rule subexpression is a bit of the rule that might match the user selection
    private HashMap<String, INode> walkToMatch(INode ruleSubexpr, INode node, HashMap<String, INode> lookUpTable) {
        if (ruleSubexpr instanceof ITerminal) {
            //need to be care full here

            //need extra check to see if node is a literal, then the literal has to match that of the node
            if (ruleSubexpr instanceof Literal) {
                if (!(node instanceof Literal && node.getNodeChar().equals(ruleSubexpr.getNodeChar()))) {
                    return null;
                }
            }

            boolean winning = true;
            if (node instanceof QuantifiedExpr) {
                if (ruleSubexpr instanceof QuantifiedExpr) {
                    //TODO this better

                    winning = checkArePotentialMatchingQuantsActualMatches(node, new MatchAndTransition(ruleSubexpr, ""));

                    //if the quants are the same, then just pop A maps to B in the lookup table,
                } else {
                    return null;
                }
            }


            //if lookuptable has the key (ID) already, rootOfMatchedNode must match its value, else, return null.
            if (!(node instanceof QuantifiedExpr) || winning) {
                if (lookUpTable.containsKey(ruleSubexpr.getNodeChar())) {
                    //check
                    if (lookUpTable.get(ruleSubexpr.getNodeChar()).equals(node)) {
                        //it's all G
                        return lookUpTable;
                    } else {
                        return null;
                    }
                } else {//it does not have the key in it,,, so add it, also all G
                    lookUpTable.put(ruleSubexpr.getNodeChar(), node);
                    return lookUpTable;
                }
            }
        }
        //at this point ruleSubexpr IS NOT an ID,
        if (ruleSubexpr.getNodeChar() != node.getNodeChar()) return null;


        if (ruleSubexpr.children().length == node.children().length) {
            HashMap<String, INode> foo = walkToMatch(ruleSubexpr.children()[0], node.children()[0], lookUpTable);
            HashMap<String, INode> bar = null;

            if (ruleSubexpr.children().length > 1 && node.children().length > 1) {
                bar = walkToMatch(ruleSubexpr.children()[1], node.children()[1], lookUpTable);
            }

            if (foo == null || (ruleSubexpr.children().length > 1 && bar == null)) {
                return null;
            } else {
                return lookUpTable;
            }
        } else {
            return null;
        }
    }

    public class Match {

        private INode rootOfExpr;
        private INode rootOfMatchedNode;
        private HashMap<String, INode> loopUpTable;
        private String transition;

        public Match(INode rootOfExpr, INode rootOfMatchedNode, HashMap<String, INode> loopUpTable, String transition) {
            this.rootOfExpr = rootOfExpr;
            this.rootOfMatchedNode = rootOfMatchedNode;
            this.loopUpTable = loopUpTable;
            this.transition = transition;
        }

        public INode getRootOfMatchedNode() {
            return rootOfMatchedNode;
        }

        public HashMap<String, INode> getLoopUpTable() {
            return loopUpTable;
        }

        public INode getRootOfExpr() {
            return rootOfExpr;
        }

        public String getTransition() {
            return transition;
        }

        @Override
        public String toString() {
            return "Match{" +
                    "rootOfExpr=" + rootOfExpr +
                    ", rootOfMatchedNode=" + rootOfMatchedNode +
                    ", loopUpTable=" + loopUpTable +
                    ", transition=" + transition +
                    '}';
        }
    }

}
