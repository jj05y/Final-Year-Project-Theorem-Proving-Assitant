package terminals;

import constants.Operators;
import interfaces.INode;
import interfaces.ITerminal;
import nodes.Node;
import nodes.UnaryOperator;

import java.util.List;
import java.util.Vector;

/**
 * Created by joe on 02/01/17.
 */

public class QuantifiedExpr extends Node implements INode, ITerminal {

    private char op;
    private List<Character> dummys;
    private INode range;
    private INode term;

    public QuantifiedExpr(char op, List<Character> dummys, INode range, INode term) {
        this.op = op;
        this.dummys = dummys;
        this.range = range;
        this.term = term;
    }

    public char getOp() {
        return op;
    }

    public List<Character> getDummys() {
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
        INode copyOfThis = new QuantifiedExpr(op, new Vector<>(dummys), range.copyWholeTree(), term.copyWholeTree());
        return copyOfThis;
    }

    @Override
    public String toString() {
        String dummyString = "";
        for (Character c : dummys) dummyString += c +",";
        dummyString = dummyString.substring(0, dummyString.length()-1);

        String string = Operators.LANGLE + " " + op + " " + dummyString + " : " + range + " : " + term + " " + Operators.RANGLE;
        return string;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QuantifiedExpr) {
            QuantifiedExpr other = (QuantifiedExpr) obj;
            return other.getOp() == this.op &&
                    other.getTerm().equals(this.term) &&
                    other.getRange().equals(this.range) &&
                    other.getDummys().equals(this.dummys);
        } else {
            return false;
        }
    }


}

