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
import org.bukkit.inventory.EquipmentSlot;
import org.jspecify.annotations.NonNull;
import uk.firedev.basicshowitem.ShowItemService;

@SuppressWarnings("UnstableApiUsage")
public class PluginCommand {

    private static final SimpleCommandExceptionType PLAYER_REQUIRED = new SimpleCommandExceptionType(
        new LiteralMessage("Only players can use this command.")
    );

    public static @NonNull LiteralCommandNode<CommandSourceStack> get(@NonNull ShowItemService showItemService) {
        return Commands.literal("basicshowitem")
            .requires(stack -> stack.getSender().hasPermission("basicshowitem.use"))
            .executes(ctx -> {
                Player player = requirePlayer(ctx);
                showItemService.show(player, EquipmentSlot.HAND);
                return 1;
            })
            .then(choice("hand", EquipmentSlot.HAND, showItemService))
            .then(choice("offhand", EquipmentSlot.OFF_HAND, showItemService))
            .then(choice("head", EquipmentSlot.HEAD, showItemService))
            .then(choice("chest", EquipmentSlot.CHEST, showItemService))
            .then(choice("legs", EquipmentSlot.LEGS, showItemService))
            .then(choice("feet", EquipmentSlot.FEET, showItemService))
            .build();
    }

    private static LiteralArgumentBuilder<CommandSourceStack> choice(
        @NonNull String name,
        @NonNull EquipmentSlot slot,
        @NonNull ShowItemService showItemService
    ) {
        return Commands.literal(name).executes(ctx -> {
            Player player = requirePlayer(ctx);
            showItemService.show(player, slot);
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
