import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LRUListTest {

    @Nested
    @DisplayName("LRU List API")
    class LRUListAPI {
        private LRUList<Integer, String> lruList;

        @BeforeEach
        public void LRUListInit() {
            lruList = new LRUList<>();
        }

        @Test
        public void addFirst_assert_if_key_is_null() {
            assertThrows(AssertionError.class, () -> lruList.addFirst(null, ""));
        }

        @Test
        public void addFirst_return_new_node() {
            var node = lruList.addFirst(1, "322");

            assertEquals("322", node.getValue(), "Correct node value");
        }

        @Test
        public void removeLRU_do_nothing_if_empty_list() {
            lruList.removeLRU();

            assertTrue(true);
        }

        @Test
        public void removeLRU_return_correct_key() {
            lruList.addFirst(1, "1");
            lruList.addFirst(2, "2");
            var key = lruList.removeLRU();

            assertEquals(1, key, "Correct lru key");
        }
    }

    @Nested
    @DisplayName("LRU Node API")
    class NodeAPI {
        private LRUList<Integer, String> lruList;

        @BeforeEach
        public void LRUListInit() {
            lruList = new LRUList<>();
        }

        @Test
        public void collapse_node_with_no_neighbours() {
            var node = lruList.addFirst(1, "1");
            var neighbor = node.collapse();
            assertNull(neighbor);
        }

        @Test
        public void collapse_node_with_right_neighbours() {
            var rightNeighbour = lruList.addFirst(1, "1");
            var node = lruList.addFirst(2, "1");

            var neighbor = node.collapse();
            assertEquals(rightNeighbour, neighbor);
        }

        @Test
        public void collapse_node_with_neighbours() {
            var rightNeighbour = lruList.addFirst(1, "1");
            var node = lruList.addFirst(2, "1");
            var leftNeighbour = lruList.addFirst(3, "3");

            var neighbor = node.collapse();
            assertEquals(leftNeighbour, neighbor);
        }
    }
}
