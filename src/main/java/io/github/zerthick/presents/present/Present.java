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

import com.google.common.collect.ImmutableList;
import io.github.zerthick.presents.present.data.Mutable.PresentItemData;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class Present {

    private final ItemStack present;
    private final String sender;
    private final String receiver;
    private final Text note;

    public Present(ItemStack present, String sender, String receiver) {
        this(present, sender, receiver, Text.EMPTY);
    }

    public Present(ItemStack present, String sender, String receiver, Text note) {
        this.present = present;
        this.sender = sender;
        this.receiver = receiver;
        this.note = note;
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = ItemStack.of(ItemTypes.CHEST, 1);
        itemStack.offer(Keys.DISPLAY_NAME, Text.of("From: ", sender));
        itemStack.offer(Keys.ITEM_LORE, ImmutableList.of(note));
        itemStack.offer(new PresentItemData(present));
        return itemStack;
    }

    public ItemStack getPresent() {
        return present;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Text getNote() {
        return note;
    }
}
