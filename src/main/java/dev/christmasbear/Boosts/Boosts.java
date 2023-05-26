package dev.christmasbear.Boosts;

import dev.christmasbear.Boosts.Events.*;
import dev.christmasbear.Boosts.Files.DataManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Boosts extends JavaPlugin implements Listener {
	public DataManager dataManager;

	private final Commands commands = new Commands();
	
	/*public MySQL sql;
	public SQLGetter data;*/
	
	public void onEnable() {
		this.dataManager = new DataManager(this);

		/*this.sql = new MySQL();
		this.data = new SQLGetter(this);*/
		
		/*try {
			sql.connect();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("database not connected lol!");
		}
		
		if (sql.isConnected()) {
			System.out.println("database connected. nice");
			data.createTable();
			this.getServer().getPluginManager().registerEvents(this, this);
		}*/
		for (String cmd : Commands.commands) {
			getCommand(cmd).setExecutor(new Commands());
		}
		//getCommand("getdasherkit").setExecutor(new Commands());
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "  ____                          _         \r\n"
				+ " | __ )    ___     ___    ___  | |_   ___ \r\n"
				+ " |  _ \\   / _ \\   / _ \\  / __| | __| / __|\r\n"
				+ " | |_) | | (_) | | (_) | \\__ \\ | |_  \\__ \\\r\n"
				+ " |____/   \\___/   \\___/  |___/  \\__| |___/\r\n"
				+ "                                          ");
		getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getPluginManager().registerEvents(new MageEvents(), this);
		this.getServer().getPluginManager().registerEvents(new HealerEvents(), this);
		this.getServer().getPluginManager().registerEvents(new SantaEvents(), this);
		this.getServer().getPluginManager().registerEvents(new NinjaEvents(), this);
		this.getServer().getPluginManager().registerEvents(new WarpMenu(), this);
		this.getServer().getPluginManager().registerEvents(new KitsMenu(), this);
		this.getServer().getPluginManager().registerEvents(new AlchemistEvents(), this);
		this.getServer().getPluginManager().registerEvents(new SoulSeekerEvents(), this);
		this.getServer().getPluginManager().registerEvents(new HackerEvents(), this);
		this.getServer().getPluginManager().registerEvents(new SniperEvents(), this);
		this.getServer().getPluginManager().registerEvents(new AngelEvents(), this);
		this.getServer().getPluginManager().registerEvents(new ElementalEvents(), this);
		this.getServer().getPluginManager().registerEvents(new DasherEvents(), this);
		//this.getServer().getPluginManager().registerEvents(new ComboEvents(), this);
		this.getServer().getPluginManager().registerEvents(new WindBenderEvents(), this);
		/*ManaClass manaClass = new ManaClass();
		for (Player online : Bukkit.getOnlinePlayers()) {
			manaClass.init(online);
		}*/
		
		Commands commands = new Commands();
		List<Integer> _rgb;
		ArrayList<Integer> rgb;
		HashMap<String, String> lore = new HashMap<String, String>();
		ItemStack weapon;
		
		_rgb = Arrays.asList(153, 255, 255, 187, 255, 255, 221, 255, 255, 255, 255, 255);
		rgb = new ArrayList<>();
		rgb.addAll(_rgb);
		lore.put("Left Click", "Fires an accurate snow beam that damages whatever comes in contact with it");
		lore.put("Right Click", "Leaps in the direction you are looking at");
		lore.put("Right Click + Shift", "Leaps in the direction opposite of where you are looking at");
		Commands.mageKitItems = commands.kitCreator("Wizard", rgb, "#ccffff", "#ffffff", new ItemStack(Material.END_ROD, 1), ChatColor.RED + "Candy " + ChatColor.WHITE + "Cane " + ChatColor.RED + "Wand", Tools.loreColourer(lore));
		lore.clear();
		System.out.println(Arrays.toString(Commands.mageKitItems));

		_rgb = Arrays.asList(229, 113, 162, 237, 160, 192, 245, 206, 222, 253, 253, 253);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore.put("not done yet", "");
		Commands.healerKitItems = commands.kitCreator("Healer", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.PINK_DYE, 1), "is it yuor mother or you're mother lmfao maqd cuase bad", Tools.loreColourer(lore));
		lore.clear();
		Commands.santaKitItems = commands.kitCreator("Santa", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.SNOW_BLOCK, 1), "stop talking kid", Tools.loreColourer(lore));
		lore.clear();

		_rgb = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		weapon = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta weaponMeta = (PotionMeta) weapon.getItemMeta();
		weaponMeta.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE));
		weaponMeta.addEnchant(Enchantment.DAMAGE_ALL, 69, true);
		weapon.setItemMeta(weaponMeta);
		Commands.ninjaKitItems = commands.kitCreator("Ninja", rgb, "#000000", "#808080", weapon, "stop talking kid", Tools.loreColourer(lore));
		lore.clear();

		_rgb = Arrays.asList(229, 113, 162, 237, 160, 192, 245, 206, 222, 253, 253, 253);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		Commands.alchemistKitItems = commands.kitCreator("Alchemist", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.CYAN_STAINED_GLASS), "Alchemist's Potion Launcher", Tools.loreColourer(lore));
		lore.clear();

		Commands.soulseekerKitItems = commands.kitCreator("SoulSeeker", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.POPPY), "SoulSeeker's Flower", Tools.loreColourer(lore));
		lore.clear();

		Commands.hackerKitItems = commands.kitCreator("Hacker", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.BLACK_CONCRETE), "Hack Injection Tool", Tools.loreColourer(lore));
		lore.clear();

		_rgb = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		Commands.sniperKitItems = commands.kitCreator("Sniper", rgb, "#000000", "#000000", new ItemStack(Material.BLACK_DYE), "Sniper's Sniper", Tools.loreColourer(lore));
		lore.clear();

		_rgb = Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		Commands.angelKitItems = commands.kitCreator("Angel", rgb, "#ffffff", "#ffffff", new ItemStack(Material.WHITE_DYE), "Angel's Shotgun", Tools.loreColourer(lore));
		lore.clear();

		_rgb = Arrays.asList(229, 113, 162, 237, 160, 192, 245, 206, 222, 253, 253, 253);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		weapon = new ItemStack(Material.TIPPED_ARROW);
		weaponMeta = (PotionMeta) weapon.getItemMeta();
		weaponMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		weapon.setItemMeta(weaponMeta);
		Commands.elementalKitItems = commands.kitCreator("Elemental", rgb, "#eda0c0", "#ffffff", weapon, "Elemental Wand", Tools.loreColourer(lore));

		_rgb = Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		Commands.dasherKitItems = commands.kitCreator("Dasher", rgb, "#ffffff", "#ffffff", new ItemStack(Material.OXEYE_DAISY), "Dasher", Tools.loreColourer(lore));
		lore.clear();

		_rgb = Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		Commands.windBenderKitItems = commands.kitCreator("WindBender", rgb, "#ffffff", "#ffffff", new ItemStack(Material.OXEYE_DAISY), "Windsheesh", Tools.loreColourer(lore));
		lore.clear();


		ItemStack warpMenu = new ItemStack(Material.COMPASS);
		ItemMeta menuMeta = warpMenu.getItemMeta();
		menuMeta.setDisplayName(ChatColor.AQUA + "Warp Menu");
		warpMenu.setItemMeta(menuMeta);
		Commands.warpMenu = warpMenu;
		
		loadConfig();
	}
	
	public void onDisable() {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "  ____                          _         \r\n"
				+ " | __ )    ___     ___    ___  | |_   ___ \r\n"
				+ " |  _ \\   / _ \\   / _ \\  / __| | __| / __|\r\n"
				+ " | |_) | | (_) | | (_) | \\__ \\ | |_  \\__ \\\r\n"
				+ " |____/   \\___/   \\___/  |___/  \\__| |___/\r\n"
				+ "                                          ");
		//sql.disconnect();
	}
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	/*@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		data.createPlayer(p);
	}*/
}
