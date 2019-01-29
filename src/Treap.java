
import java.util.*;

public class Treap<T> implements Comparator<Object> {
    private T x;
    private int y;

    private Treap<T> Left;
    private Treap<T> Right;

    public Treap(T x, int y, Treap<T> left, Treap<T> right) {
        this.x = x;
        this.y = y;
        this.Left = left;
        this.Right = right;
    }

    public Treap() {
    }

    public Treap<T> merge(Treap<T> treap1, Treap<T> treap2) {
        if (treap1 == null) return treap2;
        if (treap2 == null) return treap1;
        else if (treap1.y > treap2.y) {
            treap1.Right = merge(treap1.Right, treap2);
            return treap1;
        } else {
            treap2.Left = merge(treap1, treap2.Left);
            return treap2;
        }
    }

    private Treap<T>[] pairTreap = new Treap[2];

    public Treap<T>[] split(T key) {
        if (compare(key, this.x) > 0) {
            pairTreap = this.Right.split(key);
            this.Right = pairTreap[0];
            pairTreap[0] = this;
            return pairTreap;
        } else {
            pairTreap = this.Left.split(key);
            this.Left = pairTreap[1];
            pairTreap[1] = this;
            return pairTreap;
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
        Random random = new Random();
        Treap<T>[] pair = this.split(vertex);
        Treap<T> oneVertex = new Treap<>(vertex, random.nextInt(100), null, null);
        Treap<T> tr = merge(merge(pair[0], oneVertex), pair[1]);
        this.x = tr.x;
        this.y = tr.y;
        this.Left = tr.Left;
        this.Right = tr.Right;

    }

    public void remove(T vertex) {
        Treap<T>[] tr = split(vertex);
        Treap<T>[] tr1 = tr[0].split(vertex);
        Treap<T> newTreap = merge(tr[0], tr[1]);
        this.x = newTreap.x;
        this.y = newTreap.y;
        this.Left = newTreap.Left;
        this.Right = newTreap.Right;
    }

    public static void main(String[] args) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Treap<Integer> treap = new Treap<>();
        map.put(5, 4);
        map.put(21, 7);
        map.put(12, 12);
        map.put(8, 1);
        map.put(33, 13);
        map.put(10, 6);
        map.put(16, 3);
        map.put(2, 2);
        treap.build(map);
        treap.toString();
    }

    /*
    @Override
    public String toString() {
        return "";
    }
    */

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
