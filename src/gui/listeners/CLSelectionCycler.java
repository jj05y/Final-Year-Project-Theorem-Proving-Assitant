package gui.listeners;

import gui.core.Bit;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by joe on 17/12/16.
 */
public class CLSelectionCycler implements EventHandler<MouseEvent> {

    private HBox box;
    Text selectionText;
    String selection;

    public CLSelectionCycler(HBox box) {
        this.box = box;
        this.selectionText = null;
    }

    public CLSelectionCycler(HBox box, Text selectionText) {
        this.box = box;
        this.selectionText = selectionText;
    }

    @Override
    public void handle(MouseEvent event) {
        for (Node n2 : box.getChildren()) {
            ((Bit) n2).setWhite();
        }
        Bit clicked = (Bit) event.getSource();
        selection = clicked.cycleAndGetSelection();
        if (selectionText!= null) selectionText.setText(selection);

    }

    public HBox getBox() {
        return box;
    }

    public void setBox(HBox box) {
        this.box = box;
    }

    public Text getSelectionText() {
        return selectionText;
    }

    public void setSelectionText(Text selectionText) {
        this.selectionText = selectionText;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
