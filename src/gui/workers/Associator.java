package gui.workers;

import constants.Operators;
import gui.core.Bit;
import interfaces.INode;
import interfaces.ITerminal;
import javafx.scene.Node;
import nodes.NodeForBrackets;
import nodes.UnaryOperator;
import terminals.Identifier;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by joe on 17/12/16.
 */
public class Associator {

    private Stack<INode> bracketNodes;
    private Stack<Bit> bracketBits;
    private Stack<INode> unaryNodes;
    private Stack<Bit> unaryBits;


    public void associate(INode node, Iterator<Node> iterator) {
        this.bracketNodes = new Stack<>();
        this.bracketBits = new Stack<>();
        this.unaryNodes = new Stack<>();
        this.unaryBits = new Stack<>();
        walkAndAssociateBitsAndNodes(node, iterator);
        dealWithStack(unaryNodes,unaryBits);
        dealWithStack(bracketNodes,bracketBits);
    }

    private void dealWithStack(Stack<INode> nodes, Stack<Bit> bits) {
        while (!nodes.empty()) {
            INode n = nodes.pop();
            Bit b = bits.pop();
            n.setBit(b);
            if (!b.getNodesInTree().contains(n)) b.getNodesInTree().add(n);
        }
    }


    private void walkAndAssociateBitsAndNodes(INode node, Iterator<Node> iterator) {

        while (node instanceof NodeForBrackets || node instanceof UnaryOperator) {
            if (node instanceof NodeForBrackets) {
                bracketNodes.push(node);
                node = node.children()[0];
            }
            if (node instanceof UnaryOperator) {
                unaryNodes.push(node);
                node = node.children()[0];
            }

        }


        if (node instanceof ITerminal) {
            Bit b;
            //we don't care about )
            while ((b = (Bit) iterator.next()).getText().equals(")")) ;

            while (b.getText().equals("(") || b.getText().equals(Operators.NOT + "")) {
                if (b.getText().equals("(")) {
                    bracketBits.push(b);
                    while ((b = (Bit) iterator.next()).getText().equals(")")) ;
                }
                if (b.getText().equals(Operators.NOT + "")) {
                    unaryBits.push(b);
                    b = (Bit) iterator.next();
                }
            }

            if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
            node.setBit(b);

            return;
        }

        walkAndAssociateBitsAndNodes(node.children()[0], iterator);

        Bit b;
        while ((b = (Bit) iterator.next()).getText().equals(")")) ;

        while (b.getText().equals("(") || b.getText().equals(Operators.NOT + "")) {

            if (b.getText().equals("(")) {
                bracketBits.push(b);
                while ((b = (Bit) iterator.next()).getText().equals(")")) ;
            }
            if (b.getText().equals(Operators.NOT + "")) {
                unaryBits.push(b);
                b = (Bit) iterator.next();

            }
        }

        if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
        node.setBit(b);

        if (node.children().length > 1) walkAndAssociateBitsAndNodes(node.children()[1], iterator);

    }
}

