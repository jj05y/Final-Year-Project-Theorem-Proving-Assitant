package workers;

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

                for (MatchAndTransition mat : potentials) {
                    if (matchAndTransitions == null) matchAndTransitions = new LazySet<>();
                    matchAndTransitions.addAll(checkArePotentialMatchingQuantsActualMatches(node, mat));
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

    private Set<MatchAndTransition> checkArePotentialMatchingQuantsActualMatches(INode node, MatchAndTransition matchInRule) {
        Set<MatchAndTransition> matchAndTransitions = new LazySet<>();
        INode rule = matchInRule.getMatch();
        //if node matches the rule, add it to the set of matches and transitions

        //what needs to match
        //quantifier
        //list of dummies
        //range tree
        //term tree



        return matchAndTransitions;

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

                    //if the quants are the same, then just pop A maps to B in the lookup table,
                   // Set<MatchAndTransition> sss = checkArePotentialMatchingQuantsActualMatches(node,ruleSubexpr);
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
