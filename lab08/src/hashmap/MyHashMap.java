package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author ceyewan
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    int capacity;
    int size;
    double loadFactor;

    /**
     * Constructors
     */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor      maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        buckets = new Collection[this.capacity];
        for (int i = 0; i < this.capacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * Note that that this is referring to the hash table bucket itself,
     * not the hash map itself.
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new HashSet<>();
    }

    private void increaseBucket() {
        capacity *= 2;
        Collection<Node>[] tmp = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            tmp[i] = createBucket();
        }
        for (int i = 0; i < capacity / 2; i++) {
            for (Node n : buckets[i]) {
                int index = Math.floorMod(n.key.hashCode(), capacity);
                tmp[index].add(n);
            }
        }
        buckets = tmp;
    }

    // Your code won't compile until you do so!
    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), capacity);
        for (Node n : buckets[index]) {
            if (n.key.equals(key)) {
                n.value = value;
                return;
            }
        }
        buckets[index].add(new Node(key, value));
        size++;
        if ((double) size / capacity >= loadFactor) {
            increaseBucket();
        }
    }

    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), capacity);
        for (Node n : buckets[index]) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), capacity);
        for (Node n : buckets[index]) {
            if (n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> result = new HashSet<>();
        for (int i = 0; i < capacity; i++) {
            for (Node n : buckets[i]) {
                result.add(n.key);
            }
        }
        return result;
    }

    @Override
    public V remove(K key) {
        int index = Math.floorMod(key.hashCode(), capacity);
        for (Node n : buckets[index]) {
            if (n.key == key) {
                V tmp = n.value;
                buckets[index].remove(n);
                size--;
                return tmp;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        private int index = 0;
        private Iterator<Node> iterator = null;

        @Override
        public boolean hasNext() {
            if (iterator != null && iterator.hasNext()) {
                return true;
            }
            while (index < capacity) {
                Collection<Node> bucket = buckets[index];
                if (!bucket.isEmpty()) {
                    iterator = bucket.iterator();
                    if (iterator.hasNext()) {
                        return true;
                    }
                }
                index++;
            }
            return false;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                return iterator.next().key;
            }
        }
    }
}
