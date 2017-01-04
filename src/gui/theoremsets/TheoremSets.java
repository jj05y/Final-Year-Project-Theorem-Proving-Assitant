package gui.theoremsets;

import gui.core.Theorem;
import trees.BoolTrees;

import java.util.List;
import java.util.Vector;

/**
 * Created by joe on 19/12/16.
 */
public class TheoremSets {

    public List<Theorem> getTheoremSet1() {
        List<Theorem> list = new Vector<>();
        list.add(new Theorem(BoolTrees.absZeroequivXandY(),0, true,null));
        list.add(new Theorem(BoolTrees.goldenRule(),1,true,null));
        list.add(new Theorem(BoolTrees.goldenRulePQ(),2,true,null));
        list.add(new Theorem(BoolTrees.absZero(),3,true,null));
        list.add(new Theorem(BoolTrees.andOverOr(),4,true,null));
        list.add(new Theorem(BoolTrees.weirdBrokenabsZeroequivXandY(),5,true,null));
        return list;
    }


    public List<Theorem> getTheoremSet2() {
        List<Theorem> list = new Vector<>();
        list.add(new Theorem(BoolTrees.equivAssoc(),0,true,null));
        list.add(new Theorem(BoolTrees.equivSem(),1,true,null));
        list.add(new Theorem(BoolTrees.equivId(),2,true,null));
        list.add(new Theorem(BoolTrees.equivReflex(),3,true,null));
        list.add(new Theorem(BoolTrees.t(),4,true,null));
        list.add(new Theorem(BoolTrees.orSym(),5,true,null));
        list.add(new Theorem(BoolTrees.orAssoc(),6,true,null));
        list.add(new Theorem(BoolTrees.orIdem(),7,true,null));
        list.add(new Theorem(BoolTrees.orOverEquiv(),8,true,null));
    //    list.add(new Theorem(BoolTrees.XandYimplX(),9,true,null));
        return list;
    }
}
