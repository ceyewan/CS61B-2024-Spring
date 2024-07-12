import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    int[] array;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        array = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return Math.abs(find(v));
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return array[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v >= array.length || v < 0) {
            throw new IllegalArgumentException("v must >= 0 and < array.length");
        }
        List<Integer> tmp = new ArrayList<Integer>();
        while (array[v] >= 0) {
            tmp.add(v);
            v = array[v];
        }
        for (int v1 : tmp) {
            array[v1] = v;
        }
        return v;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        assert 0 <= v1 && v1 < array.length;
        assert 0 <= v2 && v2 < array.length;
        int r1 = find(v1);
        int r2 = find(v2);
        if (r1 == r2) {
            return;
        }
        if (array[r1] < array[r2]) {
            array[r1] = array[r1] + array[r2];
            array[r2] = r1;
        } else {
            array[r2] = array[r1] + array[r2];
            array[r1] = r2;
        }
    }

}
