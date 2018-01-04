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
import io.github.zerthick.presents.present.PresentManager;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

import java.util.*;
import java.util.stream.Collectors;

public class PresentManagerSerializer implements TypeSerializer<PresentManager> {

    public static void register() {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(PresentManager.class), new PresentManagerSerializer());
    }

    @Override
    public PresentManager deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {

        Map<UUID, List<Present>> uuidListMap = value.getNode("presents").getValue(new TypeToken<Map<UUID, List<Present>>>() {
        });
        Map<UUID, Set<Present>> uuidSetMap = uuidListMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new HashSet<>(e.getValue())));
        return new PresentManager(uuidSetMap);

    }

    @Override
    public void serialize(TypeToken<?> type, PresentManager obj, ConfigurationNode value) throws ObjectMappingException {

        value.getNode("presents").setValue(new TypeToken<Map<UUID, List<Present>>>() {
                                           },
                obj.getPresentMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue()))));

    }
}
