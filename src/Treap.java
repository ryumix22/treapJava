import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Treap<T> implements Comparable<Object> {
    private T x;
    private int y;

    private Treap Left;
    private Treap Right;

    public Treap(T x, int y, Treap left, Treap right) {
        this.x = x;
        this.y = y;
        Left = left;
        Right = right;
    }

    public Treap merge(Treap<T> l, Treap<T> r){
        if (l == null) return r;
        if (r == null) return l;

        if (l.y > r.y)
        {
            Treap newR = merge(l.Right, r);
            return new Treap(l.x, l.y, l.Left, newR);
        } else {
            Treap newL = merge(l, r.Left);
            return new Treap(r.x, r.y, newL, r.Right);
        }
    }

    public Pair<Treap, Treap> split(T key, Treap l, Treap r){
        Treap newTree = null;
        if (this.compareTo(key) <= 0) {
            if (Right == null) r = null;
            else Right.split(key, newTree, r);
            //l = new Treap(this.x, y, Left, newTree);
        } else {
            if (Left == null) l = null;
            else Left.split(key, l, newTree);
            //r = new Treap(this.x, y, newTree, Right);
        }
        return null;
    }

    /*
    public Treap add(T x) {
        Treap left = null;
        Treap right = null;
        split(x, left, right);
    }
    */
    public void build(Set<T> elements) {

    }


    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }
}
