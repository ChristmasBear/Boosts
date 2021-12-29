package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Commands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

public class HealerEvents implements Listener {
	
	Commands commands = new Commands();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) throws InterruptedException {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		if (inv.getItemInMainHand().equals(Commands.healerKitItems[0])) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				
			}
		}
	}
}
