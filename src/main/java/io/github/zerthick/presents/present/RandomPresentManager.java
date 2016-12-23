package io.github.zerthick.presents.present;

import io.github.zerthick.presents.util.random.RandomCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class RandomPresentManager {

    private RandomCollection<Present> presentRandomCollection;

    public RandomPresentManager(Map<Double, Present> weightMap) {
        presentRandomCollection = new RandomCollection<>();
        weightMap.forEach((aDouble, present) -> presentRandomCollection.add(aDouble, present));
    }

    public void addPresent(Present present, double weight) {
        presentRandomCollection.add(weight, present);
    }

    public Present nextPresent() {
        return presentRandomCollection.next();
    }

    public Collection<Present> nextPresents(int amount) {
        Collection<Present> presents = new ArrayList<>(amount);
        for(int i = 0; i < amount; i++) {
            presents.add(nextPresent());
        }
        return presents;
    }

    public boolean isEmpty() {
        return presentRandomCollection.isEmpty();
    }

    public Map<Double, Present> getPresentWeightMap() {
        return presentRandomCollection.getWeightMap();
    }
}
