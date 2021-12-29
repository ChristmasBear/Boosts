package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class KitsMenu implements Listener {
	private final Plugin plugin = Boosts.getPlugin(Boosts.class);
	private final ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
	private final String mainName = ChatColor.BLUE + "Kits";
	private final int[] noKitSlots = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
	private final String[] kits = new String[] {"Mage", "Santa", "Alchemist", "SoulSeeker", "Hacker"};
	
	//public SQLGetter data;
	
	public void open(Player p) {
		int size = 54;
		Inventory inv = plugin.getServer().createInventory(null, size, mainName);
		
		
		
		for (int slot : noKitSlots) {
			inv.setItem(slot, empty);
		}
		
		for (String kitName : kits) {
		    ItemStack[] kitComponents = new Commands().getKit(kitName);
		    ItemStack kitItem = new ItemStack(kitComponents[0].getType(), 1);
		    ItemMeta kitItemMeta = kitItem.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    for (int i = 0; i < kitComponents.length;) {
		    	lore.add("- x" + kitComponents[i].getAmount() + " " + kitComponents[i].getItemMeta().getDisplayName());
		    	i++;
		    }
		    kitItemMeta.setLore(lore);
		    kitItemMeta.setDisplayName(ChatColor.GREEN + kitName + " kit");
		    kitItem.setItemMeta(kitItemMeta);
		    inv.addItem(kitItem);
		}
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void interact(InventoryClickEvent e) {
		//this.data = new SQLGetter(Boosts.getPlugin(Boosts.class));
		Player p = (Player) e.getWhoClicked();
		
		Inventory open = e.getClickedInventory();
		ItemStack item = e.getCurrentItem();
		
		if (open == null) return;
		
		if (e.getView().getTitle().equals(mainName)) {
			e.setCancelled(true);
			if (item == null || !item.hasItemMeta()) return;
			
			String kitName = item.getItemMeta().getDisplayName().replace(" kit", "").substring(2);
			ItemStack[] kitComponents = new Commands().getKit(kitName);
			new Commands().giveItems(p, kitComponents);
			p.sendMessage(ChatColor.GREEN + "You have recieved: " + kitName + " kit!");
			//data.setKit(p.getUniqueId(), kitName.toLowerCase());
		}
	}
}
