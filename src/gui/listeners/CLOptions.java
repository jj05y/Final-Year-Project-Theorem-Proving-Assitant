package gui.listeners;

import gui.core.Colors;
import gui.core.ProofStep;
import gui.core.State;
import gui.core.Theorem;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Created by joe on 20/12/16.
 */
public class CLOptions implements EventHandler<MouseEvent> {

    private State state;

    public CLOptions(State state) {
        this.state = state;
    }

    @Override
    public void handle(MouseEvent event) {
        ProofStep proofStep = (ProofStep) event.getSource();
        proofStep.setOnMouseClicked(null);
        state.getWorkArea().getChildren().add(proofStep);
        state.setCurrProofStep(proofStep);
        proofStep.setToBeTheOnlySelectableProofStep();
        state.getOptions().getChildren().clear();
        ((ProofStep) state.getWorkArea().getChildren().get(state.getWorkArea().getChildren().size()-2)).removeSelection();
        for (Node n : state.getTheorems().getChildren()) ((Theorem) n).setBackground(Colors.white);

    }
}
