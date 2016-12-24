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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class SetCoalSenderExecutor extends AbstractCommandExecutor {

    public SetCoalSenderExecutor(Presents plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        Optional<String> senderOptional = args.getOne(CommandArgs.SENDER);

        if (senderOptional.isPresent()) {
            plugin.setCoalSender(senderOptional.get());
            src.sendMessage(Text.of(TextColors.BLUE, "Successfully set the coal sender to ",
                    TextColors.AQUA, senderOptional.get()));
        }

        return CommandResult.success();
    }
}
