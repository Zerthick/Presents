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

package io.github.zerthick.presents.cmd.cmdexecutors;

import io.github.zerthick.presents.Presents;
import io.github.zerthick.presents.cmd.CommandArgs;
import io.github.zerthick.presents.util.item.ItemUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

public class PresentsCreateRandomExecutor extends AbstractCommandExecutor {

    public PresentsCreateRandomExecutor(Presents plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;

            Optional<ItemStack> itemStackOptional = player.getItemInHand(HandTypes.MAIN_HAND);
            Optional<String> senderOptional = args.getOne(CommandArgs.SENDER);
            Optional<String> noteOptional = args.getOne(CommandArgs.NOTE);

            if (itemStackOptional.isPresent()) {
                if (senderOptional.isPresent()) {
                    ItemStack presentItemStack = itemStackOptional.get();
                    String sender = senderOptional.get();
                    String note = noteOptional.orElse("");

                    plugin.createRandomPresent(presentItemStack, sender, TextSerializers.FORMATTING_CODE.deserialize(note), 1);
                    player.setItemInHand(HandTypes.MAIN_HAND, null);

                    player.sendMessage(Text.of(TextColors.BLUE, "Successfully created random present from ",
                            TextColors.AQUA, ItemUtils.getItemDisplayName(presentItemStack)));
                } else {
                    player.sendMessage(Text.of(TextColors.RED, "You must specify a sender for the random item!"));

                }
            } else {
                player.sendMessage(Text.of(TextColors.RED, "Hold the item in your main hand you wish to send!"));
            }

        } else {
            src.sendMessage(Text.of("You can't send presents from the console!"));
        }
        return CommandResult.success();
    }
}
