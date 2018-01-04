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

package io.github.zerthick.presents.util.config.serializers;

import com.google.common.reflect.TypeToken;
import io.github.zerthick.presents.present.Present;
import io.github.zerthick.presents.present.RandomPresentManager;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

import java.util.Map;

public class RandomPresentManagerSerializer implements TypeSerializer<RandomPresentManager> {

    public static void register() {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(RandomPresentManager.class), new RandomPresentManagerSerializer());
    }

    @Override
    public RandomPresentManager deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {

        Map<Double, Present> doubleItemStackSnapshotMap = value.getNode("randomPresents").getValue(
                new TypeToken<Map<Double, Present>>() {
                }
        );
        int defaultRandomPresentAmount = value.getNode("defaultRandomPresentAmount").getInt();
        String coalSender = value.getNode("coalSender").getString();

        return new RandomPresentManager(doubleItemStackSnapshotMap, defaultRandomPresentAmount, coalSender);
    }

    @Override
    public void serialize(TypeToken<?> type, RandomPresentManager obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("randomPresents").setValue(new TypeToken<Map<Double, Present>>() {
                                                 },
                obj.getPresentWeightMap());
        value.getNode("defaultRandomPresentAmount").setValue(TypeToken.of(Integer.class), obj.getDefaultRandomPresentAmount());
        value.getNode("coalSender").setValue(TypeToken.of(String.class), obj.getCoalSender());
    }
}
