package com.halestudios.data.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class fly extends BaseCommand {


    @CommandAlias("fly")
    @CommandPermission("hale.fly")
    public void fly(Player player){
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage("Flight mode disabled.");
        } else {
            player.setAllowFlight(true);
            player.sendMessage("Flight mode enabled. You can now fly!");
        }
    }

    @CommandAlias("tpall")
    @CommandPermission("hale.tpall")
    public void tpall(Player player){
        Bukkit.getServer().getOnlinePlayers().forEach(target -> target.teleport(player.getLocation()));
        player.sendMessage("Teleported all players to your location.");
    }

    @CommandAlias("speed")
    @CommandPermission("hale.speed")
    public void walkspeed(Player player, int speed){
        if (speed < 1 || speed > 10) {
            player.sendMessage("Speed value must be between 1 and 10.");
            return;
        }
        player.setWalkSpeed(speed / 10f);
        player.sendMessage("Your walk speed has been set to " + speed);
    }

    @CommandAlias("flyspeed")
    @CommandPermission("hale.flyspeed")
    public void flyspeed(Player player, int speed){
        if (speed < 1 || speed > 10) {
            player.sendMessage("Fly speed value must be between 1 and 10.");
            return;
        }
        player.setFlySpeed(speed / 10f);
        player.sendMessage("Your fly speed has been set to " + speed);
    }
}