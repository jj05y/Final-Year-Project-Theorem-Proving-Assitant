package gui.core;

import constants.Operators;
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

    public Theorem(INode root, State state) {
        this.root = root;
        int index = state.getTheorems().getChildren().size();
        this.getChildren().add(new Text((Operators.DOT + "(" + index + ") [" + root.toString() + "]")));
    }

    public Theorem(INode root, int index) {
        this.root = root;
        this.getChildren().add(new Text((Operators.STAR + "(" + index + ") [" + root.toString() + "]")));
    }

    public INode getRoot() {
        return root;
    }

    public void setRoot(INode root) {
        this.root = root;
    }


}
