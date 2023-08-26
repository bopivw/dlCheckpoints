package com.halestudios.data.listeners;

import com.halestudios.data.data;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.halestudios.data.utility.ColorUtils.format;

public class World implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setFormat(format(event.getPlayer(), "%luckperms_prefix%%player_name% &8» &f%message%").replace("%message%", event.getMessage()));

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTask(data.getInstance(), () -> {
            event.setJoinMessage(format(event.getPlayer(), "&a(+) %luckperms_prefix%%player_name% &fse ha conectado"));
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTask(data.getInstance(), () -> {
            event.setQuitMessage(format(event.getPlayer(), "&c(-) %luckperms_prefix%%player_name% &fse ha desconectado"));
        });
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }
}
