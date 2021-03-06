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

import nodes.NodeForBrackets;
import parser.Parser;

import java.io.File;
import java.util.List;

/**
 * Created by joe on 16/12/16.
 */
public class TheoremProvingAssistant extends Application {

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

        //TODO delete this? leave theorem list empty?
        theorems.getChildren().addAll((new TheoremSets()).getTheoremSet3());

        state = new State(theorems, workArea, options, primaryStage);
        CLTheorems theoremClickListener = new CLTheorems(state);
        for (Node theorem : theorems.getChildren()) {
            theorem.setOnMouseClicked(theoremClickListener);
        }


        HBox leftButtonBox = new HBox(5);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        HBox rightButtonBox = new HBox(5);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        Button buttonUndo = new Button("Undo Step");
        Button buttonKeeper = new Button("Keep Theorem");
        Button buttonRemoveBrackets = new Button("Remove Brackets");
        Button buttonAddBrackets = new Button("Add Brackets");
        Button buttonClear = new Button("Clear Work Area");

        leftButtonBox.getChildren().addAll(buttonRemoveBrackets, buttonAddBrackets);
        rightButtonBox.getChildren().addAll(buttonUndo, buttonKeeper, buttonClear);

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
                 options.getChildren().clear();
             }
         });

        buttonKeeper.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

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
                if (currSelection instanceof NodeForBrackets && currSelection.getNodeChar().equals(Operators.LPAR)) {
                    if (currSelection.isRoot() ||
                            currSelection.getParent() instanceof NodeForBrackets ||
                            Operators.precedence.get(currSelection.getParent().getNodeChar()) <
                                    Operators.findLowestPrecendence(currSelection.children()[0], Integer.MAX_VALUE) ||
                            (currSelection.getParent()!= null && currSelection.getParent().getNodeChar().equals(currSelection.children()[0].getNodeChar()))) {
                        INode withoutBrackets = currSelection.removeBrackets();
                        ProofStep step = new ProofStep(withoutBrackets.getRoot(), Operators.EQUIVAL + "\t\t{ remove brackets }", state, true, Operators.EQUIVAL);
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
                    ProofStep step = new ProofStep(currentSelection.getRoot(), Operators.EQUIVAL + "\t\t{ add brackets }", state, true, Operators.EQUIVAL);
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

                    // clear current lists
                    theorems.getChildren().clear();
                    workArea.getChildren().clear();
                    options.getChildren().clear();
                    // put new theorems in,
                    theorems.getChildren().addAll(newTheorems);
                    for (Node n : theorems.getChildren()) {
                        n.setOnMouseClicked(new CLTheorems(state));
                    }
                }
                primaryStage.requestFocus();
                state.setCurrSelection(null);
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
                primaryStage.requestFocus();

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
                        ProofStep step = new ProofStep(expression, "\t\t{ start }", state, true, "");
                        workArea.getChildren().add(step);
                        inputField.clear();
                    } else {
                        new AlertMessage("Invalid Input", "Please enter valid input\nEG:\n\t" +
                                "X and ( Y or Z )\n\tX -> Y = ! Y or X\n\t" +
                                "X and ! ( Y and Z )");
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
        grid.add(leftButtonBox, 0, 5, 1, 1);
        grid.add(rightButtonBox, 1, 5, 1, 1);

        Scene scene = new Scene(grid, 1600, 1200);
        scene.getStylesheets().add(TheoremProvingAssistant.class.getResource("Selection.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Theorem Proving Assistant (V 1.1 MEGA)");
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

}

