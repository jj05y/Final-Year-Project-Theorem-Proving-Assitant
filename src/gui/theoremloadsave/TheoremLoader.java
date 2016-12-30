package gui.theoremloadsave;

import gui.core.Theorem;
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
                parser = new Parser(line);
                list.add(new Theorem(parser.getTree(), index++));
            }

            br.close();
            fis.close();
        } catch (IOException e) {
            System.out.println("broken file loader");
        }


        return list;
    }
}
