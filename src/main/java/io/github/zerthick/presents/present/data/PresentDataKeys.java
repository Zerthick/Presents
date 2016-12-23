package io.github.zerthick.presents.present.data;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.inventory.ItemStack;

public class PresentDataKeys {
    public static final Key<Value<ItemStack>> PRESENT_ITEM = KeyFactory.makeSingleKey(TypeToken.of(ItemStack.class),
            new TypeToken<Value<ItemStack>>(){}, DataQuery.of("PresentItem"),"presents:present_item", "Present Item");
}
