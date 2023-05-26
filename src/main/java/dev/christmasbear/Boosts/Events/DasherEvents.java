package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Commands;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.UUID;

public class DasherEvents implements Listener {
    HashMap<UUID, Integer> dashStack = new HashMap<>();
    @EventHandler
    public void onInteract(PlayerInteractEvent e) throws InterruptedException {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        if (inv.getItemInMainHand().equals(Commands.dasherKitItems[0])) {
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (dashStack.containsKey(p.getUniqueId()) && dashStack.get(p.getUniqueId()) < 10) {
                    dashStack.put(p.getUniqueId(), dashStack.getOrDefault(p.getUniqueId(), 1) + 1);
                }
                StringBuilder sb = new StringBuilder();
                sb.append(ChatColor.AQUA).append("Charge: [");
                for (int i = 0; i < dashStack.getOrDefault(p.getUniqueId(), 1); i++) {
                    sb.append(ChatColor.GREEN).append("|");
                }
                for (int i = 0; i < 10 - dashStack.getOrDefault(p.getUniqueId(), 1); i++) {
                    sb.append(ChatColor.DARK_GRAY).append("|");
                }
                sb.append(ChatColor.AQUA).append("]");
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(sb.toString()));
            } else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                p.setVelocity(p.getLocation().getDirection().multiply(Math.log(dashStack.getOrDefault(p.getUniqueId(), 1)) * 10));
            }
        }
    }

    /*@EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        Player p = e.getPlayer();
        assert to != null;
        PlayerInventory inv = p.getInventory();
        if (inv.getItemInMainHand().equals(Commands.dasherKitItems[0])) {
            if (from.subtract(to).length() > 0.75) {
                if (p.getNearbyEntities(0.75, 0.75, 0.75).size() > 0) {
                    for (Entity entity : p.getNearbyEntities(1, 1, 1)) {
                        if (!(entity instanceof Player) && entity instanceof Damageable) {
                            ((Damageable) entity).damage(from.subtract(to).length() * 10);
                        }
                    }
                }
            }
        }

    }

    @EventHandler
    public void hotbarSwitch(PlayerItemHeldEvent e) {
        System.out.println("Switched");
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        if (Objects.requireNonNull(inv.getItem(e.getNewSlot())).equals(Commands.dasherKitItems[0])) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 255));
        } else {
            p.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }
    }*/
}
