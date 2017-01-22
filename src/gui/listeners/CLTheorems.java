package gui.listeners;

import beans.ExprAndHintandTransition;
import gui.core.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import workers.Replacer;

import java.util.Collections;
import java.util.Comparator;
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
            new AlertMessage(t, state, t.getRoot().toString(), "Derivation:\n" + t.getDerivation());
            return;
        }

        for (Node n : state.getTheorems().getChildren()) ((Theorem) n).setBackground(Colors.white);
        t.setBackground(Colors.green);

        state.getOptions().getChildren().clear();


        Set<ExprAndHintandTransition> replacements = replacer.getReplacements(state.getCurrSelection(), t.getRoot(), true);
        for (ExprAndHintandTransition exprAndHintandTransition : replacements) {
            ProofStep step = new ProofStep(exprAndHintandTransition.getExpression(), exprAndHintandTransition.getHint(t.getIndex()), state, false, exprAndHintandTransition.getTransition());
            if (!(state.getOptions().getChildren().contains(step))) state.getOptions().getChildren().add(step);
        }

        ObservableList<Node> nodes = FXCollections.observableArrayList(state.getOptions().getChildren());

        Collections.sort(nodes, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                ProofStep ps1 = (ProofStep) o1;
                ProofStep ps2 = (ProofStep) o2;
                Integer l1 = ps1.getExpression().toString().length();
                Integer l2 = ps2.getExpression().toString().length();
                return l1.compareTo(l2);
            }
        });


        state.getOptions().getChildren().setAll(nodes);

        state.getStage().requestFocus();
        state.getStage().toBack();
        state.getStage().toFront();

    }
}
