package gui.theoremsets;

import gui.core.Theorem;
import trees.Trees;

import java.util.List;
import java.util.Vector;

/**
 * Created by joe on 19/12/16.
 */
public class TheoremSet1 {

    public List<Theorem> getTheoremSet1() {
        List<Theorem> list = new Vector<>();
        list.add(new Theorem(Trees.absZeroequivXandY()));
        list.add(new Theorem(Trees.goldenRule()));
        list.add(new Theorem(Trees.goldenRulePQ()));
        list.add(new Theorem(Trees.absZero()));
        list.add(new Theorem(Trees.andOverOr()));
        list.add(new Theorem(Trees.weirdBrokenabsZeroequivXandY()));
        return list;
    }
}
