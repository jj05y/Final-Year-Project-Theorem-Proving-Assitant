package beans;

import constants.Operators;
import interfaces.INode;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by joe on 20/12/16.
 */
public class ExprAndHintandTransition {
    HashMap<Character, INode> lookupTable;
    INode expression;
    char transition;

    public ExprAndHintandTransition(HashMap<Character, INode> lookupTable, INode expression, char transition) {
        this.lookupTable = lookupTable;
        this.expression = expression;
        this.transition = transition;
    }

    public HashMap<Character, INode> getLookupTable() {
        return lookupTable;
    }

    public INode getExpression() {
        return expression;
    }

    public void setExpression(INode expression) {
        this.expression = expression;
    }

    public char getTransition() {
        return transition;
    }

    public String getHint(int index) {
        StringBuilder sb = new StringBuilder(transition + " {(");
        Set<Character> keys = lookupTable.keySet();
        for (Character c : keys) {
            if (c == Operators.TRUE) {
                sb.append("true,");
            } else if (c == Operators.FALSE) {
                sb.append("false,");
            } else {
                sb.append(c + ",");
            }
        }
        sb.replace(sb.length()-1,sb.length()," := ");
        for (Character c : keys) {
            sb.append(lookupTable.get(c) + ",");
        }
        sb.replace(sb.length()-1,sb.length(),").("+index+")}");


        return sb.toString();
    }

    @Override
    public String toString() {
        return "ExprAndHintandTransition{" +
                "lookupTable=" + lookupTable +
                ", expression=" + expression +
                ", transition=" + transition +
                '}';
    }
}
