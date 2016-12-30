package gui.workers;

import gui.core.Bit;
import interfaces.INode;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import nodes.NodeForBrackets;
import nodes.UnaryOperator;
import terminals.Identifier;

/**
 * Created by joe on 17/12/16.
 */
public class BitBoxMaker {


    //TODO this is crap
    public HBox getBitBox(INode node) {


        //need to walk expression, AND populare the bits accordingly, BUT what about brackets? whill the sort them selves?
        return walkAndFill(node, new HBox());
    }

    public HBox walkAndFill(INode node, HBox box) {
        if (node instanceof Identifier) {
            box.getChildren().add(new Bit(new Text(node.getNodeChar()+"")));
            return box;
        }


        Bit open = null;
        Bit close = null;
        if (node instanceof NodeForBrackets) {
            open = new Bit(new Text("("));
            close = new Bit(new Text(")"));
            open.setBracketBuddy(close);
            close.setBracketBuddy(open);
        }

        if (node instanceof UnaryOperator) box.getChildren().add(new Bit(new Text(node.getNodeChar()+"")));
        if (open != null) box.getChildren().add(open);
        walkAndFill(node.children()[0], box);
        if (open == null && close == null && !(node instanceof UnaryOperator)) box.getChildren().add(new Bit(new Text(node.getNodeChar()+"")));
        if (node.children().length>1) walkAndFill(node.children()[1],box);
        if (close != null) box.getChildren().add(close);
        return box;
    }

    public ObservableList<Node> getBits(INode root) {
        return (getBitBox(root)).getChildren();
    }
}
