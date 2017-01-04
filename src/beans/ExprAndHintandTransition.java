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
    HashMap<String, INode> lookupTable;
    INode expression;
    String transition;

    public ExprAndHintandTransition(HashMap<String, INode> lookupTable, INode expression, String transition) {
        this.lookupTable = lookupTable;
        this.expression = expression;
        this.transition = transition;
    }

    public HashMap<String, INode> getLookupTable() {
        return lookupTable;
    }

    public INode getExpression() {
        return expression;
    }

    public void setExpression(INode expression) {
        this.expression = expression;
    }

    public String getTransition() {
        return transition;
    }

    public String getHint(int index) {
        StringBuilder sb = new StringBuilder(transition + " {(");
        Set<String> keys = lookupTable.keySet();
        for (String s : keys) {
            if (s.equals(Operators.TRUE)) {
                sb.append("true,");
            } else if (s.equals(Operators.FALSE)) {
                sb.append("false,");
            } else {
                sb.append(s + ",");
            }
        }
        sb.replace(sb.length()-1,sb.length()," := ");
        for (String s : keys) {
            sb.append(lookupTable.get(s) + ",");
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
