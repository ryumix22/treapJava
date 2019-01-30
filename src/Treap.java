
import java.util.*;

public class Treap<T> implements Comparator<Object> {
    private T x;
    private int y;

    private Treap<T> Left;
    private Treap<T> Right;

    private int Size;

    public static int sizeOfSubTreap(Treap treap) {
        if (treap == null) return 0;
        else return treap.Size;
    }

    public void recalc() {
        Size = sizeOfSubTreap(Left) + sizeOfSubTreap(Right) + 1;
    }

    private Treap(T x, int y, Treap<T> left, Treap<T> right) {
        this.x = x;
        this.y = y;
        this.Left = left;
        this.Right = right;
    }

    public Treap(T x) {
        Random random = new Random();
        this.x = x;
        this.y = random.nextInt(10000);
        Size = 1;
        this.Left = null;
        this.Right = null;
    }

    public Treap<T> merge(Treap<T> treap1, Treap<T> treap2) {
        if (treap1 == null) return treap2;
        if (treap2 == null) return treap1;
        Treap<T> treap;
        if (treap1.y > treap2.y) {
            treap1.Right = merge(treap1.Right, treap2);
            treap = treap1;
        } else {
            treap2.Left = merge(treap1, treap2.Left);
            treap = treap2;
        }
        treap.recalc();
        return treap;
    }

    private Treap<T>[] pairTreap = new Treap[2];

    public Treap<T>[] split(T key, Treap<T> treap) {
        if (treap == null) {
            pairTreap[0] = null;
            pairTreap[1] = null;
            return pairTreap;
        } else {
            if (compare(key, treap.x) > 0) {
                pairTreap = split(key, treap.Right);
                treap.Right = pairTreap[0];
                pairTreap[0] = treap;
                if (pairTreap[1] != null) {
                    pairTreap[0].recalc();
                    pairTreap[1].recalc();
                }
                return pairTreap;
            } else {
                pairTreap = split(key, treap.Left);
                treap.Left = pairTreap[1];
                pairTreap[1] = treap;
                if (pairTreap[0] != null) {
                    pairTreap[0].recalc();
                    pairTreap[1].recalc();
                }
                return pairTreap;
            }
        }
    }

    private int log2(int x) {
        double res = Math.log(x) / Math.log(2);
        return (int) Math.ceil(res);
    }

    public Treap<T> build(TreeMap<T, Integer> vertices) {
        ArrayDeque<Treap<T>> queueOfVertex = new ArrayDeque<>();
        for (Map.Entry<T, Integer> item : vertices.entrySet()) {
            Treap<T> treap = new Treap<>(item.getKey(), item.getValue(), null, null);
            queueOfVertex.addLast(treap);
        }
        int a = queueOfVertex.size();
        for (int i = 0; i < log2(a); i++) {
            int b = queueOfVertex.size();
            if (b % 2 == 0) {
                for (int j = 0; j < b / 2; j++) {
                    Treap<T> subTreap = merge(queueOfVertex.removeFirst(), queueOfVertex.removeFirst());
                    queueOfVertex.addLast(subTreap);
                }
            } else {
                for (int k = 0; k < (b / 2) + 1; k++) {
                    if (k == b / 2) queueOfVertex.addLast(queueOfVertex.removeFirst());
                    else {
                        Treap<T> subTreap = merge(queueOfVertex.removeFirst(), queueOfVertex.removeFirst());
                        queueOfVertex.addLast(subTreap);
                    }
                }
            }
        }
        return queueOfVertex.peek();
    }

    public void add(T vertex) {
        Treap<T>[] pair = split(vertex, this);
        Treap<T> oneVertex = new Treap<>(vertex);
        Treap<T> tr = merge(merge(pair[0], oneVertex), pair[1]);
        this.x = tr.x;
        this.y = tr.y;
        this.Left = tr.Left;
        this.Right = tr.Right;
    }

    public void remove(T vertex) {
        Treap<T>[] tr = split(vertex, this);
        Treap<T>[] tr1 = tr[0].split(vertex, this);
        Treap<T> newTreap = merge(tr[0], tr[1]);
        this.x = newTreap.x;
        this.y = newTreap.y;
        this.Left = newTreap.Left;
        this.Right = newTreap.Right;
    }


    public static void main(String[] args) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Treap<Integer> treap = new Treap<>(5);
        treap.add(10);
        System.out.println(sizeOfSubTreap(treap));
    }

    /*
    @Override
    public String toString() {
        return "";
    }
    */

    //нужно исправить
    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
