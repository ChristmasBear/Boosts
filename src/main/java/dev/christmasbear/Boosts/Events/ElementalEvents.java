package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ElementalEvents implements Listener {
    private final ArrayList<FallingBlock> fallingIce = new ArrayList<>();
    private final HashMap<FallingBlock, Location> circleFallingIce = new HashMap<>();
    private final HashMap<UUID, ArrayList<Boolean>> entityElements = new HashMap<>();
    private final String pyro = ChatColor.GOLD + "♨";
    private final String cryo = ChatColor.AQUA + "❄";

    @EventHandler
    public void onInteract(PlayerInteractEvent e) throws InterruptedException {
        ItemStack iceWeapon = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta weaponMeta = (PotionMeta) iceWeapon.getItemMeta();
        weaponMeta.setBasePotionData(new PotionData(PotionType.SPEED));
        weaponMeta.setDisplayName("Elemental Wand");
        iceWeapon.setItemMeta(weaponMeta);

        Player p = e.getPlayer();
        Action a = e.getAction();
        PlayerInventory inv = p.getInventory();
        boolean leftClick = (a.equals(Action.LEFT_CLICK_AIR) || a.equals(Action.LEFT_CLICK_BLOCK));
        if (inv.getItemInMainHand().equals(Commands.elementalKitItems[0])) {
            if (leftClick) {
                if (!p.isSneaking()) {
                    Location loc = p.getLocation();
                    Vector direction = loc.getDirection();
                    for (double t=0; t < 50; t++) {
                        double adjust = (p.isSneaking()) ? 0.25 : 0.5;
                        loc.add(direction);
                        loc.add(0, adjust, 0);
                        loc.getWorld().spawnParticle(Particle.FLAME, loc.getX(), loc.getY() + 1, loc.getZ(), 1, 0.05, 0.05, 0.05, 0);
                        for (Entity entity : loc.getWorld().getEntities()) {
                            if (entity.getLocation().distance(loc) <= .875) {
                                if (entity != p && entity.getType().isAlive()) {
                                    entityElements.computeIfAbsent(entity.getUniqueId(), k -> new ArrayList<>());
                                    if (!entityElements.get(entity.getUniqueId()).contains(true)) entityElements.get(entity.getUniqueId()).add(true);
                                    displayElements(entity);
                                    if (entityElements.get(entity.getUniqueId()).contains(false)) {
                                        Location newRndLocation = entity.getLocation().add(Math.random() - 0.5, Math.random() - 1.5, Math.random() - 0.5);
                                        ArmorStand reaction = entity.getWorld().spawn(newRndLocation, ArmorStand.class);
                                        reaction.setGravity(false);
                                        reaction.setInvulnerable(true);
                                        reaction.setInvisible(true);
                                        reaction.setCanPickupItems(false);
                                        reaction.setCustomNameVisible(true);
                                        reaction.setCustomName("" + ChatColor.GOLD + ChatColor.BOLD + "Melt");
                                        Bukkit.getScheduler().scheduleSyncDelayedTask(Boosts.getPlugin(Boosts.class), reaction::remove, 30L);

                                        ((Damageable) entity).damage(5 * 10);
                                    } else {
                                        ((Damageable) entity).damage(5);
                                    }
                                }
                            }
                        }
                        loc.subtract(0, adjust, 0);
                    }
                } else {
                    Location loc = p.getLocation();
                    Vector direction = loc.getDirection();
                    for (double t = 0; t < 10; t++) {
                        double adjust = (p.isSneaking()) ? 0.25 : 0.5;
                        loc.add(direction);
                        loc.add(0, adjust, 0);

                        Vector offset = p.getLocation().getDirection().clone().multiply(Math.cos(90) * 1);
                        offset.setY(Math.sin(90) * 1);
                        loc.add(offset);
                        loc.getWorld().spawnParticle(Particle.FLAME, loc.getX(), loc.getY()+1, loc.getZ(), 1, 0.05, 0.05, 0.05, 0);
                        loc.subtract(offset);

                        /*float radius = 1f;
                        float angle = 0f;
                        float angleInc = 0.5f;
                        for (;angle < Math.PI * 2 * radius / angleInc; angle += angleInc) {
                            double x = (radius * Math.sin(angle));
                            double z = (radius * Math.cos(angle));
                            Vector offset = p.getLocation().getDirection().clone().multiply(Math.cos(angle) * radius);
                            offset.setY(Math.sin(angle) * radius);
                            loc.add(offset);
                            loc.getWorld().spawnParticle(Particle.FLAME, loc.getX()+x, loc.getY()+1, loc.getZ()+z, 1, 0.05, 0.05, 0.05, 0);
                            loc.subtract(offset);
                        }*/

                    }
                }
            }
        } else if (inv.getItemInMainHand().equals(iceWeapon)) {
            if (leftClick) {
                Location loc = p.getLocation();
                Vector direction = loc.getDirection();
                direction.setY(0);
                final double[] t = {0};
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (t[0] < 20) {
                            loc.add(direction);
                            FallingBlock ice = loc.getWorld().spawnFallingBlock(loc, Material.ICE, (byte) 0);
                            ice.setVelocity(new Vector(0, 0.5, 0));
                            fallingIce.add(ice);
                            /*Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(185, 232, 234), 3.0f);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.spawnParticle(Particle.REDSTONE, ice.getLocation(), 10, dustOptions);

                                }
                            }.runTaskLater(Boosts.getPlugin(Boosts.class), 15L);*/

                        } else {
                            cancel();
                        }
                        t[0] += 1;
                    }
                }.runTaskTimer(Boosts.getPlugin(Boosts.class), 0L, 1L);
            } else if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
                float radius = 1f;
                float angle = 0f;
                float angleInc = 0.5f;
                Location loc = p.getLocation();
                for (;angle < Math.PI * 2 * radius / angleInc; angle += angleInc) {
                    double x = (radius * Math.sin(angle));
                    double z = (radius * Math.cos(angle));
                    FallingBlock ice = loc.getWorld().spawnFallingBlock(new Location(loc.getWorld(), loc.getX()+x, loc.getY()+1, loc.getZ()+z), Material.ICE, (byte) 0);
                    ice.setVelocity(ice.getLocation().toVector().subtract(loc.toVector()).multiply(0.25));
                    circleFallingIce.put(ice, loc);
                }
            }
        }
    }

    private void displayElements(Entity e) {
        StringBuilder displayName = new StringBuilder();
        for (Boolean bool : entityElements.get(e.getUniqueId())) {
            displayName.append((bool) ? pyro : cryo);
        }
        e.setCustomNameVisible(true);
        e.setCustomName(displayName.toString());
    }

    @EventHandler
    public void entityChangeBlockEvent(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof FallingBlock && fallingIce.contains(e.getEntity())) {
            e.setCancelled(true);
            fallingIce.remove(e.getEntity());
            for (Entity entity : e.getEntity().getWorld().getNearbyEntities(e.getEntity().getLocation(), 1, 1, 1)) {
                if (entity.getType().isAlive() && !(entity instanceof Player)) {
                    applyCryo(entity);
                }
            }
        } else if (e.getEntity() instanceof FallingBlock && circleFallingIce.containsKey(e.getEntity())) {
            e.setCancelled(true);
            double radius = e.getEntity().getLocation().distance(circleFallingIce.get(e.getEntity()));
            for (Entity entity : e.getEntity().getWorld().getEntities()) {
                if (entity.getLocation().distance(circleFallingIce.get(e.getEntity())) <= radius+0.25) {
                    if (entity.getType().isAlive() && !(entity instanceof Player)) {
                        applyCryo(entity);
                    }
                }
            }
            circleFallingIce.remove(e.getEntity());
        }
    }

    private void applyCryo(Entity entity) {
        entityElements.computeIfAbsent(entity.getUniqueId(), k -> new ArrayList<>());
        if (!entityElements.get(entity.getUniqueId()).contains(false)) entityElements.get(entity.getUniqueId()).add(false);
        displayElements(entity);
        if (entityElements.get(entity.getUniqueId()).contains(true)) {
            Location newRndLocation = entity.getLocation().add(Math.random() - 0.5, Math.random() - 1.5, Math.random() - 0.5);
            ArmorStand reaction = entity.getWorld().spawn(newRndLocation, ArmorStand.class);
            reaction.setGravity(false);
            reaction.setInvulnerable(true);
            reaction.setInvisible(true);
            reaction.setCanPickupItems(false);
            reaction.setCustomNameVisible(true);
            reaction.setCustomName("" + ChatColor.GOLD + ChatColor.BOLD + "Melt");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Boosts.getPlugin(Boosts.class), reaction::remove, 30L);

            ((Damageable) entity).damage(5 * 10);
        } else {
            ((Damageable) entity).damage(5);
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
        ItemStack iceWeapon = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta weaponMeta = (PotionMeta) iceWeapon.getItemMeta();
        weaponMeta.setBasePotionData(new PotionData(PotionType.SPEED));
        weaponMeta.setDisplayName("Elemental Wand");
        iceWeapon.setItemMeta(weaponMeta);
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        for (int i = 0; i < 36;) {
            ItemStack item = inv.getItem(i);
            if (item != null) {
                if (p.isSneaking() && item.equals(iceWeapon)) inv.setItem(i, Commands.elementalKitItems[0]);
                else if (!p.isSneaking() && item.equals(Commands.elementalKitItems[0])) inv.setItem(i, iceWeapon);
            }
            i++;
        }
    }
}
