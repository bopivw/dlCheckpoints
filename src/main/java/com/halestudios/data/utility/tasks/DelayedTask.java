 package com.halestudios.data.utility;

 import com.halestudios.data.data;
 import org.bukkit.Bukkit;

 public class DelayedTask {

     public DelayedTask(Runnable runnable) {
         this(runnable, 0L);
     }

     public DelayedTask(Runnable runnable, long delay) {

         if (data.getInstance().isEnabled()) {
             int id = Bukkit.getScheduler().scheduleSyncDelayedTask(data.getInstance(), runnable, delay);
         } else {
             runnable.run();
         }
     }
 }