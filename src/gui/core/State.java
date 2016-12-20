package gui.core;

import interfaces.INode;
import javafx.scene.layout.VBox;

/**
 * Created by joe on 20/12/16.
 */
public class State {

    private INode currSelection;
    private ProofStep currProofStep;
    private VBox workArea;
    private VBox theorems;

    public State(VBox theorems, VBox workArea) {
        this.theorems = theorems;
        this.workArea = workArea;
    }

    public INode getCurrSelection() {
        return currSelection;
    }

    public void setCurrSelection(INode currSelection) {
        this.currSelection = currSelection;
    }

    public ProofStep getCurrProofStep() {
        return currProofStep;
    }

    public void setCurrProofStep(ProofStep currProofStep) {
        this.currProofStep = currProofStep;
    }

    public VBox getWorkArea() {
        return workArea;
    }

    public void setWorkArea(VBox workArea) {
        this.workArea = workArea;
    }

    public VBox getTheorems() {
        return theorems;
    }

    public void setTheorems(VBox theorems) {
        this.theorems = theorems;
    }
}
