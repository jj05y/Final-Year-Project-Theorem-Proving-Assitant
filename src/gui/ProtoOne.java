package gui;

import gui.core.ProofStep;
import gui.core.State;
import gui.listeners.CLTheorem;
import gui.theoremsets.TheoremSet1;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import trees.Trees;

/**
 * Created by joe on 16/12/16.
 */
public class ProtoOne extends Application{

    private VBox workArea;
    private VBox theorems;
    private State state;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        workArea = new VBox();
        workArea.setPadding(new Insets(10));
        theorems = new VBox();
        theorems.setPadding(new Insets(10));

        theorems.getStyleClass().add("area");
        workArea.getStyleClass().add("area");

        theorems.prefWidthProperty().bind(grid.widthProperty());
        workArea.prefWidthProperty().bind(grid.widthProperty());
        workArea.prefHeightProperty().bind(grid.heightProperty());
        theorems.prefHeightProperty().bind(grid.heightProperty());

        theorems.getChildren().addAll((new TheoremSet1()).getTheoremSet1());

        state = new State(theorems, workArea);
        CLTheorem theoremClickListener = new CLTheorem(state);
        for (Node theorem : theorems.getChildren()) {
            theorem.setOnMouseClicked(theoremClickListener);
        }


        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER);
        Button button1 = new Button("Button1");
        Button button2 = new Button("Button2");
        Button button3 = new Button("Button3");
        Button button4 = new Button("Button4");

        buttonBox.getChildren().addAll(button1, button2, button3, button4);


        HBox inputBox = new HBox(5);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        Text inputText = new Text("Input:");
        TextField inputField = new TextField();
        inputBox.getChildren().addAll(inputText,inputField);

        grid.add(inputBox,0,0,2,1);
        grid.add(workArea, 0, 1);
        grid.add(theorems, 1, 1);
        grid.add(buttonBox, 0, 2,2,1);


        //lets hard code a theorem to the work area, and change theorem clicklistener to cycle the current selection,
        ProofStep step = new ProofStep(Trees.XandYorZwithBrackets(), "Something",state);
        workArea.getChildren().add(step);
        state.setCurrProofStep(step);

        Scene scene = new Scene(grid, 1200, 800);
        scene.getStylesheets().add(Selection.class.getResource("Selection.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();

    }

}

