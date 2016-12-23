package io.github.zerthick.presents.present;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;
import java.util.UUID;

public class PresentDeliveryLocationManager {

    Map<UUID, Location<World>> presentLocations;

    public PresentDeliveryLocationManager(Map<UUID, Location<World>> presentLocations) {
        this.presentLocations = presentLocations;
    }

    public void setPresentDeliveryLocation(User user, Location<World> location) {
        presentLocations.put(user.getUniqueId(), location);
    }

    public boolean hasPresentDeliveryLocation(User user) {
        return presentLocations.containsKey(user.getUniqueId());
    }

    public Map<UUID, Location<World>> getPresentDeliveryLocations() {
        return presentLocations;
    }
}
