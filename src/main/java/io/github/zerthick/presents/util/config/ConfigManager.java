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

package io.github.zerthick.presents.util.config;

import com.google.common.reflect.TypeToken;
import io.github.zerthick.presents.NaughtyListManager;
import io.github.zerthick.presents.Presents;
import io.github.zerthick.presents.present.PresentDeliveryLocationManager;
import io.github.zerthick.presents.present.PresentManager;
import io.github.zerthick.presents.present.RandomPresentManager;
import io.github.zerthick.presents.util.config.serializers.*;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;


public class ConfigManager {

    public static void registerSerializers() {
        PresentSerializer.register();
        PresentDeliveryLocationManagerSerializer.register();
        PresentManagerSerializer.register();
        RandomPresentManagerSerializer.register();
        NaughtyListManagerSerializer.register();
    }

    public static PresentDeliveryLocationManager loadPresentDeliveryLocationManager(Presents plugin) throws IOException, ObjectMappingException {
        Path configDir = plugin.getDefaultConfigDir().resolve("DeliveryLocations.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configDir).build();

        if (Files.exists(configDir)) {
            CommentedConfigurationNode node = loader.load();
            return node.getNode("locationData").getValue(TypeToken.of(PresentDeliveryLocationManager.class), new PresentDeliveryLocationManager(new HashMap<>()));
        } else {
            return new PresentDeliveryLocationManager(new HashMap<>());
        }
    }

    public static void savePresentDeliveryLocationManager(PresentDeliveryLocationManager locationManager, Presents plugin) throws IOException, ObjectMappingException {
        Path configDir = plugin.getDefaultConfigDir().resolve("DeliveryLocations.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configDir).build();

        CommentedConfigurationNode node = loader.load();
        node.getNode("locationData").setValue(TypeToken.of(PresentDeliveryLocationManager.class), locationManager);
        loader.save(node);
    }

    public static PresentManager loadPresentManager(Presents plugin) throws IOException, ObjectMappingException {
        Path configDir = plugin.getDefaultConfigDir().resolve("Presents.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configDir).build();

        if (Files.exists(configDir)) {
            CommentedConfigurationNode node = loader.load();
            return node.getNode("presentData").getValue(TypeToken.of(PresentManager.class), new PresentManager(new HashMap<>()));
        } else {
            return new PresentManager(new HashMap<>());
        }
    }

    public static void savePresentManager(PresentManager presentManager, Presents plugin) throws IOException, ObjectMappingException {
        Path configDir = plugin.getDefaultConfigDir().resolve("Presents.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configDir).build();

        CommentedConfigurationNode node = loader.load();
        node.getNode("presentData").setValue(TypeToken.of(PresentManager.class), presentManager);
        loader.save(node);
    }

    public static RandomPresentManager loadRandomPresentManager(Presents plugin) throws IOException, ObjectMappingException {
        Path configDir = plugin.getDefaultConfigDir().resolve("RandomPresents.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configDir).build();

        if (Files.exists(configDir)) {
            CommentedConfigurationNode node = loader.load();
            return node.getNode("randomPresentData").getValue(TypeToken.of(RandomPresentManager.class), new RandomPresentManager(new HashMap<>(), 5, "The Grinch"));
        } else {
            return new RandomPresentManager(new HashMap<>(), 5, "The Grinch");
        }
    }

    public static void saveRandomPresentManager(RandomPresentManager randomPresentManager, Presents plugin) throws IOException, ObjectMappingException {
        Path configDir = plugin.getDefaultConfigDir().resolve("RandomPresents.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configDir).build();

        CommentedConfigurationNode node = loader.load();
        node.getNode("randomPresentData").setValue(TypeToken.of(RandomPresentManager.class), randomPresentManager);
        loader.save(node);
    }

    public static NaughtyListManager loadNaughtyListManager(Presents plugin) throws IOException, ObjectMappingException {
        Path configDir = plugin.getDefaultConfigDir().resolve("NaughtyList.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configDir).build();

        if (Files.exists(configDir)) {
            CommentedConfigurationNode node = loader.load();
            return node.getNode("naughtyListData").getValue(TypeToken.of(NaughtyListManager.class), new NaughtyListManager(new HashSet<>()));
        } else {
            return new NaughtyListManager(new HashSet<>());
        }
    }

    public static void saveNaughtyListManager(NaughtyListManager naughtyListManager, Presents plugin) throws IOException, ObjectMappingException {
        Path configDir = plugin.getDefaultConfigDir().resolve("NaughtyList.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configDir).build();

        CommentedConfigurationNode node = loader.load();
        node.getNode("naughtyListData").setValue(TypeToken.of(NaughtyListManager.class), naughtyListManager);
        loader.save(node);
    }
}
