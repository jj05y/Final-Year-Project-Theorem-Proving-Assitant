package gui.listeners;

import gui.ProtoOne;
import gui.core.ProofStep;
import gui.core.Theorem;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Created by joe on 17/12/16.
 */
public class CLTheorem implements EventHandler<MouseEvent> {

    private VBox workArea;

    public CLTheorem(VBox workArea) {
        this.workArea = workArea;
    }

    @Override
    public void handle(MouseEvent event) {
        Theorem t = (Theorem) event.getSource();
        System.out.println("Clicked on " + t.getRoot());

        workArea.getChildren().clear();
        workArea.getChildren().add(new ProofStep(t.getRoot(), "definition"));

    }

    public VBox getWorkArea() {
        return workArea;
    }

    public void setWorkArea(VBox workArea) {
        this.workArea = workArea;
    }
}
