package core;

import interfaces.INode;
import workers.Matcher;

/**
 * Created by joe on 31/12/16.
 */
public class MatchAndTransition {

    private INode match;
    private char transition;

    public MatchAndTransition(INode match, char transition) {
        this.match = match;
        this.transition = transition;
    }

    public INode getMatch() {
        return match;
    }

    public char getTransition() {
        return transition;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MatchAndTransition) {
            MatchAndTransition other = (MatchAndTransition) obj;
            return match.equals(other.getMatch()) && transition == other.getTransition();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "MatchAndTransition{" +
                "match=" + match +
                ", transition=" + transition +
                '}';
    }
}
