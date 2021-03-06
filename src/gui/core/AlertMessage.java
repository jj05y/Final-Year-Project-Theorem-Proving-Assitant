package gui.core;

import constants.Operators;
import gui.TheoremProvingAssistant;
import interfaces.INode;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import parser.Parser;
import terminals.Identifier;
import workers.Matcher;
import workers.TreePermuter;

import java.util.*;

/**
 * Created by joe on 31/12/16.
 */
//This class is a wrapper for alert dialogs needed
public class AlertMessage {

    public AlertMessage(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.getDialogPane().getStylesheets().add(TheoremProvingAssistant.class.getResource("Selection.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialogs");
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(alert.getDialogPane().getWidth() * 2.5);
        alert.showAndWait();
    }

    public AlertMessage(Theorem t, State state, String title, String body) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.getDialogPane().getStylesheets().add(TheoremProvingAssistant.class.getResource("Selection.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialogs");
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(alert.getDialogPane().getWidth() * 2.5);

        ButtonType deleteButton = new ButtonType("Delete");
        ButtonType continueButton = new ButtonType("Continue Work");
        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        if (t.isAxiom()) {
            alert.getButtonTypes().setAll(deleteButton, closeButton);
        } else {
            alert.getButtonTypes().setAll(continueButton, deleteButton, closeButton);
        }

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == deleteButton) {
            state.getTheorems().getChildren().remove(state.getTheorems().getChildren().indexOf(t));
        } else if (result.get() == continueButton) {
            Set<INode> continueOptions = (new TreePermuter()).getUniqueStringPermsSplitOnJoiners(t.getRoot());
            state.getOptions().getChildren().clear();
            state.getWorkArea().getChildren().clear();
            List<ProofStep> steps = new Vector<>();
            for (INode n : continueOptions) {
                steps.add(new ProofStep(n.copySubTree(), "\t\t{ continue.(" + t.getIndex() + ") }", state, false, ""));
            }
            state.getOptions().getChildren().addAll(steps);
        }
        state.getStage().requestFocus();
        state.getStage().toBack();
        state.getStage().toFront();
    }

    private HashMap<String, INode> extras;

    public AlertMessage(Set<INode> unknownMappings, Matcher.Match match, INode rule) {
        String matchInRule = "Part of rule that matches: " + match.getRootOfMatchedNode().toString();
        StringBuilder sb = new StringBuilder();
        Set<String> keys = match.getLoopUpTable().keySet();
        for (String s : keys) {
            s = s.equals("") ? "empty-range" : s;
            if (!(s.equals(Operators.TRUE)) && !(s.equals(Operators.FALSE))) sb.append(s + ",");
        }
        if (sb.length() > 0) {
            sb.replace(sb.length() - 1, sb.length(), " := ");
            for (String s : keys) {
                String val = match.getLoopUpTable().get(s).toString();
                s = val.equals("") ? "empty-range" : val;
                if (!(s.equals(Operators.TRUE)) && !(s.equals(Operators.FALSE))) sb.append(s + ",");
            }
        } else {
            sb.append("No mappings defined..");
        }

        String lookUpTableSoFar = "Look up Table: " + sb.substring(0, sb.length() - 1);
        extras = new HashMap<>();
        String ruleSelected = "Rule Selected: " + rule;

        String unknownsString = "";
        for (INode n : unknownMappings) {
            unknownsString += "," + n;
        }
        unknownsString = unknownsString.substring(1);
        TextInputDialog dialog = new TextInputDialog(unknownsString);
        dialog.setTitle("Mappings");
        dialog.setHeaderText(ruleSelected + "\n" + matchInRule + "\n" + lookUpTableSoFar);

        dialog.setContentText(unknownsString + " :=");

        dialog.getDialogPane().getStylesheets().add(TheoremProvingAssistant.class.getResource("Selection.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("dialogs");
        dialog.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        dialog.setResizable(true);
        dialog.getDialogPane().setPrefWidth(dialog.getDialogPane().getWidth() * 2.5);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String[] unknowns = unknownsString.split(",");
            String[] mapping = result.get().split(",");
            Parser parser;
            for (int i = 0; i < mapping.length; i++) {
                parser = new Parser(mapping[i]);
                INode n = parser.getTree();
                if (n != null) {
                    extras.put(unknowns[i], n);
                } else {
                    new AlertMessage("Invalid Mapping", "Invalid mapping given, reverting to rule default");
                }
            }
        }
    }

    public HashMap<String, INode> getGetExtraMappings() {
        return extras;
    }

}
