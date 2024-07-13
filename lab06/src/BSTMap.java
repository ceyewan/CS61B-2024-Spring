import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = this.right = null;
        }
    }

    BSTNode root;
    int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    private BSTNode insert(BSTNode root, K key, V value) {
        if (root == null) {
            size++;
            return new BSTNode(key, value);
        }
        if (key.compareTo(root.key) < 0) {
            root.left = insert(root.left, key, value);
        } else if (key.compareTo(root.key) > 0) {
            root.right = insert(root.right, key, value);
        } else {
            root.value = value;
        }
        return root;
    }

    @Override
    public void put(K key, V value) {
        root = insert(root, key, value);
    }

    private BSTNode find(BSTNode root, K key) {
        if (root == null) {
            return null;
        }
        if (key.equals(root.key)) {
            return root;
        } else if (key.compareTo(root.key) < 0) {
            return find(root.left, key);
        } else {
            return find(root.right, key);
        }
    }

    @Override
    public V get(K key) {
        BSTNode tmp = find(root, key);
        return tmp == null ? null : tmp.value;
    }

    @Override
    public boolean containsKey(K key) {
        return find(root, key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private Set<K> keySetHelper(BSTNode root) {
        if (root == null) {
            return new TreeSet<>();
        }
        Set<K> result = new TreeSet<>(keySetHelper(root.left));
        result.add(root.key);
        result.addAll(keySetHelper(root.right));
        return result;
    }

    @Override
    public Set<K> keySet() {
        return keySetHelper(root);
    }

    private BSTNode findMin(BSTNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private BSTNode delete(BSTNode root, K key) {
        if (root == null) {
            return null;
        }
        if (key.compareTo(root.key) < 0) {
            root.left = delete(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = delete(root.right, key);
        } else {
            size--;
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            BSTNode tmp = findMin(root.right);
            root.key = tmp.key;
            root.value = tmp.value;
            root.right = delete(root.right, tmp.key);
        }
        return root;
    }

    @Override
    public V remove(K key) {
        V tmp = get(key);
        root = delete(root, key);
        return tmp;
    }

    private class BSTMapIterator implements Iterator<K> {
        private final Stack<BSTNode> stack = new Stack<>();

        BSTMapIterator() {
            pushStack(root);
        }

        private void pushStack(BSTNode root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BSTNode tmp = stack.pop();
            pushStack(tmp.right);
            return tmp.key;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private void printHelper(BSTNode root) {
        if (root != null) {
            printHelper(root.left);
            System.out.println("{" + root.key + ": " + root.value + "}");
            printHelper(root.right);
        }
    }

    public void printInOrder() {
        printHelper(root);
    }
}
