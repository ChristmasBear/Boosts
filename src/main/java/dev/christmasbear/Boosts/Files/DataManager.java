package dev.christmasbear.Boosts.Files;

import dev.christmasbear.Boosts.Boosts;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {
    private final Boosts plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public DataManager(Boosts plugin) {
        this.plugin = plugin;
        saveDefaultConfig(); // saves & init
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
            setDefaultConfig();
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("config.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null) reloadConfig();
        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.configFile == null) return;
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to: " + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
        }
        if (!this.configFile.exists()) this.plugin.saveResource("config.yml", false);
    }

    private void setDefaultConfig() {
        getConfig().set("kits.wizard.baseDmg", 100d);
        getConfig().set("kits.alchemist.baseDmg", 100d);
        getConfig().set("kits.santa.baseDmg", 100d);
        getConfig().set("kits.soulseeker.baseDmg", 2d);
        getConfig().set("kits.soulseeker.dmgMulti", 1.5d);
        getConfig().set("kits.soulseeker.heal", 1);
        getConfig().set("kits.soulseeker.mainCooldown", 10);
        getConfig().set("kits.soulseeker.altCooldown", 0);
        getConfig().set("kits.hacker.basePow", 1.5d);
        getConfig().set("kits.hacker.buffs.slow.dur", 200L);
        getConfig().set("kits.hacker.buffs.slow.lvl", 5);
        getConfig().set("kits.hacker.buffs.slow.amp", 2);
        getConfig().set("kits.hacker.altCooldown", 500);
        saveConfig();
    }
}
