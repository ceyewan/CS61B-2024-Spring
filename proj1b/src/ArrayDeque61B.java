import java.util.ArrayList;
import java.util.List;

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
        if(size == 0) {
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
        if(size == 0) {
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
}
