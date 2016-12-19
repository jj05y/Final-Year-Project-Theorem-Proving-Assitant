package gui.core;

import gui.listeners.CLSelectionCycler;
import gui.workers.Associator;
import gui.workers.BitBoxMaker;
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

        List<INode> treesForExpression = (new TreePermuter()).getTreesForExpression(expression);

        HBox box = (new BitBoxMaker()).getBitBox(expression);

        Associator associator = new Associator();
        for (INode root : treesForExpression) associator.walkAndAssociateBitsAndNodes(root, box.getChildren().iterator());

        CLSelectionCycler selectionCycler = new CLSelectionCycler(box);
        for (Node n : box.getChildren()) n.setOnMousePressed(selectionCycler);

        this.getChildren().add(box);
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
