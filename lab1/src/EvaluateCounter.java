import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class EvaluateCounter<K, V> {
    private final Function<K, V> evaluator;
    private final Map<K, Integer> callCount = new HashMap<>();

    public EvaluateCounter(Function <K, V> evaluator) {
        this.evaluator = evaluator;
    }

    public Function<K, V> getObservedEvaluator() {
        return key -> {
            int oldCount = callCount.getOrDefault(key, 0);
            callCount.put(key, oldCount + 1);

            return evaluator.apply(key);
        };
    }

    public int callsWithKey(K key) {
        return callCount.get(key);
    }
}