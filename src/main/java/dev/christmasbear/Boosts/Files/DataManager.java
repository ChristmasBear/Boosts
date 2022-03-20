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
        set("kits.wizard.baseDmg", 100d);
        set("kits.wizard.boostPow", 2.5d);
        set("kits.wizard.range", 100);
        set("kits.alchemist.baseDmg", 100d);
        set("kits.santa.baseDmg", 100d);
        set("kits.soulseeker.baseDmg", 2d);
        set("kits.soulseeker.dmgMulti", 1.5d);
        set("kits.soulseeker.heal", 1);
        set("kits.soulseeker.mainCooldown", 10);
        set("kits.soulseeker.altCooldown", 0);
        set("kits.soulseeker.range", 100);
        set("kits.hacker.basePow", 1.5d);
        set("kits.hacker.buffs.slow.dur", 200L);
        set("kits.hacker.buffs.slow.lvl", 5);
        set("kits.hacker.buffs.slow.amp", 2);
        set("kits.hacker.altCooldown", 500);
        set("kits.sniper.baseDmg", 1d);
        set("kits.sniper.headshotMulti", 100d);
        set("kits.sniper.scopeMulti", 1.5d);
        set("kits.sniper.range", 100);
        set("kits.angel.baseDmg", 100d);
        set("kits.angel.shotgunScaling.exp", 1.5d);
        set("kits.angel.shotgunScaling.multi", 2d);
        set("kits.angel.range", 100);
        saveConfig();
    }

    public void set(String dir, Object val) { getConfig().set(dir, val); }
}
