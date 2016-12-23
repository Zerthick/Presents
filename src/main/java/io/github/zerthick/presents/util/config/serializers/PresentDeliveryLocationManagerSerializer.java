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
import io.github.zerthick.presents.present.PresentDeliveryLocationManager;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;
import java.util.UUID;

public class PresentDeliveryLocationManagerSerializer implements TypeSerializer<PresentDeliveryLocationManager> {

    public static void register() {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(PresentDeliveryLocationManager.class), new PresentDeliveryLocationManagerSerializer());
    }

    @Override
    public PresentDeliveryLocationManager deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {

        Map<UUID, Location<World>> uuidLocationMap = value.getNode("locations").getValue(
                new TypeToken<Map<UUID, Location<World>>>() {
                });

        return new PresentDeliveryLocationManager(uuidLocationMap);
    }

    @Override
    public void serialize(TypeToken<?> type, PresentDeliveryLocationManager obj, ConfigurationNode value) throws ObjectMappingException {
        value.setValue(new TypeToken<Map<UUID, Location<World>>>() {
                       },
                obj.getPresentDeliveryLocations());
    }
}
