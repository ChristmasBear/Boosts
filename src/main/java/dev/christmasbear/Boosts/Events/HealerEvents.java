package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Commands;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class HealerEvents implements Listener {
	
	Commands commands = new Commands();
	ArrayList<Arrow> arrows = new ArrayList<>();
	@EventHandler
	public void onInteract(PlayerInteractEvent e) throws InterruptedException {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		if (inv.getItemInMainHand().equals(Commands.healerKitItems[0])) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (p.isSneaking()) {
					Double health = p.getHealth();
					if (health < 20) {
						p.setHealth(health + 5);
						p.getWorld().spawnParticle(Particle.HEART, p.getLocation(), 10, 0.5, 0.5, 0.5, 0.1);
					}
				}
			} else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				Vector playerDirection = p.getLocation().getDirection();
				Arrow arrow = p.launchProjectile(Arrow.class, playerDirection.multiply(5));
				arrow.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
			}
		}
	}

	@EventHandler
	public void onArrowHit(org.bukkit.event.entity.ProjectileHitEvent e) {
		if (e.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getEntity();
			if (arrow.getShooter() instanceof Player) {
				Player p = (Player) arrow.getShooter();
				if (arrow.getBasePotionData().getType().equals(PotionType.INSTANT_HEAL) && arrows.contains(arrow)) {
					p.getWorld().spawnParticle(Particle.HEART, p.getLocation(), 10, 0.5, 0.5, 0.5, 0.1);
					arrow.remove();
				}
			}
		}
	}

}
