import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class LRUCache<K, V> {
    private static final int DEFAULT_CAPACITY = 64;

    private int capacity;
    private LRUList<K, V> lruList = new LRUList<>();
    private Map<K, LRUList.Node<K, V>> hashtable = new HashMap<>();
    private Function<K, V> evaluator;

    public LRUCache(Function<K, V> evaluator, int capacity) {
        assert evaluator != null;
        assert capacity > 0;

        this.capacity = capacity;
        this.evaluator = evaluator;
    }

    public LRUCache(Function<K, V> evaluator) {
        this(evaluator, DEFAULT_CAPACITY);
    }

    public V get(K key) {
        assert key != null;

        if (isCached(key)) {
            return updateUsageAndGetValue(key);
        }

        if (isFull()) {
            removeLRU();
        }

        V value = evaluator.apply(key);
        cache(key, value);

        return value;
    }

    public void changeCapacity(int newCapacity) {
        assert newCapacity > 0;

        if (newCapacity < capacity) {
            while (size() > newCapacity) {
                removeLRU();
            }
        }

        capacity = newCapacity;
    }

    public void changeEvaluator(Function<K, V> newEvaluator) {
        assert newEvaluator != null;

        clear();

        this.evaluator = newEvaluator;
    }


    public int size() {
        return hashtable.size();
    }

    private boolean isFull() {
        return size() == capacity;
    }

    private boolean isCached(K key) {
        return hashtable.containsKey(key);
    }

    private void cache(K key, V value) {
        int _preSize = size();
        assert _preSize < capacity;

        var newNode = lruList.addFirst(key, value);
        hashtable.put(key, newNode);

        int _postSize = size();
        assert _postSize == _preSize + 1;
    }

    private void removeLRU() {
        int _preSize = size();
        assert _preSize <= capacity;

        K LRUKey = lruList.removeLRU();
        hashtable.remove(LRUKey);

        int _postSize = size();
        assert _postSize == _preSize - 1;
    }

    private V updateUsageAndGetValue(K key) {
        int _prevSize = size();

        var node = hashtable.get(key);
        V value = node.getValue();
        node.collapse();
        var newNode = lruList.addFirst(key, value);

        hashtable.replace(key, newNode);

        int _postSize = size();
        assert _prevSize == _postSize;

        return value;
    }

    private void clear() {
        lruList = new LRUList<>();
        hashtable = new HashMap<>();
    }
}
