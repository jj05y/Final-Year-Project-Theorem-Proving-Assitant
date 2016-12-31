package gui.theoremloadsave;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import gui.core.AlertMessage;
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
            Gson gson = new Gson();
            while ((line = br.readLine()) != null) {
                SavableTheorem st;
                try {
                    st = gson.fromJson(line, SavableTheorem.class);
                } catch (JsonSyntaxException e) {
                    st = null;
                }
                Boolean isAxiom;
                INode expression;
                String derivation;
                if (st == null) {
                    isAxiom = line.split(" ")[0].equals("*");
                    line = line.substring(2);
                    parser = new Parser(line);
                    expression = parser.getTree();
                    derivation = "No derivation has been defined for this theorem.";
                } else {
                    isAxiom = st.isAxiom();
                    parser = new Parser(st.getPlainTextTheorem());
                    expression = parser.getTree();
                    derivation = st.getDerivation();
                }
                if (expression != null) {
                    list.add(new Theorem(expression, index++, isAxiom, derivation));
                } else {
                    new AlertMessage("Invalid Syntax", line + " skipped due to invalid format.\nInput Examples:\n\t* X and ( Y or Z )\n\t* X => Y = ! Y or X\n\t- X and ! ( Y and Z )");
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
