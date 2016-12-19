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
        while (!nodes.empty()) {
            INode n = nodes.pop();
            Bit b = bits.pop();
            n.setBit(b);
            if (!b.getNodesInTree().contains(n)) b.getNodesInTree().add(n);
        }
    }

    public void walkAndAssociateBitsAndNodes(INode node, Iterator<Node> iterator) {



        if (node instanceof Identifier) {
            Bit b;
            while ((b = (Bit) iterator.next()).getText().equals(")"));
            if (b.getText().equals("(")) {
                bits.push(b);
                while ((b = (Bit) iterator.next()).getText().equals(")"));
            }

            if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
            node.setBit(b);

            return;
        }

        if (node instanceof NodeForBrackets) {
            nodes.push(node);
            node = node.children()[0];
        }

        walkAndAssociateBitsAndNodes(node.children()[0], iterator);

        Bit b;
        while ((b = (Bit) iterator.next()).getText().equals(")"));
            if (b.getText().equals("(")) {
            bits.push(b);
            System.out.println("push2: (");
            while ((b = (Bit) iterator.next()).getText().equals(")"));
        }

        if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
        node.setBit(b);

        if (node.children().length > 1) walkAndAssociateBitsAndNodes(node.children()[1], iterator);

    }
}

