package Gui.Core;

import Gui.Core.Bit;
import Interfaces.INode;
import javafx.geometry.Pos;
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
        for (char c : root.toString().toCharArray()) {
            if (c != ' ') {
                this.getChildren().add(new Bit(new Text(c + "")));
            }
        }

    }

    public INode getRoot() {
        return root;
    }

    public void setRoot(INode root) {
        this.root = root;
    }
}
