package uk.firedev.basicshowitem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import uk.firedev.basicshowitem.tag.ItemTagResolver;

public final class ShowItemService {

    private final BasicShowItem plugin;
    private final CooldownTracker cooldown;

    public ShowItemService(@NonNull BasicShowItem plugin, long cooldownSeconds) {
        this.plugin = plugin;
        this.cooldown = new CooldownTracker(cooldownSeconds);
    }

    public boolean show(@NonNull Player player, @NonNull EquipmentSlot slot) {
        if (!Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTask(plugin, () -> show(player, slot));
            return true;
        }

        ItemStack item = player.getInventory().getItem(slot);
        if (item.isEmpty()) {
            player.sendMessage(Component.text("You have no item in that slot.", NamedTextColor.RED));
            return false;
        }

        long remaining = cooldown.remainingSeconds(player.getUniqueId());
        if (remaining > 0) {
            player.sendMessage(Component.text(
                "You can show another item in " + remaining + " seconds.",
                NamedTextColor.RED
            ));
            return false;
        }

        Component message = Component.text(player.getName(), NamedTextColor.GRAY)
            .append(Component.text(" is showing: ", NamedTextColor.GRAY))
            .append(ItemTagResolver.buildHover(item.clone()));
        Bukkit.getOnlinePlayers().forEach(recipient -> recipient.sendMessage(message));
        Bukkit.getConsoleSender().sendMessage(message);
        cooldown.markUsed(player.getUniqueId());
        return true;
    }
}
