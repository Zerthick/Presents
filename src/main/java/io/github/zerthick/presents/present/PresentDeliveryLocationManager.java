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
