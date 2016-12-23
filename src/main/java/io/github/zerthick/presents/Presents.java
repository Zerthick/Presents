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

package io.github.zerthick.presents;

import com.google.inject.Inject;
import io.github.zerthick.presents.cmd.CommandRegister;
import io.github.zerthick.presents.present.Present;
import io.github.zerthick.presents.present.PresentDeliveryLocationManager;
import io.github.zerthick.presents.present.PresentManager;
import io.github.zerthick.presents.present.RandomPresentManager;
import io.github.zerthick.presents.present.data.PresentData;
import io.github.zerthick.presents.present.data.PresentDataKeys;
import io.github.zerthick.presents.util.config.ConfigManager;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.nio.file.Path;
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

    //Config Stuff
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path defaultConfigDir;

    private PresentManager presentManager;
    private RandomPresentManager randomPresentManager;
    private PresentDeliveryLocationManager presentDeliveryLocationManager;
    private NaughtyListManager naughtyListManager;

    public Logger getLogger() {
        return logger;
    }

    public PluginContainer getInstance() {
        return instance;
    }

    public Path getDefaultConfigDir() {
        return defaultConfigDir;
    }

    @Listener
    public void onServerInit(GameInitializationEvent event) {

        //Register DataManipulator
        PresentData.registerData();

        ConfigManager.registerSerializers();
        try {
            presentManager = ConfigManager.loadPresentManager(this);
            randomPresentManager = ConfigManager.loadRandomPresentManager(this);
            presentDeliveryLocationManager = ConfigManager.loadPresentDeliveryLocationManager(this);
            naughtyListManager = ConfigManager.loadNaughtyListManager(this);
        } catch (IOException | ObjectMappingException e) {
            logger.error("Error loading configs! Error: " + e.getMessage());
        }

        //Register commands
        CommandRegister.registerCmds(this);
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {

        try {
            ConfigManager.savePresentManager(presentManager, this);
            ConfigManager.saveRandomPresentManager(randomPresentManager, this);
            ConfigManager.savePresentDeliveryLocationManager(presentDeliveryLocationManager, this);
            ConfigManager.saveNaughtyListManager(naughtyListManager, this);
        } catch (IOException | ObjectMappingException e) {
            logger.error("Error saving configs! Error: " + e.getMessage());
        }

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
        if (itemStackSnapshot.getType() == ItemTypes.CHEST_MINECART) {
            itemStackSnapshot.get(PresentDataKeys.PRESENT_ITEM).ifPresent(presentItemStackSnapshot -> {
                player.setItemInHand(event.getHandType(), presentItemStackSnapshot.createStack());
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
                            ItemStackSnapshot coalItemStack = ItemStack.of(ItemTypes.COAL, ThreadLocalRandom.current().nextInt(1, 17)).createSnapshot();
                            chest.getInventory().offer(new Present(coalItemStack, "Santa Clause", "").toItemStack());
                        }
                    } else if(!randomPresentManager.isEmpty()){
                        randomPresentManager.nextPresentItems(5).forEach(presentItem -> chest.getInventory().offer(new Present(presentItem, "Santa Clause", "").toItemStack()));
                    }
                });
            }
        });
    }

    public void sendPresent(ItemStack present, Player sender, User receiver, Text note) {
        presentManager.sendPresent(receiver.getUniqueId(), new Present(present.createSnapshot(), sender.getName(), receiver.getName(), note));
    }

    public void setPresentDeliveryLocation(User user, Location<World> location) {
        presentDeliveryLocationManager.setPresentDeliveryLocation(user, location);
    }

    public boolean hasPresentDeliveryLocation(User user) {
        return presentDeliveryLocationManager.hasPresentDeliveryLocation(user);
    }

    public void createRandomPresent(ItemStack present, double weight) {
        randomPresentManager.addPresent(present.createSnapshot(), weight);
    }

    public void setUserNaughty(User user, boolean naughty) {
        if (naughty) {
            naughtyListManager.makeNaughty(user);
        } else {
            naughtyListManager.makeNice(user);
        }
    }
}
