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
