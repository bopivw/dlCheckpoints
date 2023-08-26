package com.halestudios.data.especial.guns;

import com.halestudios.data.data;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.halestudios.data.data.manager;
import static com.halestudios.data.utility.player.WorldUtils.world;

public class RegisterGuns {
    Plugin instance = data.getInstance();

    public void register() {
        manager.registerCommand(new GunsCommands());

        new Pistol(instance);

        task();
    }

    public static void task() {
        new BukkitRunnable() {
            @Override
            public void run() {

                for (Entity entity : world.getEntities()) {
                    if (entity instanceof Arrow arrow) {
                        if (!arrow.isOnGround()) {
                            Location location = arrow.getLocation();
                            arrow.getWorld().spawnParticle(Particle.SMOKE_NORMAL, location, 5, 0, 0, 0, 0.0);
                        } else {
                            arrow.remove();
                        }
                    }
                }
            }
        }.runTaskTimer(data.getInstance(), 0, 1L);
    }
}
