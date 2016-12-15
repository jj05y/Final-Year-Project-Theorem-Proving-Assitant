package Gui;

import Interfaces.INode;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Set;

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


        INode expression  = Trees.goldenRule();
        Set<String> validSubStrings = (new TreePermuter()).getListOfValidSubStrings(expression);


        HBox box = new HBox(3);
        for (char c : expression.toString().toCharArray()) {
            if (c != ' ') {
                box.getChildren().add(new Bit(new Text(c + "")));
            }
        }

        for (Node n : box.getChildren()) {
            n.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (((Bit) n).isWhite()) {
                        ((Bit) n).setRed();
                    } else {
                        ((Bit) n).setWhite();
                    }
                    theSelection.setText(isSelectionIsValid(box, validSubStrings));

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


    private String isSelectionIsValid(HBox box, Set<String> validSubStrings) {


        //need to check if only neighbouring boxes clicked
        String validator = "";
        for (Node n : box.getChildren()) {
            if (((Bit) n).isSelected()) {
                validator+="1";
            } else {
                validator+="0";
            }
        }
        System.out.println(validator);
        if (validator.matches(".*1++0++1++.*")) {
            for (Node n : box.getChildren()) {
                if (((Bit) n).isSelected()) {
                    ((Bit) n).setRed();
                }
            }
            return "";
        }

        //at his point we know only one block is matched, lets grab that string.
        String theSelection = "";
        for (Node n : box.getChildren()) {
            if (((Bit) n).isSelected()) {
                theSelection += ((Bit) n).getText() + " ";
            }
        }
        theSelection = theSelection.trim();
        System.out.println(theSelection);
        if (validSubStrings.contains(theSelection)) {
            for (Node n : box.getChildren()) {
                if (((Bit) n).isSelected()) {
                    ((Bit) n).setGreen();
                }
            }
            return theSelection;
        } else {
            for (Node n : box.getChildren()) {
                if (((Bit) n).isSelected()) {
                    ((Bit) n).setRed();
                }
            }
            return "";
        }
    }

}

class Bit extends StackPane {

    private Text text;
    private INode node;

    private static Background red = new Background(new BackgroundFill(Color.FIREBRICK, new CornerRadii(7),Insets.EMPTY));
    private static Background green = new Background(new BackgroundFill(Color.FORESTGREEN, new CornerRadii(7),Insets.EMPTY));
    private static Background white = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(7),Insets.EMPTY));



    public Bit(Text text) {
        super(text);
        this.setBackground(white);
        this.text = text;
        this.node = null;
    }

    public void setRed() {
        this.setBackground(red);
    }


    public void setGreen() {
        this.setBackground(green);
    }


    public void setWhite() {
        this.setBackground(white);
    }

    public boolean isWhite() {
        return this.getBackground() == white;
    }

    public boolean isRed() {
        return this.getBackground() == red;
    }

    public boolean isGreen() {
        return this.getBackground() == green;
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public INode getNode() {
        return node;
    }

    public void setNode(INode node) {
        this.node = node;
    }

    public boolean isSelected() {
        return isRed() || isGreen();
    }
}
