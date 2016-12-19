package gui.workers;

import gui.core.Bit;
import interfaces.INode;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import nodes.NodeForBrackets;
import terminals.Identifier;

import java.util.Stack;

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

        if (node instanceof NodeForBrackets) {
            node = node.children()[0]; //skip (no use)
        }


        walkAndFill(node.children()[0], box);
        box.getChildren().add(new Bit(new Text(node.getNodeChar()+"")));
        if (node.children().length>1) walkAndFill(node.children()[1],box);
        return box;
    }

    public ObservableList<Node> getBits(INode root) {
        return (getBitBox(root)).getChildren();
    }
}
