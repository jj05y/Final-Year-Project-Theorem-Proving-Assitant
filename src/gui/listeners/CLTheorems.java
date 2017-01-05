package gui.listeners;

import beans.ExprAndHintandTransition;
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



        int index = state.getTheorems().getChildren().indexOf(t);

        System.out.println("sel: " + state.getCurrSelection());
        System.out.println("t  : " + t.getRoot());

        Set<ExprAndHintandTransition> replacements = replacer.getReplacements(state.getCurrSelection(), t.getRoot());
        System.out.println(replacements);
        for (ExprAndHintandTransition exprAndHintandTransition : replacements) {
            state.getOptions().getChildren().add(new ProofStep(exprAndHintandTransition.getExpression(), exprAndHintandTransition.getHint(index), state, false, exprAndHintandTransition.getTransition()));
        }


    }
}
