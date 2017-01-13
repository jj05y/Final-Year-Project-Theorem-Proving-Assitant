package gui.core;

import beans.ExprAndHintandTransition;
import constants.Operators;
import gui.ProtoOne;
import interfaces.INode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
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
        alert.getDialogPane().getStylesheets().add(ProtoOne.class.getResource("Selection.css").toExternalForm());
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
        alert.getDialogPane().getStylesheets().add(ProtoOne.class.getResource("Selection.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialogs");
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(alert.getDialogPane().getWidth() * 2.5);

        ButtonType deleteButton = new ButtonType("Delete");
        ButtonType continueButton = new ButtonType("Continue Work");
        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        if (t.isAxiom()) {
            alert.getButtonTypes().setAll(closeButton);
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
                steps.add(new ProofStep(n.copySubTree(), "{ continue.(" + t.getIndex() + ") }", state, false, ""));
            }
            state.getOptions().getChildren().addAll(steps);
        }
        state.getStage().requestFocus();
    }

    private HashMap<String, INode> extras;

    public AlertMessage(Set<INode> unknownMappings, Matcher.Match match) {
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

        String lookUpTableSoFar = "Look up Table: " + sb.substring(0,sb.length()-1);
        extras = new HashMap<>();
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Mappings");
        dialog.setHeaderText(matchInRule + "\n" + lookUpTableSoFar);
        String unknownsString = "";
        for (INode n : unknownMappings) {
            unknownsString += "," + n;
        }
        unknownsString = unknownsString.substring(1);
        dialog.setContentText(unknownsString + " :=");
        dialog.getDialogPane().getStylesheets().add(ProtoOne.class.getResource("Selection.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("dialogs");
        dialog.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        dialog.setResizable(true);
        dialog.getDialogPane().setPrefWidth(dialog.getDialogPane().getWidth() * 2.5);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String[] mapping = result.get().split(",");
            String[] unknowns = unknownsString.split(",");
            for (int i = 0; i < mapping.length; i++) {
                extras.put(unknowns[i], new Identifier(mapping[i]));
            }
        }
    }

    public HashMap<String, INode> getGetExtraMappings() {
        return extras;
    }

}
