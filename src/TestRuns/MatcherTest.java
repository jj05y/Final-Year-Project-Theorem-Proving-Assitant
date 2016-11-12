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

        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + Trees.XandYandZ() + " in " + Trees.goldenRule());
        for (Matcher.Match m : matcher.match(Trees.XandYandZ(), Trees.goldenRule())){
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + Trees.XandYorZ() + " in " + Trees.goldenRule());
        for (Matcher.Match m : matcher.match(Trees.XandYorZ(), Trees.goldenRule())){
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + Trees.ZandW() + " in " + Trees.goldenRule());
        for (Matcher.Match m : matcher.match(Trees.ZandW(), Trees.goldenRule())){
            System.out.println(m);
        }


        System.out.println("\n*******************************************************************\n");
        System.out.println("Matches for " + Trees.YequivX() + " in " + Trees.goldenRule());
        for (Matcher.Match m : matcher.match(Trees.YequivX(), Trees.goldenRule())){
            System.out.println(m);
        }




    }

}
