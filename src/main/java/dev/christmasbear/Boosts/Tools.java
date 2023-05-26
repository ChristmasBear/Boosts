package dev.christmasbear.Boosts;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;

public class Tools {
    public static ArrayList<String> loreColourer(HashMap<String, String> input) {
        ArrayList<String> lore = new ArrayList<>();
        for (String a : input.keySet()) {
            lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + a + ChatColor.RESET + ChatColor.WHITE + " | " + ChatColor.AQUA + input.get(a));
        }
        return lore;
    }
}
