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
    private HBox bitBox;

    private String transition;



    public ProofStep(INode expression, String hint, State state, boolean isProofStep, String transition) {
        this.expression = expression;
        this.hint = hint;
        this.state = state;

        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(new Text(hint));

        box = new HBox();
        box.getChildren().add(new Text("\t"));

        bitBox = (new BitBoxMaker()).getBitBox(expression);
        box.getChildren().add(bitBox);

        if (isProofStep) {
            setToBeTheOnlySelectableProofStep();
        } else {
            this.setOnMouseClicked(new CLOptions(state));
        }

        this.getChildren().add(box);
        this.transition = transition;
    }

    public void associateAndSetClickListenerForBits() {
        // remove current associations
        for (Node n : bitBox.getChildren()) ((Bit) n).getNodesInTree().clear();

        List<INode> treesForExpression = (new TreePermuter()).getTreesForExpression(expression);

        Associator associator = new Associator();
        for (INode root : treesForExpression) associator.associate(root, bitBox.getChildren().iterator());

        CLSelectionCycler selectionCycler = new CLSelectionCycler(state, bitBox);
        for (Node n : bitBox.getChildren()) n.setOnMousePressed(selectionCycler);
    }

    public void removeClickListenerForBits() {
        for (Node n : bitBox.getChildren()) n.setOnMousePressed(null);
    }

    public INode getExpression() {
        return expression;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void removeSelection() {
        for (Node n : bitBox.getChildren()) {
            ((Bit) n).setWhite();
        }
    }

    public void setToBeTheOnlySelectableProofStep() {
        for (Node n : state.getWorkArea().getChildren()) {
            ((ProofStep) n).removeClickListenerForBits();
        }
        associateAndSetClickListenerForBits();
    }

    public String getTransition() {
        return transition;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProofStep)) return false;
        ProofStep other = (ProofStep) obj;
        return this.transition.equals(other.getTransition()) &&
                this.expression.toString().equals(other.getExpression().toString()) &&
                this.hint.equals(other.getHint());
    }
}
