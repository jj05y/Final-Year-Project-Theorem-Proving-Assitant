package gui.core;

import constants.Operators;
import gui.listeners.CLTheorems;
import gui.workers.BitBoxMaker;
import interfaces.INode;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Created by joe on 16/12/16.
 */
public class Theorem extends FlowPane {

    private INode root;
    private boolean isAxiom;

    public Theorem(INode root, State state) {
        this.root = root;
        int index = state.getTheorems().getChildren().size();
        this.getChildren().add(new Text((Operators.DOT + "(" + index + ") [" + root.toString() + "]")));
        this.setOnMouseClicked(new CLTheorems(state));
        this.isAxiom = false;
    }

    public Theorem(INode root, int index, boolean isAxiom) {
        this.root = root;
        this.getChildren().add(new Text(((isAxiom ? Operators.STAR+"" : Operators.DOT +"") + "(" + index + ") [" + root.toString() + "]")));
        this.isAxiom = isAxiom;
    }

    public INode getRoot() {
        return root;
    }

    public void setRoot(INode root) {
        this.root = root;
    }

    public boolean isAxiom() {
        return isAxiom;
    }
}
