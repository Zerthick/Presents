/*
 * Copyright (C) 2018  Zerthick
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

import java.util.*;

public class PresentManager {

    private Map<UUID, Set<Present>> presentMap;

    public PresentManager(Map<UUID, Set<Present>> presentMap) {
        this.presentMap = presentMap;
    }

    public void sendPresent(User receiver, Present present) {
        sendPresent(receiver.getUniqueId(), present);
    }

    public void sendPresent(UUID receiverUUID, Present present) {
        Set<Present> presents = presentMap.getOrDefault(receiverUUID, new HashSet<>());
        presents.add(present);
        presentMap.put(receiverUUID, presents);
    }

    public Optional<Set<Present>> getPresents(User receiver) {
        return getPresents(receiver.getUniqueId());
    }

    public Optional<Set<Present>> getPresents(UUID receiverUUID) {
        return Optional.ofNullable(presentMap.get(receiverUUID));
    }

    public Optional<Set<Present>> removePresents(User receiver) {
        return getPresents(receiver.getUniqueId());
    }

    public Optional<Set<Present>> removePresents(UUID receiverUUID) {
        return Optional.ofNullable(presentMap.remove(receiverUUID));
    }

    public Map<UUID, Set<Present>> getPresentMap() {
        return presentMap;
    }
}
