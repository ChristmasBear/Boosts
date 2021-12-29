package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import dev.christmasbear.Boosts.Files.DataManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HackerEvents implements Listener {
    private final Map<UUID, Integer> hackLevels = new HashMap<>();
    private final HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    DataManager data = new DataManager(Boosts.getPlugin(Boosts.class));
    private final int cooldownTime = data.getConfig().getInt("kits.hacker.altCooldown");
    private final Double basePow = data.getConfig().getDouble("kits.hacker.basePow");
    private final int slowDuration = data.getConfig().getInt("kits.hacker.buffs.slow.dur");
    private final int slowAmp = data.getConfig().getInt("kits.hacker.buffs.slow.amp");
    private final int slowLvl = data.getConfig().getInt("kits.hacker.buffs.slow.lvl");
    private final int regenDuration = data.getConfig().getInt("kits.hacker.buffs.regen.dur");
    private final int regenAmp = data.getConfig().getInt("kits.hacker.buffs.regen.amp");
    private final int regenLvl = data.getConfig().getInt("kits.hacker.buffs.regen.lvl");
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) return;
        Player p = e.getPlayer();
        Action a = e.getAction();
        PlayerInventory inv = p.getInventory();
        if (inv.getItemInMainHand().equals(Commands.hackerKitItems[0])) {
            if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)) {
                if (cooldown.containsKey(p.getUniqueId())) {
                    long msLeft = ((cooldown.get(p.getUniqueId())) + cooldownTime) - (System.currentTimeMillis());
                    if (msLeft > 0) {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Ability on Cooldown! [" + msLeft + "ms]"));
                        return;
                    } else {
                        cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                    }

                } else {
                    cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                }
                Location blockLoc = p.getTargetBlock(null, 5).getLocation();
                List<Entity> near = (List<Entity>) blockLoc.getWorld().getNearbyEntities(blockLoc, 3, 3, 3);
                near.forEach((entity) -> {
                    if (entity instanceof Mob) {
                        int hackLevel = hackLevels.getOrDefault(entity.getUniqueId(), 0);
                        hackLevel++;
                        hackLevels.put(entity.getUniqueId(), hackLevel);
                        String customName = ChatColor.BLACK + "HACKED!" + ChatColor.GREEN + " - " + ChatColor.BLACK + "§k[" + ChatColor.GREEN + hackLevel + ChatColor.BLACK + "§k]";
                        entity.setCustomName(customName);
                        entity.setCustomNameVisible(true);
                        if (hackLevel == slowLvl) {
                            ((Mob) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, slowDuration, slowAmp));
                        }
                        int finalHackLevel = hackLevel;
                        Bukkit.getScheduler().scheduleSyncDelayedTask(dev.christmasbear.Boosts.Boosts.getPlugin(Boosts.class), () -> {
                            if (hackLevels.get(entity.getUniqueId()) != null && hackLevels.get(entity.getUniqueId()) == finalHackLevel) {
                                hackLevels.remove(entity.getUniqueId());
                                entity.setCustomName("");
                                entity.setCustomNameVisible(false);
                            }
                        }, 200L);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (hackLevels.containsKey(e.getEntity().getUniqueId()) && ((Player) e.getDamager()).getInventory().getItemInMainHand().equals(Commands.hackerKitItems[0])) {
            e.setDamage(e.getDamage() * Math.pow(basePow, hackLevels.get(e.getEntity().getUniqueId())));
            if (hackLevels.get(e.getEntity().getUniqueId()) >= regenLvl) {
                ((Player) e.getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, regenDuration, regenAmp));
            }
            hackLevels.remove(e.getEntity().getUniqueId());
            e.getEntity().setCustomName("");
            e.getEntity().setCustomNameVisible(false);
        }

    }
}
