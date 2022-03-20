package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import dev.christmasbear.Boosts.Files.DataManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AngelEvents implements Listener {
    private final HashMap<UUID, ArrayList<Location>> nodes = new HashMap<>();
    private final HashMap<UUID, Location[]> nodeConnections = new HashMap<>();
    private final int pointsPerLine = 10;
    private final Long nodeLifespan = 20L;
    DataManager data = new DataManager(Boosts.getPlugin(Boosts.class));
    private final double baseDmg = data.getConfig().getDouble("kits.hacker.altCooldown");
    private final double shotgunExp = data.getConfig().getDouble("kits.angel.shotgunScaling.exp");
    private final double shotgunMulti = data.getConfig().getDouble("kits.angel.shotgunScaling.multi");
    private final int range = data.getConfig().getInt("kits.angel.range");

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
                for (double t=0; t < range; t++) {
                    double adjust = (p.isSneaking()) ? 0.25 : 0.5;
                    loc.add(direction);
                    loc.add(0, adjust, 0);
                    Objects.requireNonNull(loc.getWorld()).spawnParticle(Particle.BLOCK_CRACK, loc.getX(), loc.getY() + 1, loc.getZ(), 1, 0, 0, 0, Material.STONE.createBlockData());
                    for (Entity entity : loc.getWorld().getEntities()) {
                        if (entity.getType().isAlive() && entity != p && !(entity instanceof ArmorStand) && !hit.contains(entity)) {
                            if (entity.getLocation().distance(loc) <= 1.5) {
                                double dmg = baseDmg - Math.pow(entity.getLocation().distance(p.getLocation()), shotgunExp) * shotgunMulti;
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
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Boosts.getPlugin(Boosts.class), displayDmg::remove, 30L);
                            }
                        }
                    }
                    loc.subtract(0, adjust, 0);
                }
            } else if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
                e.setCancelled(true);
                Block block = p.getTargetBlock(null, 5);
                Location loc = block.getLocation();
                for (int i=0; i<2;) {
                    loc.add(0, 1, 0);
                    if (!loc.getBlock().getType().equals(Material.AIR)) {
                        return;
                    }
                    i++;
                }
                loc.getBlock().setType(Material.WHITE_STAINED_GLASS_PANE);
                loc.subtract(0, 1, 0);
                loc.getBlock().setType(Material.WHITE_STAINED_GLASS_PANE);
                ArrayList<Location> playerNodes = new ArrayList<>();
                if (nodes.containsKey(p.getUniqueId())) {
                    playerNodes = nodes.get(p.getUniqueId());

                    final Long[] countdown = {nodeLifespan};
                    ArrayList<Location> finalPlayerNodes = playerNodes;
                    final Location finalLoc = loc;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (countdown[0] > 0) {
                                countdown[0] -= 1L;
                                for (Location nodeLoc : finalPlayerNodes) {
                                    double distance = nodeLoc.distance(finalLoc);
                                    if (distance <= 5) {
                                        double d = distance / pointsPerLine;
                                        for (int i=0; i < pointsPerLine; i++) {
                                            Location l = finalLoc.clone();
                                            Vector direction = nodeLoc.toVector().subtract(finalLoc.toVector()).normalize();
                                            Vector vector = direction.multiply(i*d);
                                            l.add(vector);
                                            l.getWorld().spawnParticle(Particle.DOLPHIN, l.getX() + 0.5, l.getY() + 0.5, l.getZ() + 0.5, 1);
                                            l.getWorld().spawnParticle(Particle.DOLPHIN, l.getX() + 0.5, l.getY() + 1.5, l.getZ() + 0.5, 1);
                                            for (Entity entity : loc.getWorld().getEntities()) {
                                                if (entity.getLocation().distance(l) <= 0.75) {
                                                    if (entity != p) {
                                                        if (entity.getType().isAlive()) {
                                                            ((Damageable) entity).damage(100);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                finalPlayerNodes.remove(finalLoc);
                                finalLoc.getBlock().setType(Material.AIR);
                                finalLoc.add(0, 1, 0).getBlock().setType(Material.AIR);
                                nodes.put(p.getUniqueId(), finalPlayerNodes);
                                cancel();
                            }
                        }
                    }.runTaskTimer(Boosts.getPlugin(Boosts.class), 0L, 5L);
                } else {
                    ArrayList<Location> finalPlayerNodes1 = playerNodes;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Boosts.getPlugin(Boosts.class), new Runnable() {
                        @Override
                        public void run() {
                            finalPlayerNodes1.remove(loc);
                            loc.getBlock().setType(Material.AIR);
                            loc.add(0, 1, 0).getBlock().setType(Material.AIR);
                            nodes.put(p.getUniqueId(), finalPlayerNodes1);
                        }
                    }, nodeLifespan * 20L);
                }

                playerNodes.add(loc);
                nodes.put(p.getUniqueId(), playerNodes);
                e.setCancelled(true);
            }
        }
    }
}
