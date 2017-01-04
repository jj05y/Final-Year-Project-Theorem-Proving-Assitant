package gui;

import constants.Operators;
import gui.core.*;
import gui.listeners.CLTheorems;
import gui.theoremloadsave.TheoremLoader;
import gui.theoremloadsave.TheoremSaver;
import gui.theoremsets.TheoremSets;
import interfaces.INode;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import parser.Parser;
import terminals.Identifier;

import java.io.File;
import java.util.List;
import java.util.Vector;

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
        theorems.getChildren().addAll((new TheoremSets()).getTheoremSet2());

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
        Button buttonAddBrackets = new Button("Add Brackets");
        Button buttonClear = new Button("Clear");

        buttonBox.getChildren().addAll(buttonUndo, buttonKeeper, buttonRemoveBrackets, buttonAddBrackets, buttonClear);


        HBox inputBox = new HBox(5);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        Text inputText = new Text("Input:");
        TextField inputField = new TextField();
        inputBox.getChildren().addAll(inputText, inputField);
        Button buttonStart = new Button("Start");
        inputBox.getChildren().add(buttonStart);

        HBox theoremLoadSaveButtons = new HBox(5);
        Button buttonLoadTheorems = new Button("Load Theorems");
        Button buttonSaveTheorems = new Button("Save Theorems");
        theoremLoadSaveButtons.getChildren().addAll(buttonLoadTheorems,buttonSaveTheorems);
        theoremLoadSaveButtons.setAlignment(Pos.CENTER_RIGHT);

         buttonUndo.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 if (workArea.getChildren().size()>1) {
                     workArea.getChildren().remove(workArea.getChildren().size() - 1);
                     ((ProofStep) workArea.getChildren().get(workArea.getChildren().size() - 1)).associateAndSetClickListenerForBits();
                 } else if (workArea.getChildren().size() == 1) {
                     workArea.getChildren().clear();
                     state.setCurrSelection(null);
                 }
                 for (Node n : state.getTheorems().getChildren()) ((Theorem) n).setBackground(Colors.white);
             }
         });

        buttonKeeper.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                //TODO check for implication
                if (!(workArea.getChildren().isEmpty())) {

                    Theorem newTheorem = new Theorem(workArea.getChildren(), state);
                    newTheorem.setOnMouseClicked(new CLTheorems(state));
                    theorems.getChildren().add(newTheorem);
                    state.setCurrSelection(null);
                    workArea.getChildren().clear();
                    options.getChildren().clear();
                }
            }
        });

        buttonRemoveBrackets.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                INode currSelection = state.getCurrSelection();
                if (currSelection instanceof NodeForBrackets) {
                    if (currSelection.isRoot() ||
                            currSelection.getParent() instanceof NodeForBrackets ||
                            Operators.precedence.get(currSelection.getParent().getNodeChar()) <
                                    Operators.findLowestPrecendence(currSelection.children()[0], Integer.MAX_VALUE)) {
                        INode withoutBrackets = currSelection.removeBrackets();
                        ProofStep step = new ProofStep(withoutBrackets.getRoot(), "{remove brackets}", state, true, Operators.EQUIVAL);
                        state.getWorkArea().getChildren().add(step);
                        ((ProofStep) state.getWorkArea().getChildren().get(state.getWorkArea().getChildren().size() - 2)).removeSelection();
                        for (Node n : state.getTheorems().getChildren()) ((Theorem) n).setBackground(Colors.white);
                        state.setCurrSelection(null);
                    }
                }
            }
        });

        buttonAddBrackets.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                INode currentSelection = state.getCurrSelection();
                if (currentSelection != null) {
                    currentSelection.addBrackets();
                    ProofStep step = new ProofStep(currentSelection.getRoot(), "{add brackets}", state, true, Operators.EQUIVAL);
                    state.getWorkArea().getChildren().add(step);
                    ((ProofStep) state.getWorkArea().getChildren().get(state.getWorkArea().getChildren().size()-2)).removeSelection();
                    for (Node n : state.getTheorems().getChildren()) ((Theorem) n).setBackground(Colors.white);
                    state.setCurrSelection(null);
                }
            }
        });

        buttonClear.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                workArea.getChildren().clear();
                state.setCurrSelection(null);
                for (Node n : state.getTheorems().getChildren()) ((Theorem) n).setBackground(Colors.white);
                options.getChildren().clear();
                inputField.clear();
            }
        });


        buttonLoadTheorems.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //open file,
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Theorem File");
                File file = fileChooser.showOpenDialog(primaryStage);

                // give that file to theorem loader
                // get a list of theorems from theorem loader
                if (file != null) {
                    List<Theorem> newTheorems = (new TheoremLoader()).getTheorems(file);

                    // clear current list
                    theorems.getChildren().clear();
                    workArea.getChildren().clear();
                    options.getChildren().clear();
                    // put new theorems in,
                    theorems.getChildren().addAll(newTheorems);
                    for (Node n : theorems.getChildren()) {
                        n.setOnMouseClicked(new CLTheorems(state));
                    }
                }
            }
        });

        buttonSaveTheorems.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //choose save file
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Theorem File");
                File file = fileChooser.showSaveDialog(primaryStage);

                //give that file to theorem saver with the current theorem list
                if (file != null) {

                    (new TheoremSaver()).saveTheorems(file, theorems.getChildren());
                }
                //done
            }
        });



        buttonStart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (workArea.getChildren().size() == 0) {
                    String input = inputField.getText();
                    Parser parser = new Parser(input);
                    INode expression = parser.getTree();

                    if (expression != null) {
                        ProofStep step = new ProofStep(expression, "", state, true, ' ');
                        workArea.getChildren().add(step);
                        state.setCurrProofStep(step);
                        inputField.clear();
                    } else {
                        new AlertMessage("Invalid Input", "Please enter valid input\nEG:\n\tX and ( Y or Z )\n\tX => Y = ! Y or X\n\tX and ! ( Y and Z )");
                    }

                }
            }
        });


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

        grid.add(inputBox, 0, 0, 1, 1);
        grid.add(theoremLoadSaveButtons,1,0,1,1);
        grid.add(new Text("Work Area:"), 0, 1, 1, 1);
        grid.add(workAreaScrollPane, 0, 2, 1, 3);
        grid.add(new Text("Theorems:"), 1, 1, 1, 1);
        grid.add(theoremsScrollPane, 1, 2, 1, 1);
        grid.add(new Text("Options:"), 1, 3, 1, 1);
        grid.add(optionsAreaScrollPane, 1, 4, 1, 1);
        grid.add(buttonBox, 0, 5, 2, 1);




        Scene scene = new Scene(grid, 1600, 1200);
        scene.getStylesheets().add(ProtoOne.class.getResource("Selection.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();

    }

}

