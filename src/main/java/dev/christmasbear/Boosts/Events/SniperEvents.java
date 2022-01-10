package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Commands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
public class SniperEvents implements Listener {
    double dmg = 1;
    double headshotMulti = 100;
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().equals(Commands.sniperKitItems[0])) {
            if (e.getAction().equals(Action.LEFT_CLICK_AIR)) {
                Location loc = p.getLocation();
                Vector direction = loc.getDirection();
                for (double t=0; t < 100; t++) {
                    double adjust = (p.isSneaking()) ? 0.25 : 0.5;
                    loc.add(direction);
                    loc.add(0, adjust, 0);
                    loc.getWorld().spawnParticle(Particle.DOLPHIN, loc.getX(), loc.getY() + 1, loc.getZ(), 20);
                    for (Entity entity : loc.getWorld().getEntities()) {
                        if (entity.getLocation().distance(loc) <= .875) {
                            if (entity != p) {
                                if (entity.getType().isAlive()) {
                                    if (entity.getLocation().add(0, (entity.getHeight() < 1) ? 0 : entity.getHeight()-1, 0).distance(loc) <= 0.75) {
                                        p.sendTitle("", ChatColor.RED + "◎", 5, 10, 5);
                                        ((Damageable) entity).damage(dmg * headshotMulti * ((p.isSneaking()) ? 5 : 1));
                                    }
                                    ((Damageable) entity).damage(dmg * ((p.isSneaking()) ? 5 : 1));
                                }
                            }
                        }
                    }
                    loc.subtract(0, adjust, 0);
                }
            } else if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {

            }
        }
    }

    @EventHandler
    public void scope(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        PlayerInventory i = p.getInventory();
        if (i.getItemInMainHand().equals(Commands.sniperKitItems[0])) {
            if (e.isSneaking()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000000, 1000));
            }
        }
        if (!e.isSneaking()) {
            for (PotionEffect effect : p.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.SLOW)) {
                    p.removePotionEffect(PotionEffectType.SLOW);
                }
            }
        }
    }
}