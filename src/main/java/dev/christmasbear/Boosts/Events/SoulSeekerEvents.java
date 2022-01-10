package dev.christmasbear.Boosts.Events;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import dev.christmasbear.Boosts.Files.DataManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoulSeekerEvents implements Listener {
    private final ArrayList<ArmorStand> souls = new ArrayList<>();
    private final Map<UUID, Double> dmgMultis = new HashMap<>();
    private final Map<UUID, Long> mainCooldown = new HashMap<>();
    private final Map<UUID, Long> altCooldown = new HashMap<>();
    public DataManager data = new DataManager(Boosts.getPlugin(Boosts.class));
    double baseDmg = data.getConfig().getDouble("kits.soulseeker.baseDmg");
    double multi = data.getConfig().getDouble("kits.soulseeker.dmgMulti");
    int heal = data.getConfig().getInt("kits.soulseeker.heal");
    int mainCooldownDuration = data.getConfig().getInt("kits.soulseeker.mainCooldown");
    int altCooldownDuration = data.getConfig().getInt("kits.soulseeker.altCooldown");
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action a = event.getAction();
        PlayerInventory inv = p.getInventory();
        if (inv.getItemInMainHand().equals(Commands.soulseekerKitItems[0])) {
            if (a.equals(Action.LEFT_CLICK_AIR)) {
                if (mainCooldown.containsKey(p.getUniqueId())) {
                    long msLeft = ((mainCooldown.get(p.getUniqueId())) + mainCooldownDuration) - (System.currentTimeMillis());
                    if (msLeft > 0) {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Ability on Cooldown! [" + msLeft + "ms]"));
                        return;
                    } else {
                        mainCooldown.put(p.getUniqueId(), System.currentTimeMillis());
                    }

                } else {
                    mainCooldown.put(p.getUniqueId(), System.currentTimeMillis());
                }
                Location loc = p.getLocation();
                Vector direction = loc.getDirection();
                for (double t=0; t < 100; t++) {
                    double adjust = (p.isSneaking()) ? 0.25 : 0.5;
                    loc.add(direction);
                    loc.add(0, adjust, 0);
                    loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc.getX(), loc.getY() + 1, loc.getZ(), 1, 0, 0, 0, Material.REDSTONE_BLOCK.createBlockData());
                    for (Entity entity : loc.getWorld().getEntities()) {
                        if (entity.getLocation().distance(loc) <= .875) {
                            if (entity != p) {
                                if (entity.getType().isAlive()) {
                                    Location eLoc = entity.getLocation().subtract(0, 1, 0);
                                    ((Damageable) entity).damage(baseDmg * (dmgMultis.getOrDefault(p.getUniqueId(), 1d)));
                                    if (entity.isDead()) {
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
                    loc.subtract(0, adjust, 0);
                }
            } else if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (altCooldown.containsKey(p.getUniqueId())) {
                    long msLeft = ((altCooldown.get(p.getUniqueId())) + altCooldownDuration) - (System.currentTimeMillis());
                    if (msLeft > 0) {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Ability on Cooldown! [" + msLeft + "ms]"));
                        return;
                    } else {
                       altCooldown.put(p.getUniqueId(), System.currentTimeMillis());
                    }

                } else {
                    altCooldown.put(p.getUniqueId(), System.currentTimeMillis());
                }
                Location loc = p.getLocation();
                Vector direction = loc.getDirection();
                for (double t=0; t < 100; t++) {
                    double adjust = (p.isSneaking()) ? 0.25 : 0.5;
                    loc.add(direction);
                    loc.add(0, adjust, 0);
                    for (Entity entity : loc.getWorld().getEntities()) {
                        if (entity.getLocation().distance(loc) <= 1) {
                            if (entity != p) {
                                if (entity instanceof ArmorStand && souls.contains((ArmorStand) entity)) {
                                    if (p.isSneaking()) {
                                        p.sendTitle("", ChatColor.RED + "❤", 5, 10, 5);
                                        p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, heal));
                                    } else {
                                        p.sendTitle("", ChatColor.DARK_RED + "⚔", 5, 10, 5);
                                        dmgMultis.put(p.getUniqueId(), (dmgMultis.containsKey(p.getUniqueId()) ? dmgMultis.get(p.getUniqueId()) * multi : multi));
                                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("" + ChatColor.RED + Math.round(dmgMultis.get(p.getUniqueId()) * 10) / 10 + "x Dmg [" + Math.round(baseDmg * dmgMultis.get(p.getUniqueId()) * 10) / 10 + "]"));
                                        Bukkit.getScheduler().scheduleSyncDelayedTask(dev.christmasbear.Boosts.Boosts.getPlugin(Boosts.class), new Runnable() {
                                            @Override
                                            public void run() {
                                                dmgMultis.put(p.getUniqueId(), dmgMultis.get(p.getUniqueId()) / multi);
                                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("" + ChatColor.RED + Math.round(dmgMultis.get(p.getUniqueId()) * 10) / 10 + "x Dmg [" + Math.round(baseDmg * dmgMultis.get(p.getUniqueId()) * 10) / 10 + "]"));
                                            }
                                        }, 200L);
                                    }
                                    entity.remove();
                                    souls.remove(entity);
                                }
                            }
                        }
                    }
                    loc.subtract(0, adjust, 0);
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
