package TestRuns;

import Core.LazySet;
import Core.TreeAndSubTree;
import Interfaces.INode;
import Trees.Trees;
import Workers.Finder;

import java.util.List;

/**
 * Created by joe on 19/11/16.
 */
public class FinderTest {

    public static void main (String[] args){
        Finder finder = new Finder();
        LazySet<TreeAndSubTree> treeAndSubTrees = finder.find(Trees.goldenRule(), Trees.XandY());
        for (TreeAndSubTree tst : treeAndSubTrees) {
            System.out.println(tst);
        }
        System.out.println("**********************************************************");
        LazySet<TreeAndSubTree> treeAndSubTrees2 = finder.find(Trees.goldenRulePQ(), Trees.XandY());
        for (TreeAndSubTree tst : treeAndSubTrees2) {
            System.out.println(tst);
        }
        System.out.println("**********************************************************");
    }
}
