package com.halestudios.data.utility.tasks;

import com.halestudios.data.data;
import com.halestudios.data.utility.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.halestudios.data.utility.ColorUtils.format;
import static com.halestudios.data.utility.TaskManager.toggleTasks;

public class ScoreboardManager {
    private static final Configuration config = data.scoreboard;
    private static Scoreboard scoreboard;
    private static Objective objective;
    private static final Map<Integer, Team> teams = new HashMap<>();

    public static void createScoreboard(Player player) {
        clearScoreboard(player);

        String title = config.getString("scoreboards." + getScoreboard() + ".title");
        List<String> lines = config.getStringList("scoreboards." + getScoreboard() + ".lines");
        org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("example", "dummy", format(player, title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            addLine(line, lines.size() - i, player);
        }
        player.setScoreboard(scoreboard);
    }

    private static void clearScoreboard(Player player) {
        if (scoreboard != null) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    private static void addLine(String text, int score, Player player) {
        String coloredText = format(player, text);
        Team team = scoreboard.registerNewTeam("line" + score);
        team.addEntry(String.valueOf(ChatColor.values()[score]));
        team.setPrefix(coloredText);
        objective.getScore(String.valueOf(ChatColor.values()[score])).setScore(score);
        teams.put(score, team);
    }

    public static void setScoreboard(String newScoreboard) {
        config.set("scoreboards.score", newScoreboard);
        config.safeSave();
        config.reload();
        toggleTasks();
    }

    public static String getScoreboard() {
        return config.getString("scoreboards.score");
    }
}
