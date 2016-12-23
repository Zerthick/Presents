package io.github.zerthick.presents.present.data;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class PresentDataManipulatorBuilder implements DataManipulatorBuilder<PresentData, ImmutablePresentData>{
    @Override
    public PresentData create() {
        return new PresentData();
    }

    @Override
    public Optional<PresentData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(PresentData.class).orElse(new PresentData()));
    }

    @Override
    public Optional<PresentData> build(DataView container) throws InvalidDataException {
        if(container.contains(PresentDataKeys.PRESENT_ITEM.getQuery())) {
            Optional<ItemStack> itemStackOptional = container.getObject(PresentDataKeys.PRESENT_ITEM.getQuery(), ItemStack.class);
            if(itemStackOptional.isPresent()) {
                return Optional.of(new PresentData(itemStackOptional.get()));
            }
        }
        return Optional.empty();
    }
}
