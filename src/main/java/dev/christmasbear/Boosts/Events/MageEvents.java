package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Commands;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftTNTPrimed;
import org.bukkit.entity.*;
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
import java.util.List;

public class MageEvents implements Listener {
	private final HashMap<Player, ArrayList<TNTPrimed>> tntList = new HashMap<Player, ArrayList<TNTPrimed>>();
	
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
						player.setVelocity(player.getLocation().getDirection().multiply(-2.5f));
					} else {
						player.setVelocity(player.getLocation().getDirection().multiply(2.5f));
					}
					World world = player.getWorld();
					
					world.spawnParticle(Particle.CLOUD, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 25);
				} else if ((action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))) {
				//if (manaClass.useMana(player, true, (double) 1)) {
					Location point1 = player.getLocation();
					Block block = player.getTargetBlock(null, 100);
					Location point2 = block.getLocation();
					double space = 0.5;
					World world = point1.getWorld();
				    Validate.isTrue(point2.getWorld().equals(world), "Lines cannot be in different worlds!");
				    double distance = point1.distance(point2);
				    Vector p1 = point1.toVector();
				    Vector p2 = point2.toVector();
				    Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
				    double length = 0;
				    player.playSound(point1, Sound.BLOCK_SNOW_BREAK, 2.5f, 0);
				    for (; length < distance; p1.add(vector)) {
				        world.spawnParticle(Particle.DOLPHIN, p1.getX(), p1.getY() + 1, p1.getZ(), 20);
				        double radius = 1d;
				        Location loc = p1.toLocation(world);
				        List<Entity> near = world.getEntities();
				        for(Entity e : near) {
				            if(e.getLocation().distance(loc) <= radius) {
				            	if (!(e.equals(player))) {
			            			if (!(e instanceof Projectile || e instanceof Item || e instanceof ExperienceOrb || e instanceof CraftTNTPrimed)) {
			            				world.spawnParticle(Particle.END_ROD, e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), 10);
				            			((Damageable) e).damage(100);
				            			if (e.isDead()) {
				            				//data.addCoins(player.getUniqueId(), 2);
				            				player.sendMessage(ChatColor.GOLD + "+2 Coins");
				            			}
			            			}
				            	}
				            }
				        }
				        length += space;
					//}
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
