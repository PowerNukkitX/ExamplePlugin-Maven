package org.powernukkitx.exampleplugin.event;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

/*
 * For player related events, use PlayerEvent
 * We call this event in ExampleCommand
 */
public class CustomEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private int tick;

    public CustomEvent(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }
}
