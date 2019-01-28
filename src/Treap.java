
import java.util.Comparator;
import java.util.Random;
import java.util.Set;

public class Treap<T> implements Comparator<Object> {
    private T x;
    private int y;

    private Treap<T> Left;
    private Treap<T> Right;

    public Treap(T x, int y, Treap<T> left, Treap<T> right) {
        this.x = x;
        this.y = y;
        Left = left;
        Right = right;
    }


    public Treap<T> merge(Treap<T> treap1, Treap<T> treap2){
        if (treap1 == null) return treap2;
        if (treap2 == null) return treap1;
        else
        if (treap1.y > treap2.y)
        {
            treap1.Right = merge(treap1.Right, treap2);
            return treap1;
        } else {
            treap2.Left = merge(treap1, treap2.Left);
            return treap2;
        }
    }

    private Treap<T>[] pairTreap = new Treap[2];

    public Treap<T>[] split(T key, Treap<T> treap) {
        if (treap == null) {
            pairTreap[0] = null;
            pairTreap[1] = null;
            return pairTreap;
        } else if (compare(key, treap.x) > 0) {
            pairTreap = split(key, treap.Right);
            treap.Right = pairTreap[0];
            pairTreap[0] = treap;
            return pairTreap;
        } else {
            pairTreap = split(key, treap.Left);
            treap.Left = pairTreap[1];
            pairTreap[1] = treap;
            return pairTreap;
        }
    }
    public void build(Set<T> elements) {

    }

    public void add(T vertex, Treap<T> treap) {
        Random random = new Random();
        Treap<T>[] pair = split(vertex, treap);
        Treap<T> oneVertex = new Treap<>(vertex, random.nextInt(100), null, null);
        Treap<T> tr = merge(merge(pair[0], oneVertex), pair[1]);
        this.x = tr.x;
        this.y = tr.y;
        this.Left = tr.Left;
        this.Right = tr.Right;

    }

    public void remove(T vertex, Treap<T> treap) {

    }

    public static void main(String[] args) {
        Integer int1 = 5;
        Integer int2 = 4;
        System.out.println(int1.compareTo(int2));
    }


    //@Override
    //public int compareTo(@NotNull Object o) {
      //  return 0;
    //}

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
