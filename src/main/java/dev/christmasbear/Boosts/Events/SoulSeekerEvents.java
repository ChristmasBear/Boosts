package dev.christmasbear.Boosts.Events;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import dev.christmasbear.Boosts.Files.DataManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftTNTPrimed;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.*;

public class SoulSeekerEvents implements Listener {
    private final ArrayList<ArmorStand> souls = new ArrayList<>();
    private final Map<UUID, Double> dmgMultis = new HashMap<>();
    public DataManager data = new DataManager(Boosts.getPlugin(Boosts.class));
    double baseDmg = data.getConfig().getDouble("kits.soulseeker.baseDmg");
    double multi = data.getConfig().getDouble("kits.soulseeker.dmgMulti");
    int heal = data.getConfig().getInt("kits.soulseeker.heal");
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action a = event.getAction();
        PlayerInventory inv = p.getInventory();
        if (inv.getItemInMainHand().equals(Commands.soulseekerKitItems[0])) {
            if (a.equals(Action.LEFT_CLICK_AIR)) {
                Location point1 = p.getLocation();
                Block block = p.getTargetBlock(null, 100);
                Location point2 = block.getLocation();
                double space = 0.5;
                World world = point1.getWorld();
                Validate.isTrue(point2.getWorld().equals(world), "Lines cannot be in different worlds!");
                double distance = point1.distance(point2);
                Vector p1 = point1.toVector();
                Vector p2 = point2.toVector();
                Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
                double length = 0;
                for (; length < distance; p1.add(vector)) {
                    world.spawnParticle(Particle.BLOCK_CRACK, p1.getX(), p1.getY() + 1, p1.getZ(), 1, 0, 0, 0, Material.REDSTONE_BLOCK.createBlockData());
                    double radius = 1d;
                    Location loc = p1.toLocation(world);
                    List<Entity> near = world.getEntities();
                    for(Entity e : near) {
                        if(e.getLocation().distance(loc) <= radius) {
                            if (!(e.equals(p))) {
                                if (!(e instanceof Projectile || e instanceof Item || e instanceof ExperienceOrb || e instanceof CraftTNTPrimed)) {
                                    Location eLoc = e.getLocation().subtract(0, 1, 0);
                                    ((Damageable) e).damage(baseDmg * (dmgMultis.getOrDefault(p.getUniqueId(), 1d)));
                                    if (e.isDead()) {
                                        ArmorStand armorStand = (ArmorStand) eLoc.getWorld().spawnEntity(eLoc, EntityType.ARMOR_STAND);
                                        armorStand.setVisible(false);
                                        armorStand.setInvulnerable(true);
                                        armorStand.setGravity(false);
                                        armorStand.setHelmet(makeSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQ1ZjRkMTM5YzllODkyNjJlYzA2YjI3YWFhZDczZmE0ODhhYjQ5MjkwZDJjY2Q2ODVhMjU1NDcyNTM3M2M5YiJ9fX0="));
                                        souls.add(armorStand);
                                    }
                                }
                            }
                        }
                    }
                    length += space;
                }
            } else if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
                Location point1 = p.getLocation();
                Block block = p.getTargetBlock(null, 100);
                Location point2 = block.getLocation();
                double space = 0.5;
                World world = point1.getWorld();
                Validate.isTrue(point2.getWorld().equals(world), "Lines cannot be in different worlds!");
                double distance = point1.distance(point2);
                Vector p1 = point1.toVector();
                Vector p2 = point2.toVector();
                Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
                double length = 0;
                for (; length < distance; p1.add(vector)) {
                    double radius = 1d;
                    Location loc = p1.toLocation(world);
                    List<Entity> near = world.getEntities();
                    for(Entity e : near) {
                        if (e.getLocation().distance(loc) <= radius) {
                            if (e instanceof ArmorStand && souls.contains((ArmorStand) e)) {
                                if (p.isSneaking()) {
                                    p.sendTitle("", ChatColor.RED + "❤", 5, 10, 5);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, heal));
                                } else {
                                    p.sendTitle("", ChatColor.DARK_RED + "⚔", 5, 10, 5);
                                    dmgMultis.put(p.getUniqueId(), (dmgMultis.containsKey(p.getUniqueId()) ? dmgMultis.get(p.getUniqueId()) * multi : multi));
                                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("" + ChatColor.RED + Math.floor(dmgMultis.get(p.getUniqueId())) + "x Dmg"));
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(dev.christmasbear.Boosts.Boosts.getPlugin(Boosts.class), new Runnable() {
                                        @Override
                                        public void run() {
                                            dmgMultis.put(p.getUniqueId(), dmgMultis.get(p.getUniqueId()) / multi);
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("" + ChatColor.RED + Math.floor(dmgMultis.get(p.getUniqueId())) + "x Dmg"));
                                        }
                                    }, 200L);
                                }
                                e.remove();
                                souls.remove(e);
                            }
                        }
                    }
                    length += space;
                }
            }
        }
    }
    private ItemStack makeSkull(String base64EncodedString) {
        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64EncodedString));
        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(meta);
        return skull;
    }
}
