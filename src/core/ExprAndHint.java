package core;

import interfaces.INode;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by joe on 20/12/16.
 */
public class ExprAndHint {
    HashMap<Character, INode> lookupTable;
    INode expression;

    public ExprAndHint(HashMap<Character, INode> lookupTable, INode expression) {
        this.lookupTable = lookupTable;
        this.expression = expression;
    }

    public HashMap<Character, INode> getLookupTable() {
        return lookupTable;
    }

    public void setLookupTable(HashMap<Character, INode> lookupTable) {
        this.lookupTable = lookupTable;
    }

    public INode getExpression() {
        return expression;
    }

    public void setExpression(INode expression) {
        this.expression = expression;
    }

    public String getHint(int index) {
        //TODO this
        StringBuilder sb = new StringBuilder("= {(");
        Set<Character> keys = lookupTable.keySet();
        for (Character c : keys) {
            sb.append(c+",");
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
        return "ExprAndHint{" +
                "expression=" + expression +
                ", lookupTable=" + lookupTable +
                '}';
    }
}
