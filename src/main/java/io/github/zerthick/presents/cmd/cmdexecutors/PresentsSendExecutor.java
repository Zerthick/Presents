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
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class PresentsSendExecutor extends AbstractCommandExecutor{

    public PresentsSendExecutor(Presents plugin) {
        super(plugin);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(src instanceof Player) {
            Player player = (Player) src;

            Optional<ItemStack> itemStackOptional = player.getItemInHand(HandTypes.MAIN_HAND);
            Optional<User> receiver = args.getOne(CommandArgs.RECEIVER);
            Optional<String> note = args.getOne(CommandArgs.NOTE);

            if(receiver.isPresent()) {
                if (itemStackOptional.isPresent()) {
                    ItemStack presentItemStack = itemStackOptional.get();
                    if(plugin.hasPresentDeliveryLocation(receiver.get())) {
                        plugin.sendPresent(presentItemStack, player, receiver.get(), Text.of(note.orElse("")));
                        player.setItemInHand(HandTypes.MAIN_HAND, null);
                        player.sendMessage(Text.of(TextColors.BLUE, "Successfully sent ",
                                TextColors.AQUA, ItemUtils.getItemDisplayName(presentItemStack),
                                TextColors.BLUE, " to ", TextColors.AQUA, receiver.get().getName()));

                    } else {
                        player.sendMessage(Text.of(TextColors.RED, receiver.get().getName(), " has not set a delivery location!"));
                    }
                } else {
                    player.sendMessage(Text.of(TextColors.RED, "Hold the item in your main hand you wish to send!"));
                }
            }

        } else {
            src.sendMessage(Text.of("You can't send presents from the console!"));
        }
        return CommandResult.success();
    }
}
