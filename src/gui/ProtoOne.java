package gui;

import constants.Operators;
import gui.core.ProofStep;
import gui.core.State;
import gui.core.Theorem;
import gui.listeners.CLTheorems;
import gui.theoremsets.TheoremSet1;
import interfaces.INode;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import nodes.CommutativeAssociativeBinaryOperator;
import nodes.NodeForBrackets;
import terminals.Identifier;
import trees.Trees;

/**
 * Created by joe on 16/12/16.
 */
public class ProtoOne extends Application {

    private VBox workArea;
    private VBox theorems;
    private VBox options;
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
        options = new VBox();
        options.setPadding(new Insets(10));

        //TODO this should be a "LOAD"
        theorems.getChildren().addAll((new TheoremSet1()).getTheoremSet1());

        state = new State(theorems, workArea, options);
        CLTheorems theoremClickListener = new CLTheorems(state);
        for (Node theorem : theorems.getChildren()) {
            theorem.setOnMouseClicked(theoremClickListener);
        }


        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER);
        Button buttonUndo = new Button("Undo");
        Button buttonKeeper = new Button("Keeper");
        Button buttonRemoveBrackets = new Button("Remove Brackets");
        Button button4 = new Button("Button4");

        buttonBox.getChildren().addAll(buttonUndo, buttonKeeper, buttonRemoveBrackets, button4);

         buttonUndo.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 if (workArea.getChildren().size()>1) workArea.getChildren().remove(workArea.getChildren().size()-1);
                 ((ProofStep) workArea.getChildren().get(workArea.getChildren().size()-1)).associateAndSetClickListenerForBits();
             }
         });

        buttonKeeper.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                INode lhs = ((ProofStep) workArea.getChildren().get(0)).getExpression();
                INode rhs = ((ProofStep) workArea.getChildren().get(workArea.getChildren().size()-1)).getExpression();
                INode newTheorem = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL,lhs,rhs);
                theorems.getChildren().add(new Theorem(newTheorem,state));
                workArea.getChildren().clear();
                options.getChildren().clear();

                //TODO fix this
                ProofStep step = new ProofStep(Trees.XandYorZwithBrackets(), "Something", state, true);
                workArea.getChildren().add(step);
                state.setCurrProofStep(step);
            }
        });

        buttonRemoveBrackets.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                INode currSelection = state.getCurrSelection();
                if (currSelection instanceof NodeForBrackets && currSelection.children()[0] instanceof Identifier) {
                    ((NodeForBrackets) currSelection).removeBrackets();
                    ProofStep step = new ProofStep(currSelection.getRoot(),"{remove brackets}", state, true);
                    state.getWorkArea().getChildren().add(step);
                }
            }
        });

        HBox inputBox = new HBox(5);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        Text inputText = new Text("Input:");
        TextField inputField = new TextField();
        inputBox.getChildren().addAll(inputText, inputField);

        ScrollPane theoremsScrollPane = new ScrollPane();
        theoremsScrollPane.setContent(theorems);
        ScrollPane workAreaScrollPane = new ScrollPane();
        workAreaScrollPane.setContent(workArea);
        ScrollPane optionsAreaScrollPane = new ScrollPane();
        optionsAreaScrollPane.setContent(options);


        theoremsScrollPane.prefWidthProperty().bind(grid.widthProperty());
        theoremsScrollPane.prefHeightProperty().bind(grid.heightProperty());
        workAreaScrollPane.prefWidthProperty().bind(grid.widthProperty());
        workAreaScrollPane.prefHeightProperty().bind(grid.heightProperty());
        optionsAreaScrollPane.prefWidthProperty().bind(grid.widthProperty());
        optionsAreaScrollPane.prefHeightProperty().bind(grid.heightProperty());


        theoremsScrollPane.getStyleClass().add("area");
        workAreaScrollPane.getStyleClass().add("area");
        optionsAreaScrollPane.getStyleClass().add("area");

        theoremsScrollPane.setFitToWidth(true);
        workAreaScrollPane.setFitToWidth(true);
        optionsAreaScrollPane.setFitToWidth(true);

        grid.add(inputBox, 0, 0, 2, 1);
        grid.add(new Text("Work Area:"), 0, 1, 1, 1);
        grid.add(workAreaScrollPane, 0, 2, 1, 3);
        grid.add(new Text("Theorems:"), 1, 1, 1, 1);
        grid.add(theoremsScrollPane, 1, 2, 1, 1);
        grid.add(new Text("Options:"), 1, 3, 1, 1);
        grid.add(optionsAreaScrollPane, 1, 4, 1, 1);
        grid.add(buttonBox, 0, 5, 2, 1);


        //lets hard code a theorem to the work area, and change theorem clicklistener to cycle the current selection,
        ProofStep step = new ProofStep(Trees.XandYorZwithBrackets(), "Something", state, true);
        workArea.getChildren().add(step);
        state.setCurrProofStep(step);

        Scene scene = new Scene(grid, 1600, 1200);
        scene.getStylesheets().add(ProtoOne.class.getResource("Selection.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();

    }

}

