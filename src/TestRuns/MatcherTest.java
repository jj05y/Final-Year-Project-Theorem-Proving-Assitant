package TestRuns;

import Interfaces.INode;
import Trees.Trees;
import Workers.Matcher;

/**
 * Created by joe on 12/11/16.
 */
public class MatcherTest {

    public static void main(String[] args) {
        Matcher matcher = new Matcher();

        for (Matcher.Match m : matcher.match(Trees.XandYandZ(), Trees.goldenRule())){
            System.out.println(m);
        }

    }

}
