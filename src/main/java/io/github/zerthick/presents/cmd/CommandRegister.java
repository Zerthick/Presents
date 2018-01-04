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

package io.github.zerthick.presents.cmd;

import io.github.zerthick.presents.Presents;
import io.github.zerthick.presents.cmd.cmdexecutors.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandRegister {

    public static void registerCmds(Presents plugin) {

        CommandSpec setCoalSender = CommandSpec.builder()
                .permission("presents.command.set.coalsender")
                .arguments(GenericArguments.remainingJoinedStrings(CommandArgs.SENDER))
                .description(Text.of("Set the name of the person who sends coal"))
                .executor(new SetCoalSenderExecutor(plugin))
                .build();

        CommandSpec setNaughty = CommandSpec.builder()
                .permission("presents.command.set.naughty")
                .arguments(GenericArguments.user(CommandArgs.RECEIVER), GenericArguments.bool(CommandArgs.NAUGHTY))
                .description(Text.of("Set a user to be either on the naughty or nice list"))
                .executor(new SetNaughtyExecutor(plugin))
                .build();

        CommandSpec presentsSetRandom = CommandSpec.builder()
                .permission("presents.command.set.randomdefault")
                .arguments(GenericArguments.integer(CommandArgs.AMOUNT))
                .description(Text.of("Sets the amount of random gifts given"))
                .executor(new SetDefaultRandomPresentExecutor(plugin))
                .build();

        CommandSpec presentsCreateRandom = CommandSpec.builder()
                .permission("presents.command.create.random")
                .arguments(GenericArguments.string(CommandArgs.SENDER),
                        GenericArguments.optional(GenericArguments.remainingJoinedStrings(CommandArgs.NOTE)))
                .description(Text.of("Create random present item"))
                .executor(new PresentsCreateRandomExecutor(plugin))
                .build();

        CommandSpec presentsDeliveryLocation = CommandSpec.builder()
                .permission("presents.command.set.deliverylocation.self")
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

        CommandSpec set = CommandSpec.builder()
                .permission("presents.command.set")
                .child(setNaughty, "naughty")
                .child(presentsSetRandom, "randomDefault")
                .child(presentsDeliveryLocation, "deliveryLocation")
                .child(setCoalSender, "coalSender")
                .build();

        CommandSpec presents = CommandSpec.builder()
                .permission("presents.command.info")
                .executor(new PresentsExecutor(plugin))
                .child(presentsDeliver, "deliver")
                .child(presentsSend, "send")
                .child(presentsCreateRandom, "createRandom", "rand")
                .child(set, "set")
                .build();

        Sponge.getCommandManager().register(plugin, presents, "presents", "gifts");
    }
}
