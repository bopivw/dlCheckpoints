package com.halestudios.data.utility;

import com.halestudios.data.data;
import com.halestudios.data.utility.tasks.BossbarManager;
import com.halestudios.data.utility.tasks.ScoreboardManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    @Getter
    private static BukkitTask manager;
    private static Boolean taskStatus = true;

    public static void stopTasks() {
        taskStatus = false;
        BossbarManager.unregister();
        Bukkit.getScheduler().cancelTasks(data.getInstance());
    }

    public static void toggleTasks() {
        if (taskStatus) {
            stopTasks();
        } else {
            startTasks();
        }
    }

    public static void startTasks() {
        taskStatus = true;
        int playersPerIteration = 5; // Adjust this number based on your server's performance
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

        manager = new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {

                if (data.scoreboard.getBoolean("scoreboards.enabled")) {
                    int startIndex = index;
                    int endIndex = Math.min(index + playersPerIteration, onlinePlayers.size());

                    for (int i = startIndex; i < endIndex; i++) {
                        Player player = onlinePlayers.get(i);
                        ScoreboardManager.createScoreboard(player);
                    }

                    index += playersPerIteration;
                    if (index >= onlinePlayers.size()) {
                        index = 0; // Reset index if we've iterated through all players
                    }
                }

                int i = data.config.getInt("int",1);
                data.config.set("int",i+1);
                data.config.safeSave();
            }
        }.runTaskTimer(data.getInstance(), 0, 20L); // Update every 2 seconds (20 ticks * 2)
    }
}