/*
 * Copyright (C) 2016  Zerthick
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


