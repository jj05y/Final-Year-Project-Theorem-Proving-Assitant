package gui.listeners;

import gui.core.Bit;
import gui.core.Colors;
import gui.core.State;
import gui.core.Theorem;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by joe on 17/12/16.
 */
public class CLSelectionCycler implements EventHandler<MouseEvent> {

    private State state;
    private HBox box;
    Text selectionText;
    String selection;

    public CLSelectionCycler(State state, HBox box) {
        this.box = box;
        this.selectionText = null;
        this.state = state;
    }

    //currently only used by Selection.java no context :O;
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

        for (Node n : state.getTheorems().getChildren()) ((Theorem) n).setBackground(Colors.white);
        state.getOptions().getChildren().clear();


        selection = clicked.cycleAndGetSelection();
        if (selectionText != null) selectionText.setText(selection);
        if (state != null) state.setCurrSelection(clicked.getCurrSubExpression());
        if (state != null) System.out.println(state.getCurrSelection());

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
