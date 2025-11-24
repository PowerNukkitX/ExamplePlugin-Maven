package org.powernukkitx.exampleplugin.event;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.server.ServerCommandEvent;
import cn.nukkit.scoreboard.Scoreboard;
import cn.nukkit.scoreboard.data.DisplaySlot;
import org.powernukkitx.exampleplugin.ExamplePlugin;
import org.powernukkitx.exampleplugin.custom.entity.MyHuman;
import org.powernukkitx.exampleplugin.custom.entity.MyPig;

import java.util.concurrent.atomic.AtomicInteger;

public class EventListener implements Listener {
    private final ExamplePlugin plugin;
    private final AtomicInteger integer = new AtomicInteger(1);

    public EventListener(ExamplePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(PlayerChatEvent event) {
        if (event.getMessage().contains("test")) {
            System.out.println("spawn custom entities");
            new MyPig(event.getPlayer().getChunk(), Entity.getDefaultNBT(event.getPlayer())).spawnToAll();
            new MyHuman(event.getPlayer().getChunk(), Entity.getDefaultNBT(event.getPlayer())).spawnToAll();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false) //DON'T FORGET THE ANNOTATION @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        this.plugin.getLogger().info("ServerCommandEvent is called!");
        //you can do more here!
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement() + ":PlayerJoinEvent");
        /*
         * Example scoreboard
         */
        Scoreboard scoreboard = new Scoreboard("dummy", "Example Scoreboard");
        scoreboard.addLine("First line", 1);
        scoreboard.addLine("Second line", 2);
        scoreboard.addLine("Third line", 3);
        scoreboard.addViewer(event.getPlayer(), DisplaySlot.SIDEBAR);
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement() + ":PlayerLoginEvent");
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement() + ":PlayerRespawnEvent");
    }

    @EventHandler
    public void onCustomEvent(CustomEvent event) {
        this.plugin.getLogger().info("Custom event was called on tick " + event.getTick());
    }
}
