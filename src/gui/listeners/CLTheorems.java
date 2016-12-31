package gui.listeners;

import core.ExprAndHintandTransition;
import gui.core.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import workers.Replacer;

import java.util.Set;

/**
 * Created by joe on 17/12/16.
 */
public class CLTheorems implements EventHandler<MouseEvent> {

    private State state;
    private Replacer replacer;



    public CLTheorems(State state) {
        this.state = state;
        replacer = new Replacer();
    }

    @Override
    public void handle(MouseEvent event) {
        Theorem t = (Theorem) event.getSource();

        if (state.getCurrSelection() == null) {
            // show derivation
            new AlertMessage(t.getRoot().toString(), "Derivation:\n" + t.getDerivation());
            return;
        }

        for (Node n : state.getTheorems().getChildren()) ((Theorem) n).setBackground(Colors.white);
        t.setBackground(Colors.green);

        state.getOptions().getChildren().clear();

        //for (Node n : state.getTheorems().getChildren()) {
        //    ((Theorem) n).unHighLight();
        //}
        //t.highLight();
        int index = state.getTheorems().getChildren().indexOf(t);

        Set<ExprAndHintandTransition> replacements = replacer.getReplacements(state.getCurrSelection(), t.getRoot());

        for (ExprAndHintandTransition exprAndHintandTransition : replacements) {
            state.getOptions().getChildren().add(new ProofStep(exprAndHintandTransition.getExpression(), exprAndHintandTransition.getHint(index), state, false, exprAndHintandTransition.getTransition()));
        }


    }
}
