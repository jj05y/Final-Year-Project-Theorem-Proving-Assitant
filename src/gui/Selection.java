package gui;

import gui.core.Bit;
import gui.listeners.CLSelectionCycler;
import gui.workers.Associator;
import gui.workers.BitBoxMaker;
import interfaces.INode;
import nodes.NodeForBrackets;
import terminals.Identifier;
import trees.Trees;
import workers.TreePermuter;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.List;

/**
 * Created by joe on 15/12/16.
 */
public class Selection extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setTitle("Selection");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text info = new Text("Make a selection by clicking on characters");
        grid.add(info, 0, 0,2,1);

        Text yourSelection = new Text("Selection: ");
        grid.add(yourSelection, 0,2);

        Text theSelection = new Text();
        theSelection.setTextAlignment(TextAlignment.LEFT);
        grid.add(theSelection,1,2);

        Button resetButton = new Button("Reset");
        grid.add(resetButton,1,3);


        INode expression  = Trees.XandYequivalXandYequivalZ();
        List<INode> treesForExpression = (new TreePermuter()).getTreesForExpression(expression);

        HBox box = (new BitBoxMaker()).getBitBox(expression);

        Associator associator = new Associator();
        //walk trees and associate bits and nodes
        for (INode root : treesForExpression) {
            associator.associate(root, box.getChildren().iterator());
        }

        CLSelectionCycler selectionCycler = new CLSelectionCycler(box, theSelection);
        for (Node n : box.getChildren()) {
            n.setOnMousePressed(selectionCycler);
        }

        //this is fine to be local,
        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Node n : box.getChildren()) {
                    ((Bit) n).setWhite();
                }
                theSelection.setText("");
            }
        });

        grid.add(box, 0, 1);

        Scene scene = new Scene(grid, 1600,800);
        scene.getStylesheets().add(Selection.class.getResource("Selection.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();


    }


    /*
        System.out.println("Testing bit knows node");
        Bit foo = (Bit) box.getChildren().get(5);
        for (INode n : foo.getNodesInTree()) {
            System.out.println(n);
        }
        System.out.println("Testing node knows bit");
        INode bar = treesForExpression.get(0);
        System.out.println(bar.getBit().getText());
*/



}

