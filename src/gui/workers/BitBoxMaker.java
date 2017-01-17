package gui.workers;

import constants.Operators;
import gui.core.Bit;
import interfaces.INode;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import nodes.NodeForBrackets;
import nodes.UnaryOperator;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import terminals.Literal;
import terminals.QuantifiedExpr;

/**
 * Created by joe on 17/12/16.
 */
public class BitBoxMaker {


    public HBox getBitBox(INode node) {


        //need to walk expression, AND populare the bits accordingly, BUT what about brackets? whill the sort them selves?
        return walkAndFill(node, new HBox());
    }

    public HBox walkAndFill(INode node, HBox box) {
        if (node instanceof Identifier) {
            box.getChildren().add(new Bit(new Text(node.getNodeChar())));
            return box;
        }

        if (node instanceof Literal) {
            box.getChildren().add(new Bit(new Text(node.getNodeChar())));
            return box;
        }

        if (node instanceof ArrayAndIndex) {
            box.getChildren().add(new Bit(new Text(node.getNodeChar())));
            return box;
        }

        if (node instanceof QuantifiedExpr) {
            dealWithQuant(node, box);
            return box;
        }


        Bit open = null;
        Bit close = null;
        if (node instanceof NodeForBrackets) {
            open = new Bit(new Text(node.getNodeChar()));
            close = new Bit(new Text(Operators.bracketPair.get(node.getNodeChar())));
            open.setBracketBuddy(close);
            close.setBracketBuddy(open);
        }

        if (node instanceof UnaryOperator) box.getChildren().add(new Bit(new Text(node.getNodeChar())));
        if (open != null) box.getChildren().add(open);
        walkAndFill(node.children()[0], box);
        if (open == null && close == null && !(node instanceof UnaryOperator))
            box.getChildren().add(new Bit(new Text(node.getNodeChar() + "")));
        if (node.children().length > 1) walkAndFill(node.children()[1], box);
        if (close != null) box.getChildren().add(close);
        return box;
    }

    private void dealWithQuant(INode node, HBox box) {
        QuantifiedExpr quantifiedExpr = (QuantifiedExpr) node;
        Bit langle = new Bit(new Text(Operators.LANGLE + quantifiedExpr.getQuantifier() + quantifiedExpr.getDummyString() + " : "));
        Bit middleColon = new Bit(new Text(" : "));
        Bit rangle = new Bit(new Text(Operators.RANGLE));

        langle.setBracketBuddy(middleColon);
        middleColon.setBracketBuddy(rangle);
        rangle.setBracketBuddy(langle);

        box.getChildren().add(langle);
        walkAndFill(quantifiedExpr.getRange(), box);
        box.getChildren().add(middleColon);
        walkAndFill(quantifiedExpr.getTerm(), box);
        box.getChildren().add(rangle);
    }

    public ObservableList<Node> getBits(INode root) {
        return (getBitBox(root)).getChildren();
    }
}
