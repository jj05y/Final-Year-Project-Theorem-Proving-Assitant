package gui.theoremloadsave;

import com.google.gson.Gson;
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


            Gson gson = new Gson();
            for (Node t : theorems) {
                INode n = ((Theorem) t).getRoot();
                SavableTheorem st = new SavableTheorem(n.toPlainText(), ((Theorem) t).getDerivation(), ((Theorem) t).isAxiom());
                String json = gson.toJson(st);
                fw.write(json + "\n");
            }


            fw.flush();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
