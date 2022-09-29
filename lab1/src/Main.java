public class Main {
    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(key -> key * key, 3);

        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        System.out.println(cache.get(2));

        System.out.println(cache.get(4));
    }
}