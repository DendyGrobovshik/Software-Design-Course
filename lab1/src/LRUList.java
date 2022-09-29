public class LRUList<K, V> {
    private Node<K, V> head = null;
    private Node<K, V> tail = null;

    public Node<K, V> addFirst(K key, V value) {
        assert key != null;

        var newNode = new Node<>(key, value, this.head);

        head = newNode;
        if (isEmpty()) {
            tail = newNode;
        }

        return newNode;
    }

    public K removeLRU() {
        if (tail == null) {
            return null;
        }
        K key = tail.key;

        var neighbor = tail.collapse();
        assert neighbor == null || neighbor == tail.prev;

        tail = neighbor;

        if (neighbor == null) {
            head = null;
        }

        assert key != null;

        return key;
    }

    private boolean isEmpty() {
        return tail == null;
    }

    public static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> prev = null;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            assert key != null;

            this.key = key;
            this.value = value;

            this.next = next;
            if (next != null) {
                next.prev = this;
            }
        }

        public V getValue() {
            return value;
        }

        public Node<K, V> collapse() {
            if (next != null) {
                next.prev = prev;
            }

            if (prev != null) {
                prev.next = next;
                return prev;
            }

            return next;
        }
    }
}
