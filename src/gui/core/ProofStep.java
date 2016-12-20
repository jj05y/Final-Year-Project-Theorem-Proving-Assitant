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

    private State state;
    private INode expression;
    private String hint;
    HBox box;


    public ProofStep(INode expression, String hint, State state) {
        this.expression = expression;
        this.hint = hint;
        this.state = state;

        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().add(new Text(hint));
        box = new HBox();

        List<INode> treesForExpression = (new TreePermuter()).getTreesForExpression(expression);

        box = (new BitBoxMaker()).getBitBox(expression);

        Associator associator = new Associator();
        for (INode root : treesForExpression) associator.associate(root, box.getChildren().iterator());

        CLSelectionCycler selectionCycler = new CLSelectionCycler(state, box);
        for (Node n : box.getChildren()) n.setOnMousePressed(selectionCycler);

        this.getChildren().add(box);
    }

    public INode getExpression() {
        return expression;
    }

    public void setExpression(INode expression) {
        this.expression = expression;
        this.getChildren().remove(1);
        HBox box = (new BitBoxMaker()).getBitBox(expression);
        this.getChildren().add(box);
        System.out.println("expression apparently changed to " + expression);
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
