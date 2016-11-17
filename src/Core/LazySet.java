package Core;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by joe on 14/11/16.
 */
public class LazySet<E> extends AbstractSet<E> {

    private Vector<E> list;

    public LazySet() {
        this.list = new Vector<E>();
    }

    @Override
    public boolean add(E e) {
        if (!list.contains(e)) {
            list.add(e);
            return false;
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public int size() {
        return list.size();
    }
}
