package gui.core;

import gui.ProtoOne;
import interfaces.INode;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import workers.TreePermuter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

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
                steps.add(new ProofStep(n.copySubTree(), "{ continue }", state, false, ""));
            }
            state.getOptions().getChildren().addAll(steps);

        }
    }
}
