package gui.listeners;

import gui.core.Bit;
import gui.core.Colors;
import gui.core.State;
import gui.core.Theorem;
import interfaces.INode;
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

    public CLSelectionCycler(State state, HBox box) {
        this.box = box;
        this.state = state;
    }


    @Override
    public void handle(MouseEvent event) {
        for (Node n2 : box.getChildren()) {
            ((Bit) n2).setWhite();
        }
        Bit clicked = (Bit) event.getSource();

        for (Node n : state.getTheorems().getChildren()) ((Theorem) n).setBackground(Colors.white);
        state.getOptions().getChildren().clear();


        INode selection = clicked.cycleAndGetSelection();
        if (state != null) state.setCurrSelection(selection);
        System.out.println("curr selection: " + selection);
    }

    public HBox getBox() {
        return box;
    }

    public void setBox(HBox box) {
        this.box = box;
    }




}
