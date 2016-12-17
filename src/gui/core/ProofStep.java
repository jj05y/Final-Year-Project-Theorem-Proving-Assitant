package gui.core;

import interfaces.INode;
import terminals.Identifier;
import workers.TreePermuter;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.List;

/**
 * Created by joe on 16/12/16.
 */
public class ProofStep extends VBox {

    private INode expression;
    private String hint;
    HBox box;

    public ProofStep(INode expression, String hint) {
        this.expression = expression;
        this.hint = hint;

        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().add(new Text(hint));
        box = new HBox();

        List<INode> treesForExpression = (new TreePermuter()).permuteJustOneTier(expression);

        for (int i = 0; i < treesForExpression.size(); i++) {
            if (!treesForExpression.get(i).toString().equals(expression.toString())) {
                treesForExpression.remove(i--);
            }
        }


        //TODO this
        //this is going to have to be changed to an in order traversal of every tree in trees for expression,
        //yeah, and create the bits on the first one, and then add to list or node on following ones,
        HBox box = new HBox(3);
        for (char c : expression.toString().toCharArray()) {
            if (c != ' ') {
                box.getChildren().add(new Bit(new Text(c + "")));
            }
        }

        //walk trees and associate bits and nodes
        for (INode root : treesForExpression) {
            walkAndAssociateBitsAndNodes(root, box.getChildren().iterator());
        }


        for (Node n : box.getChildren()) {
            n.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n2 : box.getChildren()) {
                        ((Bit) n2).setWhite();
                    }
                    Bit clicked = (Bit) event.getSource();
                    String selection = clicked.cycleAndGetSelection();
                }
            });
        }
        this.getChildren().add(box);

    }

    private void walkAndAssociateBitsAndNodes(INode node, Iterator<Node> iterator) {

        //a bit is a char in the GUI
        if (node instanceof Identifier) { //a leaf
            Bit b = ((Bit) iterator.next());
            if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
            node.setBit(b);
            return;
        }

        walkAndAssociateBitsAndNodes(node.children()[0], iterator);
        Bit b = ((Bit) iterator.next());
        if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
        node.setBit(b);
        if (node.children().length>1) walkAndAssociateBitsAndNodes(node.children()[1],iterator);
    }

    public INode getExpression() {
        return expression;
    }

    public void setExpression(INode expression) {
        this.expression = expression;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
