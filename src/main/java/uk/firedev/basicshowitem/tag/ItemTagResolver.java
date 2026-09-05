package uk.firedev.basicshowitem.tag;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ItemTagResolver {

    public static TagResolver get(@NonNull Player player) {
        return TagResolver.resolver(
            resolver(player, "hand", EquipmentSlot.HAND),
            resolver(player, "offhand", EquipmentSlot.OFF_HAND),
            resolver(player, "head", EquipmentSlot.HEAD),
            resolver(player, "chest", EquipmentSlot.CHEST),
            resolver(player, "legs", EquipmentSlot.LEGS),
            resolver(player, "feet", EquipmentSlot.FEET)
        );
    }

    public static String replaceVariables(@NonNull String string) {
        return string
            .replace("[item]", "<hand>")
            .replace("[i]", "<hand>")
            .replace("[hand]", "<hand>")
            .replace("[offhand]", "<offhand>")
            .replace("[head]", "<head>")
            .replace("[chest]", "<chest>")
            .replace("[legs]", "<legs>")
            .replace("[feet]", "<feet>");
    }

    public static @Nullable EquipmentSlot slotForExactToken(@NonNull String message) {
        return switch (message.trim().toLowerCase()) {
            case "[i]", "[item]", "[hand]" -> EquipmentSlot.HAND;
            case "[offhand]" -> EquipmentSlot.OFF_HAND;
            case "[head]" -> EquipmentSlot.HEAD;
            case "[chest]" -> EquipmentSlot.CHEST;
            case "[legs]" -> EquipmentSlot.LEGS;
            case "[feet]" -> EquipmentSlot.FEET;
            default -> null;
        };
    }

    private static TagResolver resolver(@NonNull final Player player, @NonNull final String name, @NonNull final EquipmentSlot slot) {
        return TagResolver.resolver(name, ((argumentQueue, context) ->
            Tag.selfClosingInserting(buildHover(player.getInventory().getItem(slot)))
        ));
    }

    /**
     * Manually builds the hover event to avoid legacy chat plugins parsing the raw translation.
     * <p>
     * If a legacy chat plugin is present, the hover data will be lost, but the item name will be shown.
     */
    public static Component buildHover(@NotNull ItemStack item) {
        if (item.isEmpty()) {
            return Component.text("[Air]");
        }
        return Component.text()
            .append(Component.text("["))
            .append(retrieveDisplayName(item))
            .append(Component.text("]"))
            .hoverEvent(item)
            .build();
    }

    private static Component retrieveDisplayName(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return Component.text("Air");
        }
        Component display = meta.displayName();
        return display == null ? Component.translatable(item) : display;
    }

}
