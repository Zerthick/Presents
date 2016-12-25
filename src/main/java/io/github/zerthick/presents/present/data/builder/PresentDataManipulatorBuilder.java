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

package io.github.zerthick.presents.present.data.builder;

import io.github.zerthick.presents.present.data.PresentDataKeys;
import io.github.zerthick.presents.present.data.immutable.ImmutablePresentItemData;
import io.github.zerthick.presents.present.data.mutable.PresentItemData;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import java.util.Optional;

public class PresentDataManipulatorBuilder extends AbstractDataBuilder<PresentItemData> implements DataManipulatorBuilder<PresentItemData, ImmutablePresentItemData> {

    public PresentDataManipulatorBuilder() {
        super(PresentItemData.class, 1);
    }

    @Override
    public PresentItemData create() {
        return new PresentItemData();
    }

    @Override
    public Optional<PresentItemData> createFrom(DataHolder dataHolder) {
        return create().fill(dataHolder);
    }

    @Override
    public Optional<PresentItemData> buildContent(DataView container) throws InvalidDataException {
        if(container.contains(PresentDataKeys.PRESENT_ITEM.getQuery())) {
            Optional<ItemStackSnapshot> itemStackOptional = container.getSerializable(PresentDataKeys.PRESENT_ITEM.getQuery(), ItemStackSnapshot.class);
            if(itemStackOptional.isPresent()) {
                return Optional.of(new PresentItemData(itemStackOptional.get()));
            }
        }
        return Optional.empty();
    }
}
