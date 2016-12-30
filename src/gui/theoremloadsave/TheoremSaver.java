package gui.theoremloadsave;

import gui.core.Theorem;
import interfaces.INode;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Created by joe on 30/12/16.
 */
public class TheoremSaver {

    public void saveTheorems(File file, ObservableList<Node> theorems) {

        try {

            FileWriter fw = new FileWriter(file);

            List<String> theoremStrings = new Vector<>();
            for (Node n : theorems) {
                theoremStrings.add((((Theorem) n).isAxiom() ? "* " : "- ")  + ((Theorem) n).getRoot().toPlainText());
            }

            for (String s : theoremStrings) {
                fw.write(s + "\n");
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
