package gui.theoremsets;

import gui.core.Theorem;
import trees.*;

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
        list.add(new Theorem(BoolTrees.equivSemBalanced(),1,true,null));
        list.add(new Theorem(BoolTrees.equivId(),2,true,null));
        list.add(new Theorem(BoolTrees.equivReflex(),3,true,null));
        list.add(new Theorem(BoolTrees.t(),4,true,null));
        list.add(new Theorem(BoolTrees.orSym(),5,true,null));
        list.add(new Theorem(BoolTrees.orAssoc(),6,true,null));
        list.add(new Theorem(BoolTrees.orIdem(),7,true,null));
        list.add(new Theorem(BoolTrees.orOverEquiv(),8,true,null));
        list.add(new Theorem(QuantTrees.QuantEquivX(),9,true,null));
        list.add(new Theorem(QuantTrees.quantEquivQuant(),10,true,null));
        list.add(new Theorem(BoolTrees.XandYimplX(),11,true,null));
        list.add(new Theorem(NumTrees.xGTEy(),12,true,null));
        list.add(new Theorem(NumTrees.xLTy(),13,true,null));

        return list;
    }
    public List<Theorem> getTheoremSet3() {
        List<Theorem> list = new Vector<>();
        list.add(new Theorem(BoolTrees.equivSemBalanced(),1,true,null));
        list.add(new Theorem(BoolTrees.equivId(),2,true,null));
        list.add(new Theorem(BoolTrees.orSym(),3,true,null));
        list.add(new Theorem(BoolTrees.orAssoc(),4,true,null));
        list.add(new Theorem(BoolTrees.orIdem(),5,true,null));
        list.add(new Theorem(BoolTrees.orOverEquiv(),6,true,null));
        list.add(new Theorem(BoolTrees.XandYimplX(),7,true,null));
        list.add(new Theorem(BoolTrees.impToOr(),8,true,null));
        list.add(new Theorem(QuantTrees.XorQuantEquivQuant(), 9, true, null));
        list.add(new Theorem(NumTrees.floorDef(), 10, true, null));
        list.add(new Theorem(NumTrees.ceilingDef(), 11, true, null));
        list.add(new Theorem(LatticeTrees.defUp(), 12, true, null));
        list.add(new Theorem(LatticeTrees.defDown(), 13, true, null));
        list.add(new Theorem(LatticeTrees.wDef(), 14, true, null));


        return list;
    }
}
