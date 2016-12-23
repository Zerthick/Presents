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

package io.github.zerthick.presents.present.data.Immutable;

import io.github.zerthick.presents.present.data.Mutable.PresentItemData;
import io.github.zerthick.presents.present.data.PresentDataKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.item.inventory.ItemStack;

public class ImmutablePresentItemData extends AbstractImmutableSingleData<ItemStack, ImmutablePresentItemData, PresentItemData> {

    public ImmutablePresentItemData() {
        this(null);
    }

    public ImmutablePresentItemData(ItemStack value) {
        super(value, PresentDataKeys.PRESENT_ITEM);
    }

    private ImmutableValue<ItemStack> presentItemStack() {
        return Sponge.getRegistry().getValueFactory().createValue(PresentDataKeys.PRESENT_ITEM, getValue()).asImmutable();
    }

    @Override
    protected ImmutableValue<?> getValueGetter() {
        return presentItemStack();
    }

    @Override
    public PresentItemData asMutable() {
        return new PresentItemData(getValue());
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(PresentDataKeys.PRESENT_ITEM, getValue());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }
}
