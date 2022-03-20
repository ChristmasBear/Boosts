/*package dev.christmasbear.Boosts;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ManaClass implements Listener {
	private static final HashMap<UUID, Double> mana = new HashMap<UUID, Double>();
	private static final HashMap<UUID, Double> regeneration = new HashMap<UUID, Double>();
	private static final HashMap<UUID, Double> maxMana = new HashMap<UUID, Double>();
	
	public Double getMana(Player p) {
		return mana.get(p.getUniqueId());
	}
	
	public Double getMaxMana(Player p) {
		return maxMana.get(p.getUniqueId());
	}
	
	public Double getRegeneration(Player p) {
		return regeneration.get(p.getUniqueId());
	}
	
	public String displayMana(Player p) {
		return "§3[" + getMana(p) + "/" + getMaxMana(p) + "]";
	}
	
	public Boolean useMana(Player p, Boolean isStatic, Double amount) {
		if (isStatic) {
			String text;
			if (amount > mana.get(p.getUniqueId())) {
				text = displayMana(p) + " §cNot enough mana!";
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
				return false;
			} else {
				text =  displayMana(p) + "§9 -" + amount;
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
				mana.put(p.getUniqueId(), getMana(p) - amount);
				return true;
			}
		}
		return true;
	}
	
	public void init(Player p) {
		mana.put(p.getUniqueId(), (double) 0);
		maxMana.put(p.getUniqueId(), (double) 20);
		regeneration.put(p.getUniqueId(), (double) 3);
		new Timer().scheduleAtFixedRate(new TimerTask(){
		    @Override
		    public void run(){
		    	if (!p.isOnline()) return;
		    	if (getMaxMana(p) >= getMana(p) + getRegeneration(p)) {
		    		mana.put(p.getUniqueId(), getMana(p) + getRegeneration(p));
		    		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(displayMana(p)));
		    	} else if (!getMana(p).equals(getMaxMana(p))) {
		    		mana.put(p.getUniqueId(), getMaxMana(p));
		    		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(displayMana(p)));
		    	}
		    }
		},0,100);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		init(e.getPlayer());
	}
}
*/