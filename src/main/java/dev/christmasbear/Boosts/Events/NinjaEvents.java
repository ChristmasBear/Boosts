package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class NinjaEvents implements Listener {
	public static UUID[] hiddenPlayers = new UUID[] {};
	Boolean smoke = true;
	
	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			PlayerInventory inv = p.getInventory();
			if (inv.getItemInMainHand().equals(Commands.ninjaKitItems[0])) {
				if (p.getLocation().distance(e.getEntity().getLocation()) >= 3) {
					e.setCancelled(true);
				} else {
					Location loc = e.getEntity().getLocation();
					p.getWorld().spawnParticle(Particle.SQUID_INK, loc.getX(), loc.getY() + 1, loc.getZ(), 20);
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player p = event.getPlayer();
		PlayerInventory inv = p.getInventory();
		
		if (inv.getItemInMainHand().equals(Commands.ninjaKitItems[0])) {
			if ((action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {
				smoke = true;
				Location loc = p.getLocation();
				new BukkitRunnable() {
					public void run() {
						if (smoke) {
			                p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc.getX(), loc.getY()-2, loc.getZ(), 100);
						} else this.cancel();
					}
				}.runTaskTimer(Boosts.getPlugin(Boosts.class), 0, 2L);
				
				new BukkitRunnable() {
					public void run() {
						smoke = false;
					}
				}.runTaskLater(Boosts.getPlugin(Boosts.class), 200L);
				
			}
		}
	}
}
