package uk.firedev.basicshowitem;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.basicshowitem.command.PluginCommand;

import java.util.List;

public final class BasicShowItem extends JavaPlugin {

    private final Metrics metrics = new Metrics(this, 26943);

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        // Register plugin command
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(PluginCommand.get(), List.of("showitem"));
        });
    }

}
