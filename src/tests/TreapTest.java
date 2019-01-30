package tests;

import org.junit.jupiter.api.Test;
import treap.Treap;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class TreapTest extends Treap {

    @Test
    void build() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(5, 10);
        map.put(8, 20);
        map.put(11, 13);
        map.put(2, 4);
        map.put(6, 6);
        map.put(13, 7);
        map.put(10, 9);
        Treap<Integer> treap = new Treap<>();
        treap.build(map);
        TreeMap<String, Integer> map2 = new TreeMap<>();
        map2.put("a", 7);
        map2.put("zr", 4);
        map2.put("b", 10);
        map2.put("sr", 15);
        map2.put("yy", 11);
        map2.put("ssd", 5);
        map2.put("as", 12);
        map2.put("pp", 2);
        Treap<String> treap2 = new Treap<>();
        treap2.build(map2);
        assertEquals(treap.toString(), "Node{x=8, y=20, left=Node{x=5, y=10, left=Node{x=2, y=4, left=null, right=null}, right=Node{x=6, y=6, left=null, right=null}}, right=Node{x=11, y=13, left=Node{x=10, y=9, left=null, right=null}, right=Node{x=13, y=7, left=null, right=null}}}");
        assertEquals(treap2.toString(), "Node{x=sr, y=15, left=Node{x=as, y=12, left=Node{x=a, y=7, left=null, right=null}, right=Node{x=b, y=10, left=null, right=Node{x=pp, y=2, left=null, right=null}}}, right=Node{x=yy, y=11, left=Node{x=ssd, y=5, left=null, right=null}, right=Node{x=zr, y=4, left=null, right=null}}}");
    }

    @Test
    void add() {
        Treap<Integer> treap = new Treap<>(6);
        treap.add(8);
        treap.add(10);
        treap.add(21);
        treap.add(17);
        treap.add(14);
        treap.add(3);
        treap.add(1);
        assertEquals(treap.toString(), treap.toString());
    }

    @Test
    void merge() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(5, 10);
        map.put(8, 20);
        map.put(11, 13);
        Treap<Integer> treap = new Treap<>();
        treap.build(map);
        TreeMap<Integer, Integer> map1 = new TreeMap<>();
        map1.put(1, 4);
        map1.put(4, 6);
        map1.put(3, 7);
        map1.put(2, 9);
        Treap<Integer> treap1 = new Treap<>();
        treap1.build(map1);
        Treap<Integer> treapRes = merge(treap1, treap);
        assertEquals(treapRes.toString(), "Node{x=8, y=20, left=Node{x=5, y=10, left=Node{x=2, y=9, left=Node{x=1, y=4, left=null, right=null}, right=Node{x=3, y=7, left=null, right=Node{x=4, y=6, left=null, right=null}}}, right=null}, right=Node{x=11, y=13, left=null, right=null}}");
    }

    @Test
    void split() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(5, 10);
        map.put(8, 20);
        map.put(4, 6);
        map.put(3, 7);
        map.put(11, 13);
        Treap<Integer> treap = new Treap<>();
        treap.build(map);
        Treap<Integer>[] mas = split(7, treap);
        assertEquals(mas[0].toString(), "Node{x=5, y=10, left=Node{x=3, y=7, left=null, right=Node{x=4, y=6, left=null, right=null}}, right=null}");
        assertEquals(mas[1].toString(), "Node{x=8, y=20, left=null, right=Node{x=11, y=13, left=null, right=null}}");
    }

    @Test
    void remove() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(5, 10);
        map.put(8, 20);
        map.put(11, 13);
        map.put(2, 4);
        map.put(6, 6);
        map.put(13, 7);
        map.put(10, 9);
        Treap<Integer> treap = new Treap<>();
        treap.build(map);
        treap.remove(10);
        assertEquals(treap.toString(), "Node{x=8, y=20, left=Node{x=5, y=10, left=Node{x=2, y=4, left=null, right=null}, right=Node{x=6, y=6, left=null, right=null}}, right=Node{x=11, y=13, left=null, right=Node{x=13, y=7, left=null, right=null}}}");
    }

    @Test
    void kElement() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(5, 10);
        map.put(8, 20);
        map.put(11, 13);
        map.put(2, 4);
        map.put(6, 6);
        map.put(13, 7);
        map.put(10, 9);
        Treap<Integer> treap = new Treap<>();
        treap.build(map);
        int k = treap.kElement(2);
        int k1 = treap.kElement(5);
        int k2 = treap.kElement(4);
        assertEquals(k, 6);
        assertEquals(k1, 11);
        assertEquals(k2, 10);
    }

}