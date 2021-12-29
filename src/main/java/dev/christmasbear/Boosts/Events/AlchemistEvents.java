package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Commands;
import dev.christmasbear.Boosts.ManaClass;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class AlchemistEvents implements Listener {
	private final ArrayList<ThrownPotion> dmgPotions = new ArrayList<ThrownPotion>();
	private final ArrayList<ThrownPotion> buffPotions = new ArrayList<ThrownPotion>();

	ManaClass manaClass = new ManaClass();
	Commands commands = new Commands();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		PlayerInventory inv = p.getInventory();
		if (inv.getItemInMainHand().equals(Commands.alchemistKitItems[0])) {
			if (a.equals(Action.LEFT_CLICK_AIR)) {
					ThrownPotion potion = p.launchProjectile(ThrownPotion.class);
					ItemStack splash = new ItemStack(Material.SPLASH_POTION);
					PotionMeta splashMeta = (PotionMeta) splash.getItemMeta();
					splashMeta.setColor(Color.AQUA);
					splash.setItemMeta(splashMeta);

					potion.setItem(splash);
					dmgPotions.add(potion);

					/*ItemStack newItem = inv.getItemInMainHand();
					newItem.setDurability((short) 1);
					inv.setItem(inv.getHeldItemSlot(), newItem);*/
			} else if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)) {
				ThrownPotion potion = p.launchProjectile(ThrownPotion.class);
				ItemStack splash = new ItemStack(Material.SPLASH_POTION);
				PotionMeta splashMeta = (PotionMeta) splash.getItemMeta();
				splashMeta.setColor(Color.GREEN);
				splash.setItemMeta(splashMeta);

				potion.setItem(splash);
				buffPotions.add(potion);
			}
		}
	}
	
	@EventHandler
	public void PotionSplashEvent(org.bukkit.event.entity.PotionSplashEvent e) {
		if (dmgPotions.contains(e.getPotion())) {
			e.setCancelled(true);
			for (LivingEntity entity : e.getAffectedEntities()) {
				if (!(entity instanceof Player)) {
					entity.damage(100);
				}
			}
			dmgPotions.remove(e.getPotion());
		} else if (buffPotions.contains(e.getPotion())) {
			e.setCancelled(true);
			for (LivingEntity entity : e.getAffectedEntities()) {
				if (entity instanceof Player) {
					entity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 1));
					entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
					entity.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 2));
				}
			}
			buffPotions.remove(e.getPotion());
		}
	}
}
