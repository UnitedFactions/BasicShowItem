package uk.firedev.basicshowitem;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import uk.firedev.basicshowitem.tag.ItemTagResolver;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("basicshowitem.use")) {
            return;
        }
        MiniMessage mm = MiniMessage.miniMessage();
        String parsedMessage = ItemTagResolver.replaceVariables(
            mm.serialize(event.message())
        );
        Component message = mm.deserialize(parsedMessage, ItemTagResolver.get(player));
        event.message(message);
    }

}
