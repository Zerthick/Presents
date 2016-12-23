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
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class PresentsDeliveryLocationExecutor extends AbstractCommandExecutor{

    public PresentsDeliveryLocationExecutor(Presents plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {

            Player player = (Player) src;
            Optional<User> receiver = args.getOne(CommandArgs.RECEIVER);
            if(player.hasPermission("presents.command.deliverylocation.other")) {
                plugin.setPresentDeliveryLocation(receiver.orElse(player), player.getLocation());
            } else {
                plugin.setPresentDeliveryLocation(player, player.getLocation());
            }
            player.sendMessage(Text.of(TextColors.BLUE, "Delivery location set!"));

        } else {
            src.sendMessage(Text.of("You can't send present delivery locations the console!"));
        }
        return CommandResult.success();
    }
}
