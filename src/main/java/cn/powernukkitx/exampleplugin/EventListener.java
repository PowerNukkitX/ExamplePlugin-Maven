package cn.powernukkitx.exampleplugin;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.event.server.ServerCommandEvent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: MagicDroidX
 * NukkitExamplePlugin Project
 */
public class EventListener implements Listener {
    private final ExamplePlugin plugin;
    private AtomicInteger integer = new AtomicInteger(1);

    public EventListener(ExamplePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false) //DON'T FORGET THE ANNOTATION @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        this.plugin.getLogger().info("ServerCommandEvent is called!");
        //you can do more here!
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event) {
        event.setCancelled();
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement()+":PlayerJoinEvent");
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerAsyncPreLogin(PlayerAsyncPreLoginEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement()+":PlayerAsyncPreLoginEvent");
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement()+":PlayerLoginEvent");
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerPreLoginEvent(PlayerPreLoginEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement()+":PlayerPreLoginEvent");
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLocallyInitialized(PlayerLocallyInitializedEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement()+":PlayerLocallyInitializedEvent");
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        this.plugin.getLogger().info(integer.getAndIncrement()+":PlayerRespawnEvent");
    }
}
