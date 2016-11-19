package TestRuns;

import Interfaces.INode;
import Terminals.Identifier;
import Trees.Trees;
import Workers.Renamer;

import java.util.HashMap;

/**
 * Created by joe on 19/11/16.
 */
public class RenamerTest {
    public static void main (String[] args) {

        Renamer renamer = new Renamer();

        INode gr = Trees.goldenRule();
        System.out.println(gr);
        System.out.println("renamed");
        System.out.println(renamer.renameIdsArbitrarily(gr));

        System.out.println("\n\nTest rename with map");
        HashMap<Character, INode> lookupTable = new HashMap<>();
        lookupTable.put('X', new Identifier('A'));
        lookupTable.put('Y', new Identifier('B'));
        System.out.println(Trees.XandY());
        System.out.println("renamed with lookUpTable: " + lookupTable);
        System.out.println(renamer.renameIdsWithLookupTable(Trees.XandY(), lookupTable));

        System.out.println("\n\nTest rename with map more complex");
        System.out.println(Trees.goldenRule());
        System.out.println("renamed with lookUpTable: " + lookupTable);
        System.out.println(renamer.renameIdsWithLookupTable(Trees.goldenRule(), lookupTable));
    }
}
