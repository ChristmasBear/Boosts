package dev.christmasbear.Boosts;

import dev.christmasbear.Boosts.Events.KitsMenu;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands implements Listener, CommandExecutor {
	static String gradiantReader(String input) {
		String output = "";
		List<String> arr = new ArrayList<String>(Arrays.asList(input.split("&")));
		arr.remove(0);
		for (String i : arr) {
			output += ChatColor.of(i.substring(0, 7)) + i.substring(7, 8);
		}
		return output;
	}
	
	public void giveItems(Player p, ItemStack[] items) {
		PlayerInventory inv = p.getInventory();
		for (ItemStack item : items) {
			if (item.getType().equals(Material.LEATHER_BOOTS)) {
				inv.setBoots(item);
			} else if (item.getType().equals(Material.LEATHER_LEGGINGS)) {
				inv.setLeggings(item);
			} else if (item.getType().equals(Material.LEATHER_CHESTPLATE)) {
				inv.setChestplate(item);
			} else if (item.getType().equals(Material.LEATHER_HELMET)) {
				inv.setHelmet(item);
			} else {
				inv.addItem(item);
			}
		}
	}
	
	public ItemStack[] kitCreator(String name, ArrayList<Integer> leatherRgb, String nameHex, String otherHex, ItemStack weapon, String weaponName, ArrayList<String> lore) {
		ItemMeta weaponMeta = weapon.getItemMeta();
		weaponMeta.setDisplayName(weaponName);
		weaponMeta.setLore(lore);
		weapon.setItemMeta(weaponMeta);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
		bootsMeta.setDisplayName("" + ChatColor.of(nameHex) + name + "'s " + ChatColor.of(otherHex) + "Boots");
		bootsMeta.setColor(Color.fromRGB(leatherRgb.get(0), leatherRgb.get(1), leatherRgb.get(2)));
		bootsMeta.setUnbreakable(true);
		boots.setItemMeta(bootsMeta);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
		leggingsMeta.setDisplayName("" + ChatColor.of(nameHex) + name + "'s " + ChatColor.of(otherHex) + "Leggings");
		leggingsMeta.setColor(Color.fromRGB(leatherRgb.get(3), leatherRgb.get(4), leatherRgb.get(5)));
		leggingsMeta.setUnbreakable(true);
		leggings.setItemMeta(leggingsMeta);

		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
		chestplateMeta.setDisplayName("" + ChatColor.of(nameHex) + name + "'s " + ChatColor.of(otherHex) + "Chestplate");
		chestplateMeta.setColor(Color.fromRGB(leatherRgb.get(6), leatherRgb.get(7), leatherRgb.get(8)));
		chestplateMeta.setUnbreakable(true);
		chestplate.setItemMeta(chestplateMeta);
		
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
		helmetMeta.setDisplayName("" + ChatColor.of(nameHex) + name + "'s " + ChatColor.of(otherHex) + "Helmet");
		helmetMeta.setColor(Color.fromRGB(leatherRgb.get(9), leatherRgb.get(10), leatherRgb.get(11)));
		helmetMeta.setUnbreakable(true);
		helmet.setItemMeta(helmetMeta);
		
		return new ItemStack[] {weapon, helmet, chestplate, leggings, boots};
	}
	
	public static final List<String> commands = Arrays.asList("getmagekit", "gethealerkit", "getsantakit", "getninjakit", "getcompass", "openkitsmenu", "getalchemistkit", "getsoulseekerkit", "gethackerkit");
	
	public static ItemStack[] mageKitItems = new ItemStack[]{};
	public static ItemStack[] healerKitItems = new ItemStack[]{};
	public static ItemStack[] santaKitItems = new ItemStack[]{};
	public static ItemStack[] ninjaKitItems = new ItemStack[]{};
	public static ItemStack[] alchemistKitItems = new ItemStack[]{};
	public static ItemStack[] soulseekerKitItems = new ItemStack[]{};
	public static ItemStack[] hackerKitItems = new ItemStack[]{};
	public static ItemStack warpMenu;
	
	public ItemStack[] getKit(String input) {
		switch (input) {
			case "Mage":
				return mageKitItems;
			case "Santa":
				return santaKitItems;
			case "Alchemist":
				return alchemistKitItems;
			case "SoulSeeker":
				return soulseekerKitItems;
			case "Hacker":
				return hackerKitItems;
		}
			
		return new ItemStack[] {new ItemStack(Material.AIR, 0)};
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equals(commands.get(0))) {
				giveItems(p, mageKitItems);
			} else if (cmd.getName().equals(commands.get(1))) {
				giveItems(p, healerKitItems);
			} else if (cmd.getName().equals(commands.get(2))) {
				giveItems(p, santaKitItems);
			} else if (cmd.getName().equals(commands.get(3))) {
				giveItems(p, ninjaKitItems);
			} else if (cmd.getName().equals(commands.get(4))) {
				PlayerInventory inv = p.getInventory();
				inv.addItem(warpMenu);
			} else if (cmd.getName().equals(commands.get(5))) {
				KitsMenu menu = new KitsMenu();
				menu.open(p);
			} else if (cmd.getName().equals(commands.get(6))) {
				giveItems(p, alchemistKitItems);
			} else if (cmd.getName().equals(commands.get(7))) {
				giveItems(p, soulseekerKitItems);
			} else if (cmd.getName().equals(commands.get(8))) {
				giveItems(p, hackerKitItems);
			}
		}
		return false;
	}
}