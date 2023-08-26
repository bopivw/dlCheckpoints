package com.halestudios.data.utility;

import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import static com.halestudios.data.utility.ColorUtils.format;

public class RegisteredPlayer {

    @Getter
    private Player bukkitPlayer;

    public RegisteredPlayer(Player player) {
        this.bukkitPlayer = player;
    }

    public void clearEffects() {
        for (PotionEffect potionEffect : bukkitPlayer.getActivePotionEffects()) {
            bukkitPlayer.removePotionEffect(potionEffect.getType());
        }
    }

    public void clearInventory() {
        bukkitPlayer.getInventory().clear();
    }

    public void sendTitle(String title, String subtitle) {
        bukkitPlayer.sendTitle(format(bukkitPlayer, title), format(bukkitPlayer, subtitle));
        bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }
    public void sendTitle(String title, String subtitle, int fade, int stay) {
        bukkitPlayer.sendTitle(format(bukkitPlayer, title), format(bukkitPlayer, subtitle),fade,stay,fade);
        bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }
    public void heal(){
        bukkitPlayer.setHealth(20.0);
    }
    public void sendActionbar(String message) {
        TextComponent textComponent = new TextComponent(format(bukkitPlayer,message));
        bukkitPlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);

    }

}