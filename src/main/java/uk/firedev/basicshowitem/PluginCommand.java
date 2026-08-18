package uk.firedev.basicshowitem;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("UnstableApiUsage")
public class PluginCommand {

    private static final SimpleCommandExceptionType PLAYER_REQUIRED = new SimpleCommandExceptionType(
        new LiteralMessage("Only players can use this command.")
    );

    public static @NonNull LiteralCommandNode<CommandSourceStack> get() {
        return Commands.literal("basicshowitem")
            .requires(stack -> stack.getSender().hasPermission("basicshowitem.use"))
            .executes(ctx -> {
                CommandSender sender = ctx.getSource().getSender();
                if (!(sender instanceof Player player)) {
                    throw PLAYER_REQUIRED.create();
                }
                player.chat("[item]");
                return 1;
            })
            .build();
    }

}
