import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    public class Node {
        T items;
        Node next, prev;
        public Node(T data) {
            items = data;
            next = prev = null;
        }
    }

    private final Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null);
        sentinel.next = sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node tmp = new Node(x);
        tmp.next = sentinel.next;
        sentinel.next.prev = tmp;
        sentinel.next = tmp;
        tmp.prev = sentinel;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node tmp = new Node(x);
        sentinel.prev.next = tmp;
        tmp.prev = sentinel.prev;
        tmp.next = sentinel;
        sentinel.prev = tmp;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        Node p = sentinel.next;
        while (p != sentinel) {
            result.add(p.items);
            p = p.next;
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node tmp = sentinel.next;
        tmp.next.prev = sentinel;
        sentinel.next = tmp.next;
        size--;
        return tmp.items;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node tmp = sentinel.prev;
        tmp.prev.next = sentinel;
        sentinel.prev = tmp.prev;
        size--;
        return tmp.items;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node p = sentinel.next;
        while (index > 0) {
            index--;
            p = p.next;
        }
        return p.items;
    }

    private T helper(Node sentinel, int index) {
        if (index == 0) {
            return sentinel.next.items;
        }
        return helper(sentinel.next, index - 1);
    }
    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return helper(sentinel, index);
    }
}
