package gui.theoremloadsave;

import interfaces.INode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by joe on 30/12/16.
 */
public class TheoremSaver {

    public void saveTheorems(File file, List<String> theorems) {

        try {

            FileWriter fw = new FileWriter(file);

            for (String s : theorems) {
                fw.write(s + "\n");
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
