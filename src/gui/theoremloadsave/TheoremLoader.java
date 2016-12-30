package gui.theoremloadsave;

import gui.core.Theorem;
import interfaces.INode;
import javafx.scene.control.Alert;
import parser.Parser;

import java.io.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by joe on 30/12/16.
 */
public class TheoremLoader {


    public List<Theorem> getTheorems(File file) {
        List<Theorem> list = new Vector<>();
        Parser parser;
        int index = 0;

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while ((line = br.readLine()) != null) {
                Boolean isAxiom = line.split(" ")[0].equals("*");
                line = line.substring(2);
                parser = new Parser(line);
                INode expression = parser.getTree();
                if (expression != null) {
                    list.add(new Theorem(expression, index++, isAxiom));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText(line + " skipped due to invalid format.\nInput Examples:\n\tX and ( Y or Z )\n\tX => Y = ! Y or X\n\tX and ! ( Y and Z )");
                    alert.showAndWait();
                }
            }

            br.close();
            fis.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("File Error");
            alert.showAndWait();
        }


        return list;
    }
}
