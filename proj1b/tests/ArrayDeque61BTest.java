import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    void addTests() {
        Deque61B<Integer> de = new ArrayDeque61B<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                de.addFirst(i);
            } else {
                de.addLast(i);
            }
        }
        Integer tmp = 0;
        for (int i = 0; i < 50; i++) {
            tmp = de.removeFirst();
            tmp = de.removeLast();
        }
        assertThat(tmp == 1).isTrue();
    }
}
