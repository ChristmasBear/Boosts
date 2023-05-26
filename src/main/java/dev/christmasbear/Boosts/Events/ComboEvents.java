package dev.christmasbear.Boosts.Events;

import dev.christmasbear.Boosts.Boosts;
import org.bukkit.event.Listener;

public class ComboEvents implements Listener {
    Boosts boosts = new Boosts();

    /*EventHandler
    public void onInteract(PlayerInteractEvent e) throws InterruptedException {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        if (inv.getItemInMainHand().equals(Commands.dasherKitItems[0])) {
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                openMainGUI(p);
            }
        }
    }

    private void openMainGUI(Player p) {
        Inventory gui = boosts.getServer().createInventory(null, 54, org.bukkit.ChatColor.GREEN + "TPS Monitor");
        ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setDisplayName("");
        fill.setItemMeta(fillMeta);
        // fills border
        for (int i = 0; i < 54;) {
            if (i < 10 || 43 < i || i % 9 == 8 || i % 9 == 0) {
                gui.setItem(i, fill);
            }
            i++;
        }
        p.openInventory(gui);
    }*/
}
