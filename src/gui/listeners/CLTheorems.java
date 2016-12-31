package gui.listeners;

import core.ExprAndHint;
import gui.core.*;
import interfaces.INode;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import workers.Replacer;

import java.util.Iterator;
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

        Set<ExprAndHint> replacements = replacer.getReplacements(state.getCurrSelection(), t.getRoot());

        for (ExprAndHint exprAndHint : replacements) {
            state.getOptions().getChildren().add(new ProofStep(exprAndHint.getExpression(), exprAndHint.getHint(index), state, false));
        }


    }
}
