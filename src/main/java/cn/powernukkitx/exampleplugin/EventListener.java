package cn.powernukkitx.exampleplugin;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.server.ServerCommandEvent;
import cn.powernukkitx.exampleplugin.customentity.MyHuman;
import cn.powernukkitx.exampleplugin.customentity.MyPig;

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
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement() + ":PlayerLoginEvent");
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement() + ":PlayerRespawnEvent");
    }
}
