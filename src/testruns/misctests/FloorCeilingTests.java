package testruns.misctests;

import interfaces.INode;
import parser.Parser;
import trees.BoolTrees;
import trees.FloorCeilingTrees;
import workers.Matcher;

import java.util.Set;

/**
 * Created by joe on 09/01/17.
 */
public class FloorCeilingTests {

    public static void main(String[] args) {

        System.out.println(FloorCeilingTrees.floorX());

        System.out.println("##################################");

        Matcher matcher = new Matcher();
        System.out.println("Matching");
        Parser parser = new Parser(" X or ( Y or Z )");
        INode n = parser.getTree();
        Set<Matcher.Match> matches = matcher.match(n, BoolTrees.orAssoc());
        for (Matcher.Match m : matches) {
            System.out.println(m);
        }

    }
}
