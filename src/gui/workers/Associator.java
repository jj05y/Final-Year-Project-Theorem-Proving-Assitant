package gui.workers;

import gui.core.Bit;
import interfaces.INode;
import javafx.scene.Node;
import nodes.NodeForBrackets;
import terminals.Identifier;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by joe on 17/12/16.
 */
public class Associator {

    private Stack<INode> nodes;
    private Stack<Bit> bits;


    public void associate(INode node, Iterator<Node> iterator) {
        this.nodes = new Stack<>();
        this.bits = new Stack<>();
        walkAndAssociateBitsAndNodes(node, iterator);
        System.out.println(nodes);
        System.out.println(bits);
    }

    public void walkAndAssociateBitsAndNodes(INode node, Iterator<Node> iterator) {


        //a bit is a char in the GUI
        if (node instanceof Identifier) { //a leaf
            Bit b;
            while ((b = (Bit) iterator.next()).getText().matches("[()]"));
            System.out.println("id bit in q: " +b);
            if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
            node.setBit(b);
            return;
        }

        if (node instanceof NodeForBrackets) {
            nodes.push(node);
            node=node.children()[0];
            //that's great, but we've NOIDEA where in the bit box the brackets are,
            //do we need to put these on a stack?
            //and put the opens on a stack to, and then pop and pair?
            //lord

            //then when highlighting a node, if it has a bracket buddy, do that one too,
            //ie: move that logic out the click listener, fair
        }

        walkAndAssociateBitsAndNodes(node.children()[0], iterator);

        Bit b;
        //skip closing brackets
        while ((b = (Bit) iterator.next()).getText().matches("[)]"));
        System.out.println("bit in q: " +b);

        if (b.getText().matches("[(]")){
            bits.push(b);
        } else {
            if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
        }
        node.setBit(b);


        if (node.children().length>1) walkAndAssociateBitsAndNodes(node.children()[1], iterator);
    }
}
