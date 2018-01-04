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
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;

public class PresentSerializer implements TypeSerializer<Present> {

    public static void register() {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Present.class), new PresentSerializer());
    }

    @Override
    public Present deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {

        final ItemStackSnapshot present = value.getNode("present").getValue(TypeToken.of(ItemStackSnapshot.class));
        final String sender = value.getNode("sender").getString();
        final String receiver = value.getNode("receiver").getString();
        final Text note = value.getNode("note").getValue(TypeToken.of(Text.class));

        return new Present(present, sender, receiver, note);
    }

    @Override
    public void serialize(TypeToken<?> type, Present obj, ConfigurationNode value) throws ObjectMappingException {

        value.getNode("present").setValue(TypeToken.of(ItemStackSnapshot.class), obj.getPresent());
        value.getNode("sender").setValue(TypeToken.of(String.class), obj.getSender());
        value.getNode("receiver").setValue(TypeToken.of(String.class), obj.getReceiver());
        value.getNode("note").setValue(TypeToken.of(Text.class), obj.getNote());

    }
}
