package gui.core;

import constants.Operators;
import gui.listeners.CLOptions;
import gui.listeners.CLSelectionCycler;
import gui.workers.Associator;
import gui.workers.BitBoxMaker;
import interfaces.INode;
import workers.TreePermuter;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Created by joe on 16/12/16.
 */
public class ProofStep extends VBox {

    private State state;
    private INode expression;
    private String hint;
    private HBox box;
    private char transition;



    public ProofStep(INode expression, String hint, State state, boolean isProofStep) {
        this.expression = expression;
        this.hint = hint;
        this.state = state;

        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().add(new Text(hint));
        box = new HBox();


        box = (new BitBoxMaker()).getBitBox(expression);


        if (isProofStep) {
            associateAndSetClickListenerForBits();
        } else {
            this.setOnMouseClicked(new CLOptions(state));
        }

        this.getChildren().add(box);
        this.transition = Operators.IMPLICATION;
    }

    public void associateAndSetClickListenerForBits() {
        // remove current associations
        for (Node n : box.getChildren()) ((Bit) n).getNodesInTree().clear();

        long startTime = System.currentTimeMillis();
        List<INode> treesForExpression = (new TreePermuter()).getTreesForExpression(expression);

        Associator associator = new Associator();
        for (INode root : treesForExpression) associator.associate(root, box.getChildren().iterator());

        CLSelectionCycler selectionCycler = new CLSelectionCycler(state, box);
        for (Node n : box.getChildren()) n.setOnMousePressed(selectionCycler);
    }

    public void removeClickListenerForBits() {
        for (Node n : box.getChildren()) n.setOnMousePressed(null);
    }

    public INode getExpression() {
        return expression;
    }

    public void setExpression(INode expression) {
        this.expression = expression;
        this.getChildren().remove(1);
        HBox box = (new BitBoxMaker()).getBitBox(expression);
        this.getChildren().add(box);
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void removeSelection() {
        for (Node n : box.getChildren()) {
            ((Bit) n).setWhite();
        }
    }

    public void setToBeTheOnlySelectableProofStep() {
        for (Node n : state.getWorkArea().getChildren()) {
            ((ProofStep) n).removeClickListenerForBits();
        }
        associateAndSetClickListenerForBits();
    }

    public char getTransition() {
        return transition;
    }
}
