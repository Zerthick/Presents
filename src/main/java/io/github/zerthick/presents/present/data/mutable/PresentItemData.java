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

package io.github.zerthick.presents.present.data.mutable;

import io.github.zerthick.presents.present.data.PresentDataKeys;
import io.github.zerthick.presents.present.data.immutable.ImmutablePresentItemData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import java.util.Optional;

public class PresentItemData extends AbstractSingleData<ItemStackSnapshot, PresentItemData, ImmutablePresentItemData> {

    public PresentItemData() {
        this(ItemStackSnapshot.NONE);
    }

    public PresentItemData(ItemStackSnapshot value) {
        super(value, PresentDataKeys.PRESENT_ITEM);
    }

    private Value<ItemStackSnapshot> presentItem() {
        return Sponge.getRegistry().getValueFactory().createValue(PresentDataKeys.PRESENT_ITEM, getValue());
    }

    @Override
    protected Value<ItemStackSnapshot> getValueGetter() {
        return presentItem();
    }

    @Override
    public Optional<PresentItemData> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(PresentItemData.class).ifPresent((data) -> {
            PresentItemData finalData = overlap.merge(this, data);
            setValue(finalData.getValue());
        });
        return Optional.of(this);
    }

    @Override
    public Optional<PresentItemData> from(DataContainer container) {
        if(container.contains(PresentDataKeys.PRESENT_ITEM.getQuery())) {
            container.getObject(PresentDataKeys.PRESENT_ITEM.getQuery(), ItemStackSnapshot.class)
                    .ifPresent(this::setValue);
            return Optional.of(this);
        }
        return Optional.empty();
    }

    @Override
    public PresentItemData copy() {
        return new PresentItemData(this.getValue());
    }

    @Override
    public ImmutablePresentItemData asImmutable() {
        return new ImmutablePresentItemData(this.getValue());
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
