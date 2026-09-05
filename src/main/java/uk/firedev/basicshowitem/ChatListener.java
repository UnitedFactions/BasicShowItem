package uk.firedev.basicshowitem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.EquipmentSlot;
import uk.firedev.basicshowitem.tag.ItemTagResolver;

public class ChatListener implements Listener {

    private final BasicShowItem plugin;
    private final ShowItemService showItemService;

    public ChatListener(BasicShowItem plugin, ShowItemService showItemService) {
        this.plugin = plugin;
        this.showItemService = showItemService;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("basicshowitem.use")) {
            return;
        }
        EquipmentSlot slot = ItemTagResolver.slotForExactToken(event.getMessage());
        if (slot == null) {
            return;
        }
        event.setCancelled(true);
        Bukkit.getScheduler().runTask(plugin, () -> showItemService.show(player, slot));
    }

}
