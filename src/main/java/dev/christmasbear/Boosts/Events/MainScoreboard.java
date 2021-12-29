/*package dev.christmasbear.Boosts.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import dev.christmasbear.Boosts.Boosts;

public class MainScoreboard implements Listener {
	private String[] scoreboardTitle = new String[] {"§eP§9O§1LLUX", "§9P§eO§1§9L§1LUX", "§1P§9O§eL§9L§1UX", "§1PO§9L§eL§9U§1X", "§1POL§9L§eU§9X", "§1POLL§9u§eX"};

	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		ScoreboardManager scoreboardManager = Bukkit.getServer().getScoreboardManager();
		Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
		@SuppressWarnings("deprecation")
		Objective objective = scoreboard.registerNewObjective("Main", "Dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Team health = scoreboard.registerNewTeam("health");
		health.addEntry("Health: ");
		health.setSuffix("");
		health.setPrefix("");
		objective.getScore("Health: ").setScore(0);
		
		new BukkitRunnable() {
			int counter = 0;
			//String finaltitle = "";
			@Override
			public void run() {
				if (!(counter < scoreboardTitle.length - 1)) {
					counter = 0;
				} else {
					counter++;
					health.setSuffix(counter + "");
				}
				objective.setDisplayName(scoreboardTitle[counter]);
				/*if (counter < split.length) {
					String letter = String.valueOf(split[counter]);
                    counter += 1;

                    finaltitle +=   letter;
                    objective.setDisplayName(finaltitle);
                    health.setSuffix(counter + "");
				} else {
					String space = "";
                    finaltitle = "";
                    for (int i = 0; i < split.length - counter; i++) {
                        space += " ";
                    }
                    objective.setDisplayName(space);
                    counter = 0;
				}
			}
		}.runTaskTimer(Boosts.getPlugin(Boosts.class), 0, 10);
		
		
		p.setScoreboard(scoreboard);
	}
}*/