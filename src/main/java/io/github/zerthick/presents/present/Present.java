package io.github.zerthick.presents.present;

import com.google.common.collect.ImmutableList;
import io.github.zerthick.presents.present.data.PresentData;
import io.github.zerthick.presents.present.data.PresentDataKeys;
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
        itemStack.offer(new PresentData(present));
        itemStack.offer(PresentDataKeys.PRESENT_ITEM, present);
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
