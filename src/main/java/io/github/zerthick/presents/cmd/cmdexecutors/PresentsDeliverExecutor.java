package io.github.zerthick.presents.cmd.cmdexecutors;

import io.github.zerthick.presents.Presents;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PresentsDeliverExecutor extends AbstractCommandExecutor{

    public PresentsDeliverExecutor(Presents plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        plugin.deliverPresents();
        src.sendMessage(Text.of(TextColors.BLUE, "Successfully delivered all presents!"));
        return CommandResult.success();
    }
}
