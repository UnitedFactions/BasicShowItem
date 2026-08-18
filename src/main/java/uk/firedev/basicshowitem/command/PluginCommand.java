package uk.firedev.basicshowitem.command;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
                Player player = requirePlayer(ctx);
                player.chat("[item]");
                return 1;
            })
            .then(choice("hand"))
            .then(choice("offhand"))
            .then(choice("head"))
            .then(choice("chest"))
            .then(choice("legs"))
            .then(choice("feet"))
            .build();
    }

    private static LiteralArgumentBuilder<CommandSourceStack> choice(@NonNull String name) {
        return Commands.literal(name).executes(ctx -> {
            Player player = requirePlayer(ctx);
            player.chat("[" + name + "]");
            return 1;
        });
    }

    private static Player requirePlayer(@NonNull CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSender sender = ctx.getSource().getSender();
        if (!(sender instanceof Player player)) {
            throw PLAYER_REQUIRED.create();
        }
        return player;
    }

}
