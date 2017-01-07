package gui.core;

import interfaces.INode;
import interfaces.ITerminal;
import terminals.Identifier;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import terminals.QuantifiedExpr;

import java.util.List;
import java.util.Vector;
import java.util.function.IntToDoubleFunction;

public class Bit extends StackPane {

    private Text text;
    private List<INode> nodesInTree;
    private int pointer;
    private Bit bracketBuddy;


    public Bit(Text text) {
        super(text);
        this.setBackground(Colors.white);
        this.text = text;
        this.nodesInTree = new Vector<>();
        pointer = 0;
    }


    public void setRed() {
        this.setBackground(Colors.red);
        if (hasBracketBuddy() && !bracketBuddy.isRed()) bracketBuddy.setRed();
    }


    public void setGreen() {
        this.setBackground(Colors.green);
        if (hasBracketBuddy() && !bracketBuddy.isGreen()) bracketBuddy.setGreen();
    }


    public void setWhite() {
        this.setBackground(Colors.white);
        if (hasBracketBuddy() && !bracketBuddy.isWhite()) bracketBuddy.setWhite();
    }

    public boolean isWhite() {
        return this.getBackground() == Colors.white;
    }

    public boolean isRed() {
        return this.getBackground() == Colors.red;
    }

    public boolean isGreen() {
        return this.getBackground() == Colors.green;
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public List<INode> getNodesInTree() {
        return nodesInTree;
    }

    public void setNodesInTree(List<INode> nodesInTree) {
        this.nodesInTree = nodesInTree;
    }


    public INode cycleAndGetSelection() {

        //handle clicking on bracket buddy with no nodes
        if (hasBracketBuddy() && nodesInTree.isEmpty()) return bracketBuddy.cycleAndGetSelection();

        INode n = nodesInTree.get(pointer);


        //we have a tree, so need to walk and hightligt the bits
        walkAndHighlight(n);


        pointer++;
        if (pointer >= nodesInTree.size()) pointer = 0;
        return n;
    }

    private void walkAndHighlight(INode node) {
        node.getBit().setGreen();

        if (node instanceof QuantifiedExpr) {
            walkAndHighlight(((QuantifiedExpr) node).getRange());
            walkAndHighlight(((QuantifiedExpr) node).getTerm());
        }

        if (node instanceof ITerminal) {
            return;
        }

        walkAndHighlight(node.children()[0]);
        if (node.children().length > 1) walkAndHighlight(node.children()[1]);
    }


    public void setBracketBuddy(Bit bracketBuddy) {
        this.bracketBuddy = bracketBuddy;
    }

    public Bit getBracketBuddy() {
        return bracketBuddy;
    }

    public boolean hasBracketBuddy() {
        return bracketBuddy != null;
    }

    @Override
    public String toString() {
        return getText();
    }


    /*public INode getCurrSubExpression() {
        return nodesInTree.isEmpty()?  bracketBuddy.getCurrSubExpression() : nodesInTree.get(pointer);
    }*/
}
