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

package io.github.zerthick.presents.present;

import io.github.zerthick.presents.util.random.RandomCollection;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class RandomPresentManager {

    private RandomCollection<ItemStack> presentRandomCollection;

    public RandomPresentManager(Map<Double, ItemStack> weightMap) {
        presentRandomCollection = new RandomCollection<>();
        weightMap.forEach((aDouble, present) -> presentRandomCollection.add(aDouble, present));
    }

    public void addPresent(ItemStack presentItem, double weight) {
        presentRandomCollection.add(weight, presentItem);
    }

    public ItemStack nextPresentItem() {
        return presentRandomCollection.next();
    }

    public Collection<ItemStack> nextPresentItems(int amount) {
        Collection<ItemStack> presents = new ArrayList<>(amount);
        for(int i = 0; i < amount; i++) {
            presents.add(nextPresentItem());
        }
        return presents;
    }

    public boolean isEmpty() {
        return presentRandomCollection.isEmpty();
    }

    public Map<Double, ItemStack> getPresentItemWeightMap() {
        return presentRandomCollection.getWeightMap();
    }
}
