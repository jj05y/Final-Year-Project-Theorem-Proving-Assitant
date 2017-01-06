package gui.core;

import interfaces.INode;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import workers.TreePermuter;

import java.util.Optional;
import java.util.Set;

/**
 * Created by joe on 31/12/16.
 */
public class AlertMessage {
    public AlertMessage(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.getDialogPane().setStyle("-fx-font-size: 2.2em;");
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(alert.getDialogPane().getWidth()*2.5);
        alert.showAndWait();
    }

    public AlertMessage(Theorem t, State state, String title, String body) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.getDialogPane().setStyle("-fx-font-size: 2.2em;");
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(alert.getDialogPane().getWidth()*2.5);

        ButtonType deleteButton = new ButtonType("Delete");
        ButtonType continueButton = new ButtonType("Continue Work");
        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(continueButton, deleteButton, closeButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == deleteButton){
            state.getTheorems().getChildren().remove(state.getTheorems().getChildren().indexOf(t));
        } else if (result.get() == continueButton) {
            System.out.println("options");
            Set<INode> continueOptions = (new TreePermuter()).getUniqueStringPermsSplitOnJoiners(t.getRoot());
            state.getOptions().getChildren().clear();
            for (INode n : continueOptions) {
                ProofStep step = new ProofStep(n, "{ continue }", state, false,"");
                state.getOptions().getChildren().add(step);
            }
        }
    }
}
