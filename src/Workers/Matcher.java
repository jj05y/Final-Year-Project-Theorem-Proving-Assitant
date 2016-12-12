package Workers;

import Core.LazySet;
import Core.TreeAndSubTree;
import Interfaces.INode;
import Terminals.Identifier;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by joe on 12/11/16.
 */
public class Matcher {

    public Set<Match> match(INode node, INode rule) {

        Set<Match> validMatches = new LazySet<>();

        //need to find every subexpression of the rule with equival as parent and matching nodeChar at rootOfMatchedNode.

        Set<INode> potentialMatches = (new TreePermuter()).nodesWithEquivAsParentAndMatchingOp(rule, node.getNodeChar());

        //for each of the potential matches, need to walk and see if it matches and build a lookup table
        for (INode potentialMatch : potentialMatches) {

            HashMap<Character, INode> lookUpTable = walkToMatch(potentialMatch, potentialMatch, new HashMap<>());

            if (lookUpTable != null) {
                validMatches.add(new Match(potentialMatch.getRoot(), potentialMatch, lookUpTable));
            }
        }

        return validMatches;

    }

    private HashMap<Character, INode> walkToMatch(INode ruleSubexpr, INode node, HashMap<Character, INode> lookUpTable) {

        if (ruleSubexpr instanceof Identifier) {
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

        public Match(INode rootOfExpr, INode rootOfMatchedNode, HashMap<Character, INode> loopUpTable) {
            this.rootOfExpr = rootOfExpr;
            this.rootOfMatchedNode = rootOfMatchedNode;
            this.loopUpTable = loopUpTable;
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

        @Override
        public String toString() {
            return "Match{" +
                    "rootOfExpr=" + rootOfExpr +
                    ", rootOfMatchedNode=" + rootOfMatchedNode +
                    ", loopUpTable=" + loopUpTable +
                    '}';
        }
    }

}
