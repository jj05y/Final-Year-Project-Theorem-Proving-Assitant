package gui.core;

import interfaces.INode;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by joe on 20/12/16.
 */
public class State {

    private Stage stage;
    private INode currSelection;
    private VBox workArea;
    private VBox theorems;
    private VBox options;


    public State(VBox theorems, VBox workArea, VBox options, Stage stage) {
        this.theorems = theorems;
        this.workArea = workArea;
        this.options = options;
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public INode getCurrSelection() {
        return currSelection;
    }

    public void setCurrSelection(INode currSelection) {
        this.currSelection = currSelection;
    }


    public VBox getWorkArea() {
        return workArea;
    }

    public void setWorkArea(VBox workArea) {
        this.workArea = workArea;
    }

    public VBox getTheorems() {
        return theorems;
    }

    public void setTheorems(VBox theorems) {
        this.theorems = theorems;
    }

    public VBox getOptions() {
        return options;
    }

    public void setOptions(VBox options) {
        this.options = options;
    }


}
