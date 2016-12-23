/*
 * Copyright (C) 2016  Zerthick
 *
 * This file is part of CommandBooks.
 *
 * CommandBooks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * CommandBooks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CommandBooks.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zerthick.presents.cmd;

import io.github.zerthick.presents.Presents;
import io.github.zerthick.presents.cmd.cmdexecutors.PresentsDeliverExecutor;
import io.github.zerthick.presents.cmd.cmdexecutors.PresentsDeliveryLocationExecutor;
import io.github.zerthick.presents.cmd.cmdexecutors.PresentsExecutor;
import io.github.zerthick.presents.cmd.cmdexecutors.PresentsSendExecutor;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

public class CommandRegister {

    private Presents plugin;
    private PluginContainer pluginContainer;

    public CommandRegister(Presents plugin) {
        this.plugin = plugin;
        pluginContainer = plugin.getInstance();
    }

    public void registerCmds() {

        CommandSpec presentsDeliveryLocation = CommandSpec.builder()
                .permission("presents.command.deliveryLocation")
                .arguments(GenericArguments.optional(GenericArguments.user(CommandArgs.RECEIVER)))
                .description(Text.of("Set delivery location of a use"))
                .executor(new PresentsDeliveryLocationExecutor(plugin))
                .build();

        CommandSpec presentsSend = CommandSpec.builder()
                .permission("presents.command.send")
                .arguments(GenericArguments.user(CommandArgs.RECEIVER),
                        GenericArguments.optional(GenericArguments.remainingJoinedStrings(CommandArgs.NOTE)))
                .description(Text.of("Send the item held in you hand as a present"))
                .executor(new PresentsSendExecutor(plugin))
                .build();

        CommandSpec presentsDeliver = CommandSpec.builder()
                .permission("presents.command.deliver")
                .description(Text.of("Delivers all presents across the world"))
                .executor(new PresentsDeliverExecutor(plugin))
                .build();

        CommandSpec presents = CommandSpec.builder()
                .permission("presents.command")
                .executor(new PresentsExecutor(plugin))
                .child(presentsDeliver, "deliver")
                .child(presentsSend, "send")
                .child(presentsDeliveryLocation, "deliveryLocation", "devloc")
                .build();

        Sponge.getCommandManager().register(plugin, presents, "presents", "gifts");
    }
}
