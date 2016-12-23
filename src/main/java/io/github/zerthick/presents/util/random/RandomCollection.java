package io.github.zerthick.presents.util.random;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class RandomCollection<E> {

    private final NavigableMap<Double, E> map;
    private double total;

    public RandomCollection() {
        map = new TreeMap<>();
        total = 0;
    }

    public void add(double weight, E result) {
        if (weight <= 0 || map.containsValue(result))
            return;
        total += weight;
        map.put(total, result);
    }

    public E next() {
        double value = ThreadLocalRandom.current().nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }

    public Map<Double, E> getWeightMap() {
        return new HashMap<>(map);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }
}


