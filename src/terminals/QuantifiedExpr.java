package terminals;

import constants.Operators;
import interfaces.INode;
import interfaces.ITerminal;
import nodes.Node;

import java.util.*;

/**
 * Created by joe on 02/01/17.
 */

public class QuantifiedExpr extends Node implements INode, ITerminal {

    private String op;
    private Set<String> dummys;
    private INode range;
    private INode term;

    public QuantifiedExpr(String op, Set<String> dummys, INode range, INode term) {
        this.op = op;
        this.dummys = dummys;
        this.range = range;
        this.term = term;
        this.nodeChar = this.toString();
    }

    public String getOp() {
        return op;
    }

    public Set<String> getDummys() {
        return dummys;
    }

    public INode getRange() {
        return range;
    }

    public INode getTerm() {
        return term;
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis = new QuantifiedExpr(op, new HashSet<>(dummys), range.copySubTree(), term.copySubTree());
        return copyOfThis;
    }

    @Override
    public void tellChildAboutParent() {
        range.setParent(this);
        range.tellChildAboutParent();
        term.setParent(this);
        term.tellChildAboutParent();
    }

    @Override
    public String toString() {
        String dummyString = "";
        for (String c : dummys) dummyString += c +",";
        dummyString = dummyString.substring(0, dummyString.length()-1);

        String string = Operators.LANGLE + " " + op + " " + dummyString + " : " + range + " : " + term + " " + Operators.RANGLE;
        return string;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QuantifiedExpr) {
            QuantifiedExpr other = (QuantifiedExpr) obj;
            return other.getOp().equals(this.op) &&
                    other.getTerm().equals(this.term) &&
                    other.getRange().equals(this.range) &&
                    other.getDummys().equals(this.dummys);
        } else {
            return false;
        }
    }


    public String getDummyString() {
        String str = "";
        for (String s : dummys) {
            str += "," + s;
        }
        return str.substring(1);
    }

    public void setDummys(Set<String> dummys) {
        this.dummys = dummys;
    }

    public void setTerm(INode term) {
        this.term = term;
    }

    public void setRange(INode range) {
        this.range = range;
    }
}

