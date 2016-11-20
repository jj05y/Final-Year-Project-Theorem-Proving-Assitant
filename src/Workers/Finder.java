package Workers;

import Core.LazySet;
import Core.TreeAndSubTree;
import Interfaces.INode;

/**
 * Created by joe on 24/10/16.
 */
public class Finder {

    private Renamer renamer;

    public Finder() {
        renamer = new Renamer();
    }

    //TODO better, going to have to itterate through all tier1 permutaions and return a node and parent pair, THAT subtree with THAT parent
    //infact, a list of them
    public LazySet<TreeAndSubTree> walk(INode root, INode node, INode toFind, LazySet<TreeAndSubTree> foundSoFar) {

        //using copys of tree to rename, they are then forgotten about
        INode copyOfNode = renamer.renameIdsArbitrarily(node);

        INode copyOfToFind = renamer.renameIdsArbitrarily(toFind);


        if (copyOfNode.equals(copyOfToFind)) {
            foundSoFar.add(new TreeAndSubTree(root, node));
        }
        if (node.children()!= null) {
            for (INode n : node.children()){
                walk(root, n, toFind, foundSoFar);
            }
        }
        return foundSoFar;
    }

    public LazySet<TreeAndSubTree> find(INode tree, INode toFind) {

        LazySet<TreeAndSubTree> treesAndSubtrees = new LazySet<>();
        for (INode perm : (new TreePermuter()).permuteJustOneTier(tree)) {
            treesAndSubtrees.addAll(walk(perm, perm, toFind, new LazySet<>()));
        }
        return treesAndSubtrees;
    }


}
