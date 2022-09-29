import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LRUCacheTest {

    @Nested
    @DisplayName("Cache API")
    class cacheAPI {
        private LRUCache<Integer, String> cache;

        @BeforeEach
        public void cacheInit() {
            cache = new LRUCache<>(Object::toString);
        }

        @Test
        public void constructor_apply_capacity() {
            cache = new LRUCache<>(Object::toString, 32);

            assertTrue(true);
        }

        @Test
        public void get_correct() {
            String result = cache.get(322);

            assertEquals("322", result);
        }

        @Test
        public void get_assert_if_key_is_null() {
            assertThrows(AssertionError.class, () -> cache.get(null));
        }

        @Test
        public void changeCapacity_assert_if_newCapacity_LE_zero() {
            assertThrows(AssertionError.class, () -> cache.changeCapacity(0));
        }

        @Test
        public void changeEvaluator_assert_if_evaluator_is_null() {
            assertThrows(AssertionError.class, () -> {
                cache.changeEvaluator(null);
            });
        }

        @Test
        public void changeEvaluator_must_clear_cache() {
            String result1 = cache.get(322);
            assertEquals("322", result1, "Initial evaluator is correct");

            cache.changeEvaluator(i -> (i.toString().concat("+")));
            String result2 = cache.get(322);
            assertEquals("322+", result2, "Updated evaluator is correct");
        }

        @Test
        public void size_correct() {
            int size = cache.size();
            assertEquals(0, size);
            cache.get(322);
            size = cache.size();
            assertEquals(1, size);
            cache.get(322);
            size = cache.size();
            assertEquals(1, size);
            cache.get(111);
            size = cache.size();
            assertEquals(2, size);
        }

        @Test
        public void size_correct_after_changeEvaluator() {
            cache.get(322);
            cache.changeEvaluator(i -> (i.toString().concat("+")));

            int size = cache.size();
            assertEquals(0, size);
        }
    }

    @Test
    public void get_must_call_evaluator_once() {
        EvaluateCounter<Integer, String> evaluateCounter = new EvaluateCounter<>(Object::toString);

        LRUCache<Integer, String> cache = new LRUCache<>(evaluateCounter.getObservedEvaluator());
        cache.get(322);
        cache.get(322);

        assertEquals(1, evaluateCounter.callsWithKey(322), "Evaluator for key 322 called once");
    }

    @Test
    public void get_must_call_evaluator_twice_because_cache_miss() {
        EvaluateCounter<Integer, String> evaluateCounter = new EvaluateCounter<>(Object::toString);

        LRUCache<Integer, String> cache = new LRUCache<>(evaluateCounter.getObservedEvaluator(), 1);
        cache.get(1);
        cache.get(2);
        cache.get(1);

        assertEquals(2, evaluateCounter.callsWithKey(1), "Evaluator for key 1 called twice");
    }
}
