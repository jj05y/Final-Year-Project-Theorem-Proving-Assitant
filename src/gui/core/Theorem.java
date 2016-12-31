package gui.core;

import constants.Operators;
import gui.listeners.CLTheorems;
import gui.workers.BitBoxMaker;
import interfaces.INode;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import nodes.BinaryOperator;

import java.util.List;

/**
 * Created by joe on 16/12/16.
 */
public class Theorem extends FlowPane {

    private INode root;
    private boolean isAxiom;
    private String derivation;

    //only used by keeper button
    public Theorem(ObservableList<Node> steps, State state) {

        INode lhs = ((ProofStep) steps.get(0)).getExpression();
        INode rhs = ((ProofStep) steps.get(steps.size() - 1)).getExpression();

        //TODO determine operator
        char transition = findTranstitionOperator(steps);
        this.root = new BinaryOperator(transition, lhs, rhs);
        this.derivation = buildDerivation(steps);

        int index = state.getTheorems().getChildren().size();
        this.getChildren().add(new Text((Operators.DOT + "(" + index + ") [" + root.toString() + "]")));
        this.setOnMouseClicked(new CLTheorems(state));
        this.isAxiom = false;
    }

    private char findTranstitionOperator(ObservableList<Node> steps) {
        char correctTransition = Operators.EQUIVAL;
        for (Node step : steps) {
            char currentTransition = ((ProofStep) step).getTransition();
            if (Operators.precedence.containsKey(currentTransition)) {
                if (Operators.precedence.get(correctTransition) < Operators.precedence.get(currentTransition)) {
                    correctTransition = currentTransition;
                }
            }
        }
        return correctTransition;
    }

    private String buildDerivation(ObservableList<Node> steps) {
        String derivation = "";
        for (Node n : steps) {
            ProofStep ps = (ProofStep) n;
            //if there is a full hint with transtition, chop it off,
            String line = ps.getTransition() + "\t\t" + (ps.getHint().length()>1?ps.getHint().substring(1):ps.getHint()) + "\n   " + ps.getExpression();
            derivation += line + "\n";
        }
            return derivation.substring(1);
    }

    public Theorem(INode root, int index, boolean isAxiom, String derivation) {
        this.root = root;
        this.getChildren().add(new Text(((isAxiom ? Operators.STAR+"" : Operators.DOT +"") + "(" + index + ") [" + root.toString() + "]")));
        this.isAxiom = isAxiom;
        if (isAxiom) {
            this.derivation = "This theorem is an Axiom so it has no derivation";
        } else {
            this.derivation = derivation;
        }
    }



    public INode getRoot() {
        return root;
    }

    public void setRoot(INode root) {
        this.root = root;
    }

    public boolean isAxiom() {
        return isAxiom;
    }

    public String getDerivation() {
        return derivation;
    }
}
