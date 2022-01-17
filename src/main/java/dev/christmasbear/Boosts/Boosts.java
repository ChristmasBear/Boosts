package dev.christmasbear.Boosts;

import dev.christmasbear.Boosts.Events.*;
import dev.christmasbear.Boosts.Files.DataManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.*;

public class Boosts extends JavaPlugin implements Listener {
	public DataManager dataManager;

	private final Commands commands = new Commands();
	
    public static Boosts getInstance;
    
	public final HashMap<UUID, Double> playerBank = new HashMap<>();
	
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
			Objects.requireNonNull(getCommand(cmd)).setExecutor(commands);
		}
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "  ____                          _         \r\n"
				+ " | __ )    ___     ___    ___  | |_   ___ \r\n"
				+ " |  _ \\   / _ \\   / _ \\  / __| | __| / __|\r\n"
				+ " | |_) | | (_) | | (_) | \\__ \\ | |_  \\__ \\\r\n"
				+ " |____/   \\___/   \\___/  |___/  \\__| |___/\r\n"
				+ "                                          ");
		
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
		
		ManaClass manaClass = new ManaClass();
		for (Player online : Bukkit.getOnlinePlayers()) {
			manaClass.init(online);
		}
		
		Commands commands = new Commands();
		List<Integer> _rgb;
		ArrayList<Integer> rgb;
		ArrayList<String> lore;
		ItemStack weapon;
		
		_rgb = Arrays.asList(153, 255, 255, 187, 255, 255, 221, 255, 255, 255, 255, 255);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore = new ArrayList<String>();
		lore.add("" + ChatColor.YELLOW + ChatColor.BOLD + "Left Click" + ChatColor.WHITE + " | " + ChatColor.AQUA + "Snow Laser" + ChatColor.WHITE + ": Fires an accurate snow beam that damages whatever comes in contact with it.");
		lore.add("" + ChatColor.YELLOW + ChatColor.BOLD + "Right Click" + ChatColor.WHITE + " | " + ChatColor.AQUA + "Snow Leap" + ChatColor.WHITE + ": Leap in the direction you are looking at.");
		lore.add("" + ChatColor.YELLOW + ChatColor.BOLD + "Right Click + Shift" + ChatColor.WHITE + " | " + ChatColor.AQUA + "Snow Leap" + ChatColor.WHITE + ": Leap in the direction opposite of where you are looking at.");
		Commands.mageKitItems = commands.kitCreator("Wizard", rgb, "#ccffff", "#ffffff", new ItemStack(Material.END_ROD, 1), ChatColor.RED + "Candy " + ChatColor.WHITE + "Cane " + ChatColor.RED + "Wand", lore);
		
		_rgb = Arrays.asList(229, 113, 162, 237, 160, 192, 245, 206, 222, 253, 253, 253);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore = new ArrayList<String>();
		lore.add("your mother moment");
		Commands.healerKitItems = commands.kitCreator("Healer", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.PINK_DYE, 1), "is it yuor mother or you're mother lmfao maqd cuase bad", lore);
		
		_rgb = Arrays.asList(229, 113, 162, 237, 160, 192, 245, 206, 222, 253, 253, 253);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore = new ArrayList<String>();
		lore.add("stfu my guy");
		Commands.santaKitItems = commands.kitCreator("Santa", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.SNOW_BLOCK, 1), "stop talking kid", lore);
		
		_rgb = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore = new ArrayList<String>();
		lore.add("stfu my guy");
		weapon = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta weaponMeta = (PotionMeta) weapon.getItemMeta();
		weaponMeta.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE));
		weaponMeta.addEnchant(Enchantment.DAMAGE_ALL, 69, true);
		weapon.setItemMeta(weaponMeta);
		Commands.ninjaKitItems = commands.kitCreator("Ninja", rgb, "#000000", "#808080", weapon, "stop talking kid", lore);
		
		_rgb = Arrays.asList(229, 113, 162, 237, 160, 192, 245, 206, 222, 253, 253, 253);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore = new ArrayList<String>();
		lore.add("stfu my guy");
		Commands.alchemistKitItems = commands.kitCreator("Alchemist", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.CYAN_STAINED_GLASS), "Alchemist's Potion Launcher", lore);

		_rgb = Arrays.asList(229, 113, 162, 237, 160, 192, 245, 206, 222, 253, 253, 253);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore = new ArrayList<String>();
		lore.add("stfu my guy");
		Commands.soulseekerKitItems = commands.kitCreator("SoulSeeker", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.POPPY), "SoulSeeker's Flower", lore);

		_rgb = Arrays.asList(229, 113, 162, 237, 160, 192, 245, 206, 222, 253, 253, 253);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore = new ArrayList<String>();
		lore.add("stfu my guy");
		Commands.hackerKitItems = commands.kitCreator("Hacker", rgb, "#eda0c0", "#ffffff", new ItemStack(Material.BLACK_CONCRETE), "Hack Injection Tool", lore);

		_rgb = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore = new ArrayList<String>();
		lore.add("stfu my guy");
		Commands.sniperKitItems = commands.kitCreator("Sniper", rgb, "#000000", "#000000", new ItemStack(Material.BLACK_DYE), "Sniper's Sniper", lore);

		_rgb = Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255);
		rgb = new ArrayList<Integer>();
		rgb.addAll(_rgb);
		lore = new ArrayList<String>();
		lore.add("stfu my guy");
		Commands.angelKitItems = commands.kitCreator("Angel", rgb, "#ffffff", "#ffffff", new ItemStack(Material.WHITE_DYE), "Angel's Shotgun", lore);

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
