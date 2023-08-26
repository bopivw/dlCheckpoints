package com.halestudios.data.utility.tasks;

import com.halestudios.data.data;
import com.halestudios.data.utility.RegisteredPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static com.halestudios.data.utility.ColorUtils.format;

public class SchedulerManager {

    public static void countdownTimer(int time) {
        int delay = 20;
        for (int i = time; i >= 0; i--) {
            final int remainingTime = i;
            Bukkit.getScheduler().runTaskLater(data.getInstance(), () -> {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    RegisteredPlayer playerUtils = new RegisteredPlayer(player);
                    if (remainingTime == 0) {
                        playerUtils.sendTitle("&a&l¡RING RING!", "&e¡BUENA SUERTE!", 5, 30);
                    } else {
                        playerUtils.sendTitle(format(String.valueOf(remainingTime)), " ", 0, 30);
                    }
                }
                if (remainingTime == 0) {
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        target.playSound(target.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 1);
                        target.playSound(target.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 2);
                    }
                }
            }, (long) (time - i) * delay);
        }
    }
}
