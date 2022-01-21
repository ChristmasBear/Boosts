//uto.
package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class AngelEvents implements Listener {
    private final double baseDmg = 100;
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        PlayerInventory inv = p.getInventory();
        if (inv.getItemInMainHand().equals(Commands.angelKitItems[0])) {
            if (a.equals(Action.LEFT_CLICK_AIR) || a.equals(Action.LEFT_CLICK_BLOCK)) {
                Location loc = p.getLocation();
                Vector direction = loc.getDirection();
                ArrayList<Entity> hit = new ArrayList<>();
                for (double t=0; t < 100; t++) {
                    double adjust = (p.isSneaking()) ? 0.25 : 0.5;
                    loc.add(direction);
                    loc.add(0, adjust, 0);
                    loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc.getX(), loc.getY() + 1, loc.getZ(), 1, 0, 0, 0, Material.STONE.createBlockData());
                    for (Entity entity : loc.getWorld().getEntities()) {
                        if (entity.getType().isAlive() && entity != p && !(entity instanceof ArmorStand) && !hit.contains(entity)) {
                            if (entity.getLocation().distance(loc) <= 1.5) {
                                double dmg = baseDmg - Math.pow(entity.getLocation().distance(p.getLocation()), 2);
                                dmg = (dmg < 0) ? 0 : (Math.round(dmg * 100d) / 100d);
                                Location newRndLocation = entity.getLocation().add(Math.random() - 0.5, Math.random() - 1.5, Math.random() - 0.5);
                                ArmorStand displayDmg = loc.getWorld().spawn(newRndLocation, ArmorStand.class);
                                displayDmg.setGravity(false);
                                displayDmg.setInvulnerable(true);
                                displayDmg.setInvisible(true);
                                displayDmg.setCanPickupItems(false);
                                displayDmg.setCustomNameVisible(true);
                                displayDmg.setCustomName(((dmg < 10) ? ChatColor.GREEN : ((dmg < 20) ? ChatColor.YELLOW : ChatColor.RED)) + "" + dmg);
                                ((Damageable) entity).damage(dmg);
                                hit.add(entity);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Boosts.getPlugin(Boosts.class), displayDmg::remove, 50L);
                            }
                        }
                    }
                    loc.subtract(0, adjust, 0);
                }
            }
        }
    }
}
