import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void removeFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("back"); // after this call we expect: ["back"]
        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]

        String result = lld1.removeFirst();
        assertThat(result.equals("front")).isTrue();
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();
        result = lld1.removeFirst();
        assertThat(result.equals("middle")).isTrue();
        assertThat(lld1.toList()).containsExactly("back").inOrder();
        result = lld1.removeFirst();
        assertThat(result.equals("back")).isTrue();
        assertThat(lld1.toList()).isEmpty();
        result = lld1.removeFirst();
        assertThat(result == null).isTrue();
    }

    @Test
    public void removeLastTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        Integer result = lld1.removeLast();
        assertThat(result == 1).isTrue();
        assertThat(lld1.toList()).containsExactly(-1, 0).inOrder();
        result = lld1.removeLast();
        assertThat(result == 0).isTrue();
        assertThat(lld1.toList()).containsExactly(-1).inOrder();
        result = lld1.removeLast();
        assertThat(result== -1).isTrue();
        assertThat(lld1.toList()).isEmpty();
        result = lld1.removeLast();
        assertThat(result == null).isTrue();
    }

    @Test
    public  void sizeTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.removeFirst();
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addLast(1);   // [0, 1]
        lld1.removeFirst();
        lld1.addFirst(-1); // [-1, 0, 1]
        assertThat(lld1.size() == 1).isTrue();
    }

    @Test
    public  void getTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        assertThat(lld1.get(0) == -1).isTrue();
        assertThat(lld1.get(1) == 0).isTrue();
        assertThat(lld1.get(3) == null).isTrue();
        assertThat(lld1.get(-1) == null).isTrue();
    }

    @Test
    public  void getRecursiveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        assertThat(lld1.getRecursive(0) == -1).isTrue();
        assertThat(lld1.getRecursive(1) == 0).isTrue();
        assertThat(lld1.getRecursive(3) == null).isTrue();
        assertThat(lld1.getRecursive(-1) == null).isTrue();
        for (int i = 0; i < 1000; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.getRecursive(1000) == 997).isTrue();
    }
}