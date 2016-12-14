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
        System.out.println("renamed arbitrarily");
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


        System.out.println("\n#################################################\n");
        //TODO is this ok? I think so, or do we need to be sure that the Z is NEW? what does that even mean?
        System.out.println("Trying a renaming without a value in the lookuptable");
        System.out.println(Trees.XandYandZ());
        System.out.println("renamed with lookUpTable: " + lookupTable);
        System.out.println(renamer.renameIdsWithLookupTable(Trees.XandYandZ(), lookupTable));

        System.out.println("\n#################################################\n");
        System.out.println("Edge case test, just an ID");
        System.out.println(new Identifier('X'));
        System.out.println("renamed with lookUpTable: " + lookupTable);
        System.out.println(renamer.renameIdsWithLookupTable(new Identifier('X'), lookupTable));
        System.out.println();
        System.out.println("Edge case test2, just an ID");
        System.out.println(new Identifier('Z'));
        System.out.println("renamed with lookUpTable: " + lookupTable);
        System.out.println(renamer.renameIdsWithLookupTable(new Identifier('Z'), lookupTable));

    }
}
