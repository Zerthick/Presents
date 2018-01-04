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

package io.github.zerthick.presents;

import org.spongepowered.api.entity.living.player.User;

import java.util.Set;
import java.util.UUID;

public class NaughtyListManager {

    private Set<UUID> naughtyList;

    public NaughtyListManager(Set<UUID> naughtyList) {
        this.naughtyList = naughtyList;
    }

    public void makeNice(User user) {
        makeNice(user.getUniqueId());
    }

    public void makeNice(UUID playerUUID) {
        naughtyList.remove(playerUUID);
    }

    public void makeNaughty(User user) {
        makeNaughty(user.getUniqueId());
    }

    public void makeNaughty(UUID playerUUID) {
        naughtyList.add(playerUUID);
    }

    public boolean isNaughty(User user) {
        return isNaughty(user.getUniqueId());
    }

    public boolean isNaughty(UUID playerUUID) {
        return naughtyList.contains(playerUUID);
    }

    public Set<UUID> getNaughtyList() {
        return naughtyList;
    }
}
