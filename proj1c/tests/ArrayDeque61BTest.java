import deque.ArrayDeque61B;
import deque.Deque61B;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
    @Test
    public void testToString() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        assertThat(deque.toString()).isEmpty();  // 测试空 deque

        deque.addFirst("1");
        assertThat(deque.toString()).isEqualTo("{1}");  // 测试单元素 deque

        deque.addFirst("2");
        deque.addLast("3");
        assertThat(deque.toString()).isEqualTo("{2, 1, 3}");  // 测试多元素 deque

        Deque61B<Integer> intDeque = new ArrayDeque61B<>();
        assertThat(intDeque.toString()).isEmpty();  // 测试空整数 deque

        intDeque.addFirst(1);
        assertThat(intDeque.toString()).isEqualTo("{1}");  // 测试单元素整数 deque

        intDeque.addFirst(2);
        intDeque.addLast(3);
        assertThat(intDeque.toString()).isEqualTo("{2, 1, 3}");  // 测试多元素整数 deque
    }

    @Test
    public void testEquals() {
        Deque61B<String> deque1 = new ArrayDeque61B<>();
        Deque61B<String> deque2 = new ArrayDeque61B<>();
        assertThat(deque1).isEqualTo(deque2);  // 测试两个空 deque 是否相等

        deque1.addFirst("1");
        deque1.addFirst("2");
        deque1.addLast("3");
        deque2.addFirst("1");
        deque2.addFirst("2");
        deque2.addLast("3");
        assertThat(deque1).isEqualTo(deque2);  // 测试相同元素的 deque 是否相等

        deque2.addLast("4");
        assertThat(deque1).isNotEqualTo(deque2);  // 测试不同元素的 deque 是否不相等

        Deque61B<Integer> intDeque1 = new ArrayDeque61B<>();
        Deque61B<Integer> intDeque2 = new ArrayDeque61B<>();
        assertThat(intDeque1).isEqualTo(intDeque2);  // 测试两个空整数 deque 是否相等

        intDeque1.addFirst(1);
        intDeque1.addFirst(2);
        intDeque1.addLast(3);
        intDeque2.addFirst(1);
        intDeque2.addFirst(2);
        intDeque2.addLast(3);
        assertThat(intDeque1).isEqualTo(intDeque2);  // 测试相同整数元素的 deque 是否相等

        intDeque2.addLast(4);
        assertThat(intDeque1).isNotEqualTo(intDeque2);  // 测试不同整数元素的 deque 是否不相等
    }

    @Test
    public void testIterator() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        Iterator<String> iterator = deque.iterator();
        assertThat(iterator.hasNext()).isFalse();  // 测试空 deque 的迭代器

        deque.addFirst("1");
        deque.addFirst("2");
        deque.addLast("3");

        iterator = deque.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("2");
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("1");
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("3");
        assertThat(iterator.hasNext()).isFalse();  // 测试多元素 deque 的迭代器

        Deque61B<Integer> intDeque = new ArrayDeque61B<>();
        intDeque.addFirst(1);
        intDeque.addFirst(2);
        intDeque.addLast(3);

        Iterator<Integer> intIterator = intDeque.iterator();
        assertThat(intIterator.hasNext()).isTrue();
        assertThat(intIterator.next()).isEqualTo(2);
        assertThat(intIterator.hasNext()).isTrue();
        assertThat(intIterator.next()).isEqualTo(1);
        assertThat(intIterator.hasNext()).isTrue();
        assertThat(intIterator.next()).isEqualTo(3);
        assertThat(intIterator.hasNext()).isFalse();  // 测试多整数元素 deque 的迭代器
    }
}