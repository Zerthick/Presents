package io.github.zerthick.presents;

import org.spongepowered.api.entity.living.player.Player;
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

    public void makeNaughty(Player player) {
        makeNaughty(player.getUniqueId());
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
