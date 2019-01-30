package treap;

import java.lang.reflect.Array;
import java.util.*;

public class Treap<T extends Comparable<T>> {
    private T x;
    private int y;

    private Treap<T> Left;
    private Treap<T> Right;

    private int Size;

    private int sizeOfSubTreap(Treap<T> treap) {
        if (treap == null) return 0;
        else return treap.Size;
    }

    private void recalc() {
        Size = sizeOfSubTreap(Left) + sizeOfSubTreap(Right) + 1;
    }

    private Treap(T x, int y, Treap<T> left, Treap<T> right) {
        this.x = x;
        this.y = y;
        this.Left = left;
        this.Right = right;
    }

    private Treap(T x, int y) {
        this.x = x;
        this.y = y;
        Size = 1;
    }

    public Treap() {

    }

    public Treap(T x) {
        Random random = new Random();
        this.x = x;
        this.y = random.nextInt(100);
        Size = 1;
        this.Left = null;
        this.Right = null;
    }

    public Treap<T> merge(Treap<T> treap1, Treap<T> treap2) {
        if (treap1 == null) return treap2;
        if (treap2 == null) return treap1;
        Treap<T> treap;
        if (treap1.y > treap2.y) {
            Treap<T> treapR = merge(treap1.Right, treap2);
            treap = new Treap<>(treap1.x, treap1.y, treap1.Left, treapR);
        } else {
            Treap<T> treapL = merge(treap1, treap2.Left);
            treap = new Treap<>(treap2.x, treap2.y, treapL, treap2.Right);
        }
        treap.recalc();
        return treap;
    }


    private Treap<T>[] pairTreap = new Treap[2];

    private void setPairTreapEmpty() {
        Treap<T>[] tr = new Treap[2];
        tr[0] = null;
        tr[1] = null;
        pairTreap = tr;
    }

    public Treap<T>[] split(T key, Treap<T> treap) {
        Treap<T>[] result;
        result = split1(key, treap);
        setPairTreapEmpty();
        return result;
    }

    private Treap<T>[] split1(T key, Treap<T> treap) {
        if (treap == null) {
            pairTreap[0] = null;
            pairTreap[1] = null;
            return pairTreap;
        } else {
            if (key.compareTo(treap.x) > 0) {
                pairTreap = split1(key, treap.Right);
                treap.Right = pairTreap[0];
                pairTreap[0] = treap;
                if (pairTreap[1] != null) {
                    pairTreap[0].recalc();
                    pairTreap[1].recalc();
                }
                return pairTreap;
            } else {
                pairTreap = split1(key, treap.Left);
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

    public void build(TreeMap<T, Integer> nodes) {
        ArrayDeque<Treap<T>> queueOfVertex = new ArrayDeque<>();
        for (Map.Entry<T, Integer> item : nodes.entrySet()) {
            Treap<T> treap = new Treap<>(item.getKey(), item.getValue());
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
        Treap<T> treap = queueOfVertex.peek();
        this.x = treap.x;
        this.y = treap.y;
        this.Left = treap.Left;
        this.Right = treap.Right;
    }

    public void add(T node) {
        Treap<T>[] pair = split(node, this);
        Treap<T> oneVertex = new Treap<>(node);
        Treap<T> treap = merge(merge(pair[0], oneVertex), pair[1]);
        this.x = treap.x;
        this.y = treap.y;
        Treap<T> treapR = treap.Right != null ? new Treap<>(treap.Right.x, treap.Right.y, treap.Right.Left, treap.Right.Right)
                : null;
        Treap<T> treapL = treap.Left != null ? new Treap<>(treap.Left.x, treap.Left.y, treap.Left.Left, treap.Left.Right)
                : null;
        this.Right = treapR;
        this.Left = treapL;
    }

    public void remove(T node) {
        Treap<T>[] tr = split(node, this);
        Treap<T>[] tr1 = split2(node, tr[1]);
        Treap<T> treap = merge(tr[0], tr1[1]);
        this.x = treap.x;
        this.y = treap.y;
        Treap<T> treapR = treap.Right != null ? new Treap<>(treap.Right.x, treap.Right.y, treap.Right.Left, treap.Right.Right)
                : null;
        Treap<T> treapL = treap.Left != null ? new Treap<>(treap.Left.x, treap.Left.y, treap.Left.Left, treap.Left.Right)
                : null;
        this.Right = treapR;
        this.Left = treapL;
    }

    private Treap<T>[] split2(T key, Treap<T> treap) {
        Treap<T>[] result;
        result = split22(key, treap);
        setPairTreapEmpty();
        return result;
    }

    private Treap<T>[] split22(T key, Treap<T> treap) {
        if (treap == null) {
            pairTreap[0] = null;
            pairTreap[1] = null;
            return pairTreap;
        } else {
            if (key.compareTo(treap.x) >= 0) {
                pairTreap = split22(key, treap.Right);
                treap.Right = pairTreap[0];
                pairTreap[0] = treap;
                if (pairTreap[1] != null) {
                    pairTreap[0].recalc();
                    pairTreap[1].recalc();
                }
                return pairTreap;
            } else {
                pairTreap = split22(key, treap.Left);
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

    public T kElement(int k) {
        Treap<T> current = this;
        while (current != null) {
            int sizeLeft = sizeOfSubTreap(current.Left);
            if (sizeLeft == k) return current.x;
            current = sizeLeft > k ? current.Left : current.Right;
            if (sizeLeft < k) k -= sizeLeft + 1;
        }
        return null;
    }


    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", left=" + Left +
                ", right=" + Right +
                '}';
    }

}
