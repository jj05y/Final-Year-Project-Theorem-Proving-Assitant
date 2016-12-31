package workers;

import core.LazySet;
import core.MatchAndTransition;
import interfaces.INode;
import interfaces.ITerminal;
import terminals.Identifier;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by joe on 12/11/16.
 */
public class Matcher {

    public Set<Match> match(INode node, INode rule) {

        Set<Match> validMatches = new LazySet<>();

        //TODO if node instance of Literal (true or false), need to find instances of that litteral.

        //if node is an ID, then valid matches are instances of a single identifier node with equiv OR IMPL OR FF as a parent,
        // i'll get them all, they will be millions, eugh
        if (node instanceof Identifier) {
            Set<MatchAndTransition> matchAndTransitions = ((new TreePermuter()).idNodesWithJoinerAsParent(rule));
            for (MatchAndTransition matchAndTransition : matchAndTransitions) {

                HashMap<Character, INode> lookupTable = new HashMap<>();
                lookupTable.put(matchAndTransition.getMatch().getNodeChar(), node);
                validMatches.add(new Match(matchAndTransition.getMatch().getRoot(),matchAndTransition.getMatch(),lookupTable,matchAndTransition.getTransition()));
            }

            return validMatches;
        }

        //need to find every subexpression of the rule with equival OR IMPL OR FF as parent and matching nodeChar at rootOfMatchedNode.

        Set<MatchAndTransition> potentialMatchesAndTransitions = (new TreePermuter()).nodesWithJoinersAsParentAndMatchingOp(rule, node.getNodeChar());
        //for each of the potential matches, need to walk and see if it matches and build a lookup table
        for (MatchAndTransition potentialMatchAndTransition : potentialMatchesAndTransitions) {

            HashMap<Character, INode> lookUpTable = walkToMatch(potentialMatchAndTransition.getMatch(), node, new HashMap<>());

            if (lookUpTable != null) {
                validMatches.add(new Match(potentialMatchAndTransition.getMatch().getRoot(), potentialMatchAndTransition.getMatch(), lookUpTable,potentialMatchAndTransition.getTransition()));
            }
        }

        return validMatches;

    }

    private HashMap<Character, INode> walkToMatch(INode ruleSubexpr, INode node, HashMap<Character, INode> lookUpTable) {

        if (ruleSubexpr instanceof ITerminal) {
            //need to be care full here

            //if lookuptable has the key (ID) already, rootOfMatchedNode must match its value, else, return null.
            if (lookUpTable.containsKey(ruleSubexpr.getNodeChar())) {
                //check
                if (lookUpTable.get(ruleSubexpr.getNodeChar()).equals(node)){
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
        //at this point ruleSubexpr IS NOT an ID, so it has to be an nodeChar (or brackets)
        if (ruleSubexpr.getNodeChar() != node.getNodeChar()) return null;

        if (ruleSubexpr.children().length == node.children().length) {
            HashMap<Character, INode> foo = walkToMatch(ruleSubexpr.children()[0], node.children()[0], lookUpTable);
            HashMap<Character, INode> bar = null;

            if (ruleSubexpr.children().length > 1 && node.children().length > 1) {
                bar = walkToMatch(ruleSubexpr.children()[1], node.children()[1], lookUpTable);
            }

            if (foo == null ||(ruleSubexpr.children().length >1 && bar == null)) {
                return null;
            } else {
                return lookUpTable;
            }
        } else {
            return null;
        }
    }

    public class Match{

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
