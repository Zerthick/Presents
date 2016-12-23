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

package io.github.zerthick.presents.util.config.serializers;

import com.google.common.reflect.TypeToken;
import io.github.zerthick.presents.NaughtyListManager;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

import java.util.*;

public class NaughtyListManagerSerializer implements TypeSerializer<NaughtyListManager> {

    public static void register() {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(NaughtyListManager.class), new NaughtyListManagerSerializer());
    }

    @Override
    public NaughtyListManager deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {

        List<UUID> uuidList = value.getNode("naughtyList").getList(TypeToken.of(UUID.class));
        Set<UUID> uuidSet = new HashSet<>(uuidList);

        return new NaughtyListManager(uuidSet);
    }

    @Override
    public void serialize(TypeToken<?> type, NaughtyListManager obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("naughtyList").setValue(new TypeToken<List<UUID>>() {
                                              },
                new ArrayList<>(obj.getNaughtyList()));
    }
}
