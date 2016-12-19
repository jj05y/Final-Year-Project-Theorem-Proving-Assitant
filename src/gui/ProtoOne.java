package gui;

import gui.core.Theorem;
import gui.listeners.CLTheorem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import trees.Trees;

/**
 * Created by joe on 16/12/16.
 */
public class ProtoOne extends Application {

    private VBox workArea;
    private VBox theorems;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        workArea = new VBox();
        theorems = new VBox();



        theorems.getChildren().add(new Theorem(Trees.absZeroequivXandY()));
        theorems.getChildren().add(new Theorem(Trees.goldenRule()));
        theorems.getChildren().add(new Theorem(Trees.goldenRulePQ()));
        theorems.getChildren().add(new Theorem(Trees.absZero()));
        theorems.getChildren().add(new Theorem(Trees.andOverOr()));


        CLTheorem theoremClickListener = new CLTheorem(workArea);
        for (Node theorem : theorems.getChildren()) {
            theorem.setOnMouseClicked(theoremClickListener);
        }


        grid.add(workArea, 0, 0);
        grid.add(theorems, 2, 0);

        Scene scene = new Scene(grid, 1200, 800);
        scene.getStylesheets().add(Selection.class.getResource("Selection.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();

    }

}

