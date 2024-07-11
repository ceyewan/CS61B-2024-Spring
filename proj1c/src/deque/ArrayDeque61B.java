package deque;

import java.util.*;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] deque;
    private int size;
    private int first, last;

    @SuppressWarnings("unchecked")
    public ArrayDeque61B() {
        deque = (T[]) new Object[8];
        size = 0;
        first = last = 0;
    }

    @SuppressWarnings("unchecked")
    private void resizeUp() {
        T[] tmp = (T[]) new Object[size * 2];
        for (int i = 0; i < size; i++) {
            tmp[i] = deque[Math.floorMod(first + i, size)];
        }
        first = 0;
        last = size;
        deque = tmp;
    }

    @Override
    public void addFirst(T x) {
        if (size == deque.length) {
            resizeUp();
        }
        first = Math.floorMod(first - 1, deque.length);
        deque[first] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == deque.length) {
            resizeUp();
        }
        deque[last] = x;
        last = Math.floorMod(last + 1, deque.length);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(deque[Math.floorMod(first + i, deque.length)]);
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

    @SuppressWarnings("unchecked")
    private void resizeDown() {
        if (deque.length < 16) {
            return;
        }
        if (size <= deque.length / 4) {
            T[] tmp = (T[]) new Object[deque.length / 2];
            for (int i = 0; i < size; i++) {
                tmp[i] = deque[Math.floorMod(first + i, deque.length)];
            }
            first = 0;
            last = size;
            deque = tmp;
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T tmp = deque[first];
        first = Math.floorMod(first + 1, deque.length);
        size--;
        resizeDown();
        return tmp;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        last = Math.floorMod(last - 1, deque.length);
        T tmp = deque[last];
        size--;
        resizeDown();
        return tmp;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return deque[Math.floorMod(first + index, deque.length)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        int pos;

        ArrayDequeIterator() {
            pos = first;
        }

        @Override
        public boolean hasNext() {
            return pos != last;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T tmp = deque[pos];
            pos = Math.floorMod(pos + 1, deque.length);
            return tmp;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ArrayDeque61B<?> otherDeque) {
            if (this.size() != otherDeque.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i++) {
                // 字符串的话，需要使用 equals 来比较
                if (!this.get(i).equals(otherDeque.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "";
        }
        StringBuilder returnSB = new StringBuilder("{");
        for (int i = 0; i < size - 1; i += 1) {
            returnSB.append(get(i));
            returnSB.append(", ");
        }
        returnSB.append(get(size - 1));
        returnSB.append("}");
        return returnSB.toString();
    }
}

