package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import dev.christmasbear.Boosts.Files.DataManager;
import org.bukkit.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class MageEvents implements Listener {
	private final HashMap<Player, ArrayList<TNTPrimed>> tntList = new HashMap<Player, ArrayList<TNTPrimed>>();
	DataManager data = new DataManager(Boosts.getPlugin(Boosts.class));
	private final double baseDmg = data.getConfig().getDouble("kits.mage.baseDmg");
	private final double boostPow = data.getConfig().getDouble("kits.mage.boostPow");
	private final int range = data.getConfig().getInt("kits.mage.range");
	//public SQLGetter data;
	
	//ManaClass manaClass = new ManaClass();
	Commands commands = new Commands();
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) throws InterruptedException {
		//this.data = new SQLGetter(Boosts.getPlugin(Boosts.class));
		Action action = event.getAction();
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		
		if (inv.getItemInMainHand().equals(Commands.mageKitItems[0])) {
			if ((action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {
				//if (manaClass.useMana(player, true, (double) 5)) {
					if (player.isSneaking()) {
						player.setVelocity(player.getLocation().getDirection().multiply(-boostPow));
					} else {
						player.setVelocity(player.getLocation().getDirection().multiply(boostPow));
					}
					World world = player.getWorld();
					
					world.spawnParticle(Particle.CLOUD, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 25);
				} else if ((action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))) {
				Location loc = player.getLocation();
				Vector direction = loc.getDirection();
				player.playSound(loc, Sound.BLOCK_SNOW_BREAK, 2.5f, 0);
				for (double t=0; t < range; t++) {
					double adjust = (player.isSneaking()) ? 0.25 : 0.5;
					loc.add(direction);
					loc.add(0, adjust, 0);
					loc.getWorld().spawnParticle(Particle.DOLPHIN, loc.getX(), loc.getY() + 1, loc.getZ(), 20);
					for (Entity entity : loc.getWorld().getEntities()) {
						if (entity.getLocation().distance(loc) <= .875) {
							if (entity != player) {
								if (entity.getType().isAlive()) {
									loc.getWorld().spawnParticle(Particle.END_ROD, loc.getX(), loc.getY(), loc.getZ(), 10);
									((Damageable) entity).damage(baseDmg);
									/*if (entity.isDead()) {
										player.sendMessage(ChatColor.GOLD + "+2 Coins");
									}*/
								}
							}
						}
					}
					loc.subtract(0, adjust, 0);
				}
			}
		} /*else if (inv.getItemInMainHand().getType().equals(Material.TNT)) {
			if ((action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {
				TNTPrimed tnt = (TNTPrimed) player.getWorld().spawn(player.getLocation(), TNTPrimed.class);
				tnt.setVelocity(player.getLocation().getDirection().normalize().multiply(5));
				ArrayList<TNTPrimed> newList = new ArrayList<TNTPrimed>();
				if (tntList.containsKey(player)) {
					newList = tntList.get(player);
				}
				newList.add(tnt);
				tntList.put(player, newList);
			} else if ((action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))) {
				if (tntList.containsKey(player)) {
					ArrayList<TNTPrimed> tntArray = tntList.get(player);
					for (TNTPrimed t : tntArray) {
						t.setFuseTicks(0);
					}
					tntList.remove(player);
				}
			}
		}*/
	}
	
	/*@EventHandler
	public void onExplosion(EntityExplodeEvent e) {
		if(e.getEntity().getType() == EntityType.PRIMED_TNT) {
			World world = e.getLocation().getWorld();
			world.playSound(e.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 0);
			world.spawnParticle(Particle.EXPLOSION_HUGE, e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), 15);
			e.setCancelled(true);
		}
	}*/
	
	@EventHandler
	public void sneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		boolean isSneaking = p.isSneaking();
		if (inv.getBoots() != null && inv.getBoots().equals(Commands.mageKitItems[4])) {
			if (isSneaking) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100000, 0));
			} else {
				p.removePotionEffect(PotionEffectType.SLOW_FALLING);
			}
		}
	}
	
	@EventHandler
	public void noBoots(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		PlayerInventory inv = p.getInventory();
		if (inv.getBoots() == null) {
			p.removePotionEffect(PotionEffectType.SLOW_FALLING);
		}
	}

	/*@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			PlayerInventory inv = p.getInventory();
			if (inv.getChestplate() != null && inv.getChestplate().equals(Commands.mageKitItems[2])) {
				if (p.isSneaking()) {
					//if (manaClass.useMana(p, true, (double) 15)) {
						Vector v = p.getLocation().getDirection().setY(1);
						p.setVelocity(v);
						e.setCancelled(true);
					//}
				}
			}
		}
	}*/
	
	/*@EventHandler
	public void join(PlayerJoinEvent e) {
		e.getPlayer().setAllowFlight(true);
	}*/

	@EventHandler
	public void onPlayerDoubleJump(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
			PlayerInventory inv = p.getInventory();
			if (inv.getBoots().equals(Commands.mageKitItems[4])) {
				//if (manaClass.useMana(p, true, (double) 3)) {
					Vector v = p.getLocation().getDirection().multiply(1).setY(1);
					p.setVelocity(v);
				//}
			}
		}	
	}
}
