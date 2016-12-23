package io.github.zerthick.presents.present.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.item.inventory.ItemStack;

public class ImmutablePresentData extends AbstractImmutableData<ImmutablePresentData, PresentData>{

    private final ItemStack presentItemStack;

    public ImmutablePresentData() {
        this(null);
    }

    public ImmutablePresentData(ItemStack presentItemStack) {
        this.presentItemStack = presentItemStack;
    }

    public ImmutableValue<ItemStack> presentItemStack() {
        return Sponge.getRegistry().getValueFactory().createValue(PresentDataKeys.PRESENT_ITEM, this.presentItemStack).asImmutable();
    }

    private ItemStack getPresentItemStack() {
        return presentItemStack;
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(PresentDataKeys.PRESENT_ITEM, this::getPresentItemStack);
        registerKeyValue(PresentDataKeys.PRESENT_ITEM, this::presentItemStack);
    }

    @Override
    public PresentData asMutable() {
        return new PresentData(this.presentItemStack);
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(PresentDataKeys.PRESENT_ITEM, getPresentItemStack());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }
}
