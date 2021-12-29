package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class WarpMenu implements Listener {
	private final Plugin plugin = Boosts.getPlugin(Boosts.class);
	private final ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
	private final String mainName = ChatColor.BLUE + "Warp Menu";
	
    /*private World multiverseWorld(String worldname){
        for(World w : Bukkit.getWorlds()){
            if(w.getName().equals(worldname)){
               return w;
            }
        }
        return null;
    }*/
	
	private void open(Player p) {
		int size = 9;
		Inventory inv = plugin.getServer().createInventory(null, size, mainName);
		
		
		
		for (int i = 0; i < size; i++) {
			inv.setItem(i, empty);
		}
		
		ItemStack spawn = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta spawnMeta = spawn.getItemMeta();
		spawnMeta.setDisplayName(ChatColor.GREEN + "Spawn");
		spawn.setItemMeta(spawnMeta);
		inv.setItem(4, spawn);
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void openMenu(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player p = event.getPlayer();
		PlayerInventory inv = p.getInventory();
		
		if (inv.getItemInMainHand().equals(Commands.warpMenu) && (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR))) {
			open(p);
		}
	}
	
	@EventHandler
	public void interact(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		Inventory open = e.getClickedInventory();
		ItemStack item = e.getCurrentItem();
		
		if (open == null) return;
		
		if (e.getView().getTitle().equals(mainName)) {
			e.setCancelled(true);
			if (item == null || !item.hasItemMeta()) return;
			
			if (item.getType().equals(Material.NETHER_STAR)) {
				Location world = Bukkit.getServer().getWorld("spawn").getSpawnLocation();
				world.setX(world.getX() + 0.5);
				world.setZ(world.getZ() + 0.5);
				world.setYaw(180);
				p.teleport(world);
			}
			
		}
	}
}
