package gui.workers;

import gui.core.Bit;
import interfaces.INode;
import javafx.scene.Node;
import nodes.NodeForBrackets;
import terminals.Identifier;

import java.util.Iterator;

/**
 * Created by joe on 17/12/16.
 */
public class Associator {

    public void walkAndAssociateBitsAndNodes(INode node, Iterator<Node> iterator) {


        //a bit is a char in the GUI
        if (node instanceof Identifier) { //a leaf
            Bit b;
            while ((b = (Bit) iterator.next()).getText().matches("[()]"));
            if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
            node.setBit(b);
            return;
        }

        if (node instanceof NodeForBrackets) {
            node=node.children()[0];
        }

        walkAndAssociateBitsAndNodes(node.children()[0], iterator);
        Bit b;
        while ((b = (Bit) iterator.next()).getText().matches("[()]"));
        if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
        node.setBit(b);
        if (node.children().length>1) walkAndAssociateBitsAndNodes(node.children()[1], iterator);
    }
}
