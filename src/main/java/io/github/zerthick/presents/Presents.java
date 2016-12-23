package io.github.zerthick.presents;

import com.google.inject.Inject;
import io.github.zerthick.presents.cmd.CommandRegister;
import io.github.zerthick.presents.present.Present;
import io.github.zerthick.presents.present.PresentDeliveryLocationManager;
import io.github.zerthick.presents.present.PresentManager;
import io.github.zerthick.presents.present.RandomPresentManager;
import io.github.zerthick.presents.present.data.ImmutablePresentData;
import io.github.zerthick.presents.present.data.PresentData;
import io.github.zerthick.presents.present.data.PresentDataKeys;
import io.github.zerthick.presents.present.data.PresentDataManipulatorBuilder;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Plugin(
        id = "presents",
        name = "Presents"
)
public class Presents {

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer instance;

    private PresentManager presentManager;
    private RandomPresentManager randomPresentManager;
    private PresentDeliveryLocationManager presentDeliveryLocationManager;
    private NaughtyListManager naughtyListManager;

    private CommandRegister commandRegister;

    public Logger getLogger() {
        return logger;
    }

    public PluginContainer getInstance() {
        return instance;
    }

    @Listener
    public void onServerInit(GameInitializationEvent event) {

        //Register DataManipulator
        Sponge.getDataManager().register(PresentData.class, ImmutablePresentData.class, new PresentDataManipulatorBuilder());

        presentManager = new PresentManager(new HashMap<>());
        randomPresentManager = new RandomPresentManager(new HashMap<>());
        presentDeliveryLocationManager = new PresentDeliveryLocationManager(new HashMap<>());
        naughtyListManager = new NaughtyListManager(new HashSet<>());

        //Register commands
        commandRegister = new CommandRegister(this);
        commandRegister.registerCmds();
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

        // Log Start Up to Console
        getLogger().info(
                instance.getName() + " version " + instance.getVersion().orElse("")
                        + " enabled!");
    }

    @Listener
    public void onItemInteract(InteractItemEvent.Secondary event, @Root Player player, @Getter("getItemStack") ItemStackSnapshot itemStackSnapshot) {
        if(itemStackSnapshot.getType() == ItemTypes.CHEST) {
            itemStackSnapshot.get(PresentDataKeys.PRESENT_ITEM).ifPresent(itemStack -> {
                player.setItemInHand(event.getHandType(), itemStack);
                event.setCancelled(true);
            });
        }
    }

    public void deliverPresents() {
        presentDeliveryLocationManager.getPresentDeliveryLocations().entrySet().parallelStream().forEach(uuidLocationEntry -> {
            UUID receiverUUID = uuidLocationEntry.getKey();
            Location<World> deliveryLocation = uuidLocationEntry.getValue();

            if(deliveryLocation.getBlock().getType() == BlockTypes.AIR) {
                deliveryLocation.setBlockType(BlockTypes.CHEST, Cause.of(NamedCause.source(instance)));
                deliveryLocation.getTileEntity().ifPresent(tileEntity -> {
                    Chest chest = (Chest) tileEntity;

                    //Give player-sent presents
                    presentManager.removePresents(receiverUUID)
                            .ifPresent(presents -> presents.forEach(present -> chest.getInventory().offer(present.toItemStack())));

                    //Give random-presents
                    if(naughtyListManager.isNaughty(receiverUUID)) {
                        //Naughty players get coal!
                        for(int i = 0; i < 5; i++) {
                            chest.getInventory().offer(ItemStack.of(ItemTypes.COAL, ThreadLocalRandom.current().nextInt(1, 17)));
                        }
                    } else if(!randomPresentManager.isEmpty()){
                        randomPresentManager.nextPresents(5).forEach(present -> chest.getInventory().offer(present.toItemStack()));
                    }
                });
            }
        });
    }

    public void sendPresent(ItemStack present, Player sender, User receiver, Text note) {
        presentManager.sendPresent(receiver.getUniqueId(), new Present(present, sender.getName(), receiver.getName(), note));
    }

    public void setPresentDeliveryLocation(User user, Location<World> location) {
        presentDeliveryLocationManager.setPresentDeliveryLocation(user, location);
    }

    public boolean hasPresentDeliveryLocation(User user) {
        return presentDeliveryLocationManager.hasPresentDeliveryLocation(user);
    }
}
