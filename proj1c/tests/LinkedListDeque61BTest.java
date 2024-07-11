import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

public class LinkedListDeque61BTest {
    @Test
    public void testIterator() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("fury road");

        // Create an iterator
        Iterator<String> iterator = deque.iterator();

        // Check that the iterator returns elements in the correct order
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("fury road");

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("2");

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("1");

        // Ensure that the iterator has no more elements
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void testEmptyIterator() {
        Deque61B<String> deque = new LinkedListDeque61B<>();

        // Create an iterator for an empty deque
        Iterator<String> iterator = deque.iterator();

        // Ensure that the iterator has no elements
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void testIteratorNoSuchElementException() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        deque.addFirst("1");

        // Create an iterator
        Iterator<String> iterator = deque.iterator();

        // Iterate through all elements
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("1");

        // Ensure that calling next() on an exhausted iterator throws NoSuchElementException
        assertThat(iterator.hasNext()).isFalse();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testIteratorRemove() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        deque.addFirst("1");

        // Create an iterator
        Iterator<String> iterator = deque.iterator();

        // Call remove() method which is not supported
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    public void testToString() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("fury road");

        // Check the toString method
        assertThat(deque.toString()).isEqualTo("{fury road, 2, 1}");

        // Check toString method on an empty deque
        Deque61B<String> emptyDeque = new LinkedListDeque61B<>();
        assertThat(emptyDeque.toString()).isEqualTo("");
    }

    @Test
    public void testEquals() {
        Deque61B<String> deque1 = new LinkedListDeque61B<>();
        Deque61B<String> deque2 = new LinkedListDeque61B<>();

        // Test empty deques are equal
        assertThat(deque1).isEqualTo(deque2);

        // Test deques with the same elements are equal
        deque1.addLast("a");
        deque2.addLast("a");
        assertThat(deque1).isEqualTo(deque2);

        deque1.addLast("b");
        deque2.addLast("b");
        assertThat(deque1).isEqualTo(deque2);

        // Test deques with different elements are not equal
        deque1.addLast("c");
        assertThat(deque1).isNotEqualTo(deque2);

        // Test deques with different sizes are not equal
        deque2.addLast("c");
        deque1.addLast("d");
        assertThat(deque1).isNotEqualTo(deque2);

        // Test deques with the same elements in different order are not equal
        deque1 = new LinkedListDeque61B<>();
        deque2 = new LinkedListDeque61B<>();
        deque1.addLast("a");
        deque1.addLast("b");
        deque2.addLast("b");
        deque2.addLast("a");
        assertThat(deque1).isNotEqualTo(deque2);
    }
}
