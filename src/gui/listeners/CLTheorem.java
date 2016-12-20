package gui.listeners;

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
        System.out.println("hi");
        for (Node n : state.getTheorems().getChildren()) {
            ((Theorem) n).unHighLight();
        }
        t.highLight();
        Set<INode> replacements = replacer.getReplacements(state.getCurrSelection(), t.getRoot());
        //replacements.add(t.getRoot());
        Iterator<INode> it = replacements.iterator();
        if (it.hasNext()) state.getCurrProofStep().setExpression(it.next());

    }
}
