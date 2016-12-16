package Gui;

import Gui.Core.Bit;
import Interfaces.INode;
import Nodes.NodeForBrackets;
import Terminals.Identifier;
import Trees.Trees;
import Workers.TreePermuter;
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

        //SETUP --------------------------------------------------------------------------------------
        INode expression  = Trees.weirdBrokenabsZeroequivXandY();
        List<INode> treesForExpression = (new TreePermuter()).permuteJustOneTier(expression);

        for (int i = 0; i < treesForExpression.size(); i++) {
            if (!treesForExpression.get(i).toString().equals(expression.toString())) {
                treesForExpression.remove(i--);
            }
        }

        //TODO this
        //this is going to have to be changed to an in order traversal of every tree in trees for expression,
        //yeah, and create the bits on the first one, and then add to list or node on following ones,
        HBox box = new HBox(3);
        for (char c : expression.toString().toCharArray()) {
            if (c != ' ') {
                box.getChildren().add(new Bit(new Text(c + "")));
            }
        }

        //walk trees and associate bits and nodes
        for (INode root : treesForExpression) {
            walkAndAssociateBitsAndNodes(root, box.getChildren().iterator());
        }

        //need to test the above :/
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
        //ok, i think it worked :/

        //node the bit knows the node and the node knows the bit :O

        //END SETUP ------------------------------------------------------------------------------------

        for (Node n : box.getChildren()) {
            n.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n2 : box.getChildren()) {
                        ((Bit) n2).setWhite();
                    }
                    Bit clicked = (Bit) event.getSource();
                    String selection = clicked.cycleAndGetSelection();
                    theSelection.setText(selection);
                }
            });
        }


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

        Scene scene = new Scene(grid);
        scene.getStylesheets().add(Selection.class.getResource("Selection.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();


    }

    private void walkAndAssociateBitsAndNodes(INode node, Iterator<Node> iterator) {


        //a bit is a char in the GUI
        if (node instanceof Identifier) { //a leaf
            Bit b;
            while ((b = (Bit) iterator.next()).getText().matches("[()]"));
            if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
            node.setBit(b);
            return;
        }

        if (node instanceof NodeForBrackets) {
            node=node.children()[0];
        }

        walkAndAssociateBitsAndNodes(node.children()[0], iterator);
        Bit b;
        while ((b = (Bit) iterator.next()).getText().matches("[()]"));
        if (!b.getNodesInTree().contains(node)) b.getNodesInTree().add(node);
        node.setBit(b);
        if (node.children().length>1) walkAndAssociateBitsAndNodes(node.children()[1], iterator);
    }



}

