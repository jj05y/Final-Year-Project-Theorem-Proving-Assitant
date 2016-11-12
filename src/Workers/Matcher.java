package Workers;

import Interfaces.INode;
import Terminals.Identifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by joe on 12/11/16.
 */
public class Matcher {

    public Set<Match> match(INode node, INode rule) {

        Set<Match> validMatches = new HashSet<>();

        //need to find every subexpression of the rule with equival as parent and matching operator at node.

        Set<INode> potentialMatches = (new TreePermuter()).nodesWithEquivAsParentAndMatchingOp(rule, node.getChar());

        //for each of the potential matches, need to walk and see if it matches and build a lookup table
        for (INode n : potentialMatches) {
            HashMap<Character, INode> potential = walkToMatch(n, node, new HashMap<Character, INode>());
            if (potential != null) {
                validMatches.add(new Match(n, potential));
            }
        }

        return validMatches;

    }

    private HashMap<Character, INode> walkToMatch(INode ruleSubexpr, INode node, HashMap<Character, INode> lookUpTable) {

        if (ruleSubexpr instanceof Identifier) {
            lookUpTable.put(ruleSubexpr.getChar(), node);
            return lookUpTable;
        }
        //at this point ruleSubexpr IS NOT an ID, so it has to be an operator (or brackets)
        if (ruleSubexpr.getChar() != node.getChar()) return null;

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

        private INode node;
        private HashMap<Character, INode> loopUpTable;

        public Match(INode node, HashMap<Character, INode> loopUpTable) {
            this.node = node;
            this.loopUpTable = loopUpTable;
        }

        public INode getNode() {
            return node;
        }

        public HashMap<Character, INode> getLoopUpTable() {
            return loopUpTable;
        }

        @Override
        public String toString() {
            return "Match{" +
                    "node=" + node +
                    ", loopUpTable=" + loopUpTable +
                    '}';
        }
    }

}
