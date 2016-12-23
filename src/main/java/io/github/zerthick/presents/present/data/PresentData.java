package io.github.zerthick.presents.present.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class PresentData extends AbstractData<PresentData, ImmutablePresentData>{

    private ItemStack presentItemStack;

    public PresentData() {
        this(null);
    }

    public PresentData(ItemStack presentItemStack) {
        this.presentItemStack = presentItemStack;
    }

    public Value<ItemStack> presentItemStack() {
        return Sponge.getRegistry().getValueFactory().createValue(PresentDataKeys.PRESENT_ITEM, this.presentItemStack);
    }

    private ItemStack getPresentItemStack() {
        return presentItemStack;
    }

    private void setPresentItemStack(ItemStack presentItemStack) {
        this.presentItemStack = presentItemStack;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(PresentDataKeys.PRESENT_ITEM, this::getPresentItemStack);
        registerFieldSetter(PresentDataKeys.PRESENT_ITEM, this::setPresentItemStack);
        registerKeyValue(PresentDataKeys.PRESENT_ITEM, this::presentItemStack);
    }

    @Override
    public Optional<PresentData> fill(DataHolder dataHolder) {

        PresentData presentData = dataHolder.get(PresentData.class).orElse(null);

        return Optional.ofNullable(presentData);
    }

    @Override
    public Optional<PresentData> fill(DataHolder dataHolder, MergeFunction overlap) {
        PresentData presentData = overlap.merge(this, dataHolder.get(PresentData.class).orElse(null));
        setPresentItemStack(presentData.getPresentItemStack());
        return Optional.of(this);
    }

    @Override
    public Optional<PresentData> from(DataContainer container) {
        if(container.contains(PresentDataKeys.PRESENT_ITEM.getQuery())) {
            container.getObject(PresentDataKeys.PRESENT_ITEM.getQuery(), ItemStack.class)
                    .ifPresent(this::setPresentItemStack);
            return Optional.of(this);
        }
        return Optional.empty();
    }

    @Override
    public PresentData copy() {
        return new PresentData(this.presentItemStack);
    }

    @Override
    public ImmutablePresentData asImmutable() {
        return new ImmutablePresentData(this.presentItemStack);
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
