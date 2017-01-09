package gui.workers;

import constants.Operators;
import gui.core.Bit;
import interfaces.INode;
import interfaces.ITerminal;
import javafx.scene.Node;
import nodes.NodeForBrackets;
import nodes.UnaryOperator;
import terminals.Identifier;
import terminals.QuantifiedExpr;

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
        System.out.println("Associating");
        System.out.println("node: " + node);
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

        if (node instanceof QuantifiedExpr) {
            //then there's 6 bits,
            QuantifiedExpr quantExpr = (QuantifiedExpr) node;
            //the start, the middlecolon and then end
            Bit langle = (Bit) iterator.next();
            langle.getNodesInTree().add(node);
            node.setBit(langle);

            //the range,
            associate(((QuantifiedExpr) node).getRange(), iterator);

            //dont care about middle colon, that's been bracketbuddied
            iterator.next();

            //the term
            associate(((QuantifiedExpr) node).getTerm(), iterator);

            //need to consume the rangle
            iterator.next();
            return;
        }


        if (node instanceof ITerminal) {
            Bit b;
            //we don't care about )
            while (Operators.bracketPair.containsValue((b = (Bit) iterator.next()).getText())) ;

            while (Operators.bracketPair.containsKey(b.getText()) || b.getText().equals(Operators.NOT)) {
                if (Operators.bracketPair.containsKey(b.getText())) {
                    bracketBits.push(b);
                    while (Operators.bracketPair.containsValue((b = (Bit) iterator.next()).getText()));
                }
                if (b.getText().equals(Operators.NOT)) {
                    unaryBits.push(b);
                    b = (Bit) iterator.next();
                }
                System.out.println("stuck");

            }

            if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
            node.setBit(b);

            return;
        }

        walkAndAssociateBitsAndNodes(node.children()[0], iterator);

        Bit b;
        while (Operators.bracketPair.containsValue((b = (Bit) iterator.next()).getText())) ;

        while (Operators.bracketPair.containsKey(b.getText()) || b.getText().equals(Operators.NOT)) {
            if (Operators.bracketPair.containsKey(b.getText())) {
                bracketBits.push(b);
                while (Operators.bracketPair.containsValue((b = (Bit) iterator.next()).getText()));
            }
            if (b.getText().equals(Operators.NOT)) {
                unaryBits.push(b);
                b = (Bit) iterator.next();
            }
        }

        if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
        node.setBit(b);

        if (node.children().length > 1) walkAndAssociateBitsAndNodes(node.children()[1], iterator);

    }
}

