package gui.core;

import gui.workers.BitBoxMaker;
import interfaces.INode;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by joe on 16/12/16.
 */
public class Theorem extends HBox {

    private INode root;

    public Theorem(INode root) {
        this.root = root;
        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addAll((new BitBoxMaker()).getBits(root));

    }

    public INode getRoot() {
        return root;
    }

    public void setRoot(INode root) {
        this.root = root;
    }

    public void highLight() {
        for (Node n : this.getChildren()){
            ((Bit) n ).setGreen();
        }
    }

    public void unHighLight() {
        for (Node n : this.getChildren()) {
            ((Bit) n ).setWhite();
        }
    }
}
