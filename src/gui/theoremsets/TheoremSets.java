package gui.theoremsets;

import gui.core.Theorem;
import trees.Trees;

import java.util.List;
import java.util.Vector;

/**
 * Created by joe on 19/12/16.
 */
public class TheoremSets {

    public List<Theorem> getTheoremSet1() {
        List<Theorem> list = new Vector<>();
        list.add(new Theorem(Trees.absZeroequivXandY(),0));
        list.add(new Theorem(Trees.goldenRule(),1));
        list.add(new Theorem(Trees.goldenRulePQ(),2));
        list.add(new Theorem(Trees.absZero(),3));
        list.add(new Theorem(Trees.andOverOr(),4));
        list.add(new Theorem(Trees.weirdBrokenabsZeroequivXandY(),5));
        return list;
    }


    public List<Theorem> getTheoremSet2() {
        List<Theorem> list = new Vector<>();
        list.add(new Theorem(Trees.equivAssoc(),0));
        list.add(new Theorem(Trees.equivSem(),1));
        list.add(new Theorem(Trees.equivId(),2));
        list.add(new Theorem(Trees.equivReflex(),3));
        list.add(new Theorem(Trees.t(),4));
        list.add(new Theorem(Trees.orSym(),5));
        list.add(new Theorem(Trees.orAssoc(),6));
        list.add(new Theorem(Trees.orIdem(),7));
        list.add(new Theorem(Trees.orOverEquiv(),8));
        return list;
    }
}
