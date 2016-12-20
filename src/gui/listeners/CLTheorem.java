package gui.listeners;

import core.ExprAndHint;
import gui.core.ProofStep;
import gui.core.State;
import gui.core.Theorem;
import interfaces.INode;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import workers.Replacer;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by joe on 17/12/16.
 */
public class CLTheorem implements EventHandler<MouseEvent> {

    private State state;
    private Replacer replacer;

    public CLTheorem(State state) {
        this.state = state;
        replacer = new Replacer();
    }

    @Override
    public void handle(MouseEvent event) {
        Theorem t = (Theorem) event.getSource();
        for (Node n : state.getTheorems().getChildren()) {
            ((Theorem) n).unHighLight();
        }
        t.highLight();
        Set<ExprAndHint> replacements = replacer.getReplacements(state.getCurrSelection(), t.getRoot());

        //TODO some way to choose from all, not just one
        Iterator<ExprAndHint> it = replacements.iterator();
        if (it.hasNext()) {
            ExprAndHint exprAndHint = it.next();
            state.getWorkArea().getChildren().add(new ProofStep(exprAndHint.getExpression(),exprAndHint.getHint(), state));
        }

    }
}
