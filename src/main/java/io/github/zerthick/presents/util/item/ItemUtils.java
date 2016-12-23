package io.github.zerthick.presents.util.item;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class ItemUtils {

    public static Text getItemDisplayName(ItemStack itemStack) {
        return itemStack.get(Keys.DISPLAY_NAME).orElse(Text.of(itemStack.getItem().getName()));
    }

}
