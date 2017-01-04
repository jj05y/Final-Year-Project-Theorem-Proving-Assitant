package workers;

import core.LazySet;
import core.MatchAndTransition;
import interfaces.INode;
import interfaces.ITerminal;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import terminals.Literal;
import terminals.QuantifiedExpr;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by joe on 12/11/16.
 */
public class Matcher {

    public Set<Match> match(INode node, INode rule) {

        Set<Match> validMatches = new LazySet<>();

        if (node instanceof ITerminal) {
            Set<MatchAndTransition> matchAndTransitions = null;

            //if node instance of Literal (true or false), need to find instances of that literal.
            if (node instanceof Literal) {
                char literal = node.getNodeChar();
                matchAndTransitions = ((new TreePermuter().literalNodesWithJoinerAsParent(rule, literal)));
            }

            //if node is an ID, then valid matches are instances of a single identifier node with equiv OR IMPL OR FF as a parent,
            // i'll get them all, they will be millions, eugh
            if (node instanceof Identifier) {
                matchAndTransitions = ((new TreePermuter()).idNodesWithJoinerAsParent(rule));
            }


            if (node instanceof QuantifiedExpr) {
                matchAndTransitions = ((new TreePermuter()).quantNodesWithJoinerAsParent(rule));

                //we can get a list of all quants, and then vet them?
                //yeah :(
                QuantifiedExpr goodOne = (QuantifiedExpr) node;
                List<MatchAndTransition> toRemove = new Vector<>();
                for (MatchAndTransition matchAndTransition : matchAndTransitions) {
                    System.out.println("Potential MAT: " + matchAndTransition);
                    QuantifiedExpr maybeOne = (QuantifiedExpr) matchAndTransition.getMatch();

                    //how do we vet them? we need to check if the two trees, range and term match, as well as dummies and quantifier
                    boolean winning = true;
                    if (!(goodOne.getDummys().equals(maybeOne.getDummys()))) winning = false;
                    if (!(goodOne.getRange().equals(maybeOne.getRange()))) winning = false;
                    if (!(goodOne.getTerm().equals(maybeOne.getTerm()))) winning = false;
                    if (!(goodOne.getOp() == maybeOne.getOp())) winning = false;
                    if (!winning) toRemove.add(matchAndTransition);
                }
                matchAndTransitions.removeAll(toRemove);
                for (MatchAndTransition m : matchAndTransitions) {
                    System.out.println("Actual MAT " + m);
                }

            }

            if (matchAndTransitions != null) {
                for (MatchAndTransition matchAndTransition : matchAndTransitions) {
                    HashMap<Character, INode> lookupTable = new HashMap<>();
                    lookupTable.put(matchAndTransition.getMatch().getNodeChar(), node);
                    validMatches.add(new Match(matchAndTransition.getMatch().getRoot(), matchAndTransition.getMatch(), lookupTable, matchAndTransition.getTransition()));
                }
            }

            return validMatches;
        }

        //need to find every subexpression of the rule with equival OR IMPL OR FF as parent and matching nodeChar at rootOfMatchedNode.

        Set<MatchAndTransition> potentialMatchesAndTransitions = (new TreePermuter()).nodesWithJoinersAsParentAndMatchingOp(rule, node.getNodeChar());
        System.out.println(potentialMatchesAndTransitions);
        //for each of the potential matches, need to walk and see if it matches and build a lookup table
        for (MatchAndTransition potentialMatchAndTransition : potentialMatchesAndTransitions) {

            HashMap<Character, INode> lookUpTable = walkToMatch(potentialMatchAndTransition.getMatch(), node, new HashMap<>());

            if (lookUpTable != null) {
                validMatches.add(new Match(potentialMatchAndTransition.getMatch().getRoot(), potentialMatchAndTransition.getMatch(), lookUpTable, potentialMatchAndTransition.getTransition()));
            }
        }

        return validMatches;

    }

    //node is the user selection
    //rule subexpression is a bit of the rule that might match the user selection
    private HashMap<Character, INode> walkToMatch(INode ruleSubexpr, INode node, HashMap<Character, INode> lookUpTable) {
        if (ruleSubexpr instanceof ITerminal) {
            //need to be care full here

            //need extra check to see if node is a literal, then the literal has to match that of the node
            if (ruleSubexpr instanceof Literal) {
                if (!(node instanceof Literal && node.getNodeChar() == ruleSubexpr.getNodeChar())) {
                    return null;
                }
            }

            boolean winning = true;
            if (node instanceof QuantifiedExpr) {
                if (ruleSubexpr instanceof QuantifiedExpr) {
                    System.out.println("wining");
                    QuantifiedExpr goodOne = (QuantifiedExpr) node;
                    QuantifiedExpr maybeOne = (QuantifiedExpr) ruleSubexpr;
                    if (!(goodOne.getDummys().equals(maybeOne.getDummys()))) winning = false;
                    if (!(goodOne.getRange().equals(maybeOne.getRange()))) winning = false;
                    if (!(goodOne.getTerm().equals(maybeOne.getTerm()))) winning = false;
                    if (!(goodOne.getOp() == maybeOne.getOp())) winning = false;
                    System.out.println(winning);
                } else {
                    return null;
                }
            }


            //if lookuptable has the key (ID) already, rootOfMatchedNode must match its value, else, return null.
            if (!(node instanceof QuantifiedExpr) || winning) {
                if (lookUpTable.containsKey(ruleSubexpr.getNodeChar())) {
                    //check
                    if (lookUpTable.get(ruleSubexpr.getNodeChar()).equals(node)) {
                        System.out.println("huh1");
                        //it's all G
                        return lookUpTable;
                    } else {
                        System.out.println("huh2");
                        return null;
                    }
                } else {//it does not have the key in it,,, so add it, also all G
                    System.out.println("huh3");
                    lookUpTable.put(ruleSubexpr.getNodeChar(), node);
                    return lookUpTable;
                }
            }
        }
        //at this point ruleSubexpr IS NOT an ID,
        if (ruleSubexpr.getNodeChar() != node.getNodeChar()) return null;


        if (ruleSubexpr.children().length == node.children().length) {
            HashMap<Character, INode> foo = walkToMatch(ruleSubexpr.children()[0], node.children()[0], lookUpTable);
            HashMap<Character, INode> bar = null;

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
        private HashMap<Character, INode> loopUpTable;
        private char transition;

        public Match(INode rootOfExpr, INode rootOfMatchedNode, HashMap<Character, INode> loopUpTable, char transition) {
            this.rootOfExpr = rootOfExpr;
            this.rootOfMatchedNode = rootOfMatchedNode;
            this.loopUpTable = loopUpTable;
            this.transition = transition;
        }

        public INode getRootOfMatchedNode() {
            return rootOfMatchedNode;
        }

        public HashMap<Character, INode> getLoopUpTable() {
            return loopUpTable;
        }

        public INode getRootOfExpr() {
            return rootOfExpr;
        }

        public char getTransition() {
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
