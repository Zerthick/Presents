/*
 * Copyright (C) 2018  Zerthick
 *
 * This file is part of Presents.
 *
 * Presents is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Presents is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Presents.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zerthick.presents.present;

import io.github.zerthick.presents.util.random.RandomCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class RandomPresentManager {

    private RandomCollection<Present> presentRandomCollection;

    private int defaultRandomPresentAmount;
    private String coalSender;

    public RandomPresentManager(Map<Double, Present> presentWeightMap, int defaultPresentAmount, String coalSender) {
        presentRandomCollection = new RandomCollection<>();
        presentWeightMap.forEach((aDouble, present) -> presentRandomCollection.add(aDouble, present));

        this.defaultRandomPresentAmount = defaultPresentAmount;
        this.coalSender = coalSender;
    }

    public void addPresent(Present present, double weight) {
        presentRandomCollection.add(weight, present);
    }

    public Present nextPresent() {
        return presentRandomCollection.next();
    }

    public Collection<Present> nextPresentItems() {
        return nextPresentItems(defaultRandomPresentAmount);
    }

    public Collection<Present> nextPresentItems(int amount) {
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

    public int getDefaultRandomPresentAmount() {
        return defaultRandomPresentAmount;
    }

    public void setDefaultRandomPresentAmount(int defaultRandomPresentAmount) {
        this.defaultRandomPresentAmount = defaultRandomPresentAmount;
    }

    public String getCoalSender() {
        return coalSender;
    }

    public void setCoalSender(String coalSender) {
        this.coalSender = coalSender;
    }
}
