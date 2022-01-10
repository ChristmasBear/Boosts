package dev.christmasbear.Boosts.Events;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.christmasbear.Boosts.Boosts;
import dev.christmasbear.Boosts.Commands;
import dev.christmasbear.Boosts.SantaElves;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityTNTPrimed;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftArmorStand;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SantaEvents implements Listener {
	Commands commands = new Commands();
	
	public final String[] gifts = new String[] {
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWM2Mjc0YzIyZDcyNmZjMTIwY2UyNTczNjAzMGNjOGFmMjM4YjQ0YmNiZjU2NjU1MjA3OTUzYzQxNDQyMmYifX19",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDA4Y2U3ZGViYTU2YjcyNmE4MzJiNjExMTVjYTE2MzM2MTM1OWMzMDQzNGY3ZDVlM2MzZmFhNmZlNDA1MiJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWI2NzMwZGU3ZTViOTQxZWZjNmU4Y2JhZjU3NTVmOTQyMWEyMGRlODcxNzU5NjgyY2Q4ODhjYzRhODEyODIifX19",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNlZjlhYTE0ZTg4NDc3M2VhYzEzNGE0ZWU4OTcyMDYzZjQ2NmRlNjc4MzYzY2Y3YjFhMjFhODViNyJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWMzODIxZDRmNjFiMTdmODJmMGQ3YThlNTMxMjYwOGZmNTBlZGUyOWIxYjRkYzg5ODQ3YmU5NDI3ZDM2In19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ5N2Y0ZjQ0ZTc5NmY3OWNhNDMwOTdmYWE3YjRmZTkxYzQ0NWM3NmU1YzI2YTVhZDc5NGY1ZTQ3OTgzNyJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTNlNThlYTdmMzExM2NhZWNkMmIzYTZmMjdhZjUzYjljYzljZmVkN2IwNDNiYTMzNGI1MTY4ZjEzOTFkOSJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUzYThmZDA4NTI5Nzc0NDRkOWZkNzc5N2NhYzA3YjhkMzk0OGFkZGM0M2YwYmI1Y2UyNWFlNzJkOTVkYyJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU0MTlmY2U1MDZhNDk1MzQzYTFkMzY4YTcxZDIyNDEzZjA4YzZkNjdjYjk1MWQ2NTZjZDAzZjgwYjRkM2QzIn19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjNzVhMDViMzQ0ZWEwNDM4NjM5NzRjMTgwYmE4MTdhZWE2ODY3OGNiZWE1ZTRiYTM5NWY3NGQ0ODAzZDFkIn19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ZjZDFjODJlMmZiM2ZhMzY4Y2ZhOWE1MDZhYjZjOTg2NDc1OTVkMjE1ZDY0NzFhZDQ3Y2NlMjk2ODVhZiJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM3MTJiMTk3MWM1ZjQyZWVmZjgwNTUxMTc5MjIwYzA4YjgyMTNlYWNiZTZiYzE5ZDIzOGMxM2Y4NmUyYzAifX19",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlNTVmY2M4MDlhMmFjMTg2MWRhMmE2N2Y3ZjMxYmQ3MjM3ODg3ZDE2MmVjYTFlZGE1MjZhNzUxMmE2NDkxMCJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmM4NjUyYmZkYjdhZGRlMTI4ZTdlYWNjNTBkMTZlYjlmNDg3YTMyMDliMzA0ZGUzYjk2OTdjZWJmMTMzMjNiIn19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU2MTJkYzdiODZkNzFhZmMxMTk3MzAxYzE1ZmQ5NzllOWYzOWU3YjFmNDFkOGYxZWJkZjgxMTU1NzZlMmUifX19",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZTJlOTFjNWQ2MmY2NzhhNmIyZDRiYmNhMmM1MjgzZTEzMTgyYzQzMjhkMGU4NzA2NGUxN2NlNmZmZmIifX19",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmZjk4NmY4OWE4ZWVhNmFlMmMzMjBjNjg4YzI0NjY2YzNhYmExYjc3MzhmZTg5Y2UzNmMyZmJmYTM2NiJ9fX0=",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2UzY2IxZGNiYjlmMTYxZWNmYTk1ODQ3M2IxYmIzYTY5NWJjNWYxMDM4OTFhMWJmOTgyNTEwNjlmYzM3M2QifX19",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY1YjUyYmRiMjU1NmZkN2Q0NmM1Yzg0OGEzOGM4YjhlZjE1M2FmZTdkNTZkYjE3NzZkOTliNTMzYmQwIn19fQ==",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDUwNWI3ZjhkNWQzYjkxMjYzMWM1YzFlZTZjYzViNjg1YThhZDRiYWU5YTk1NTJlOGZkZjdlNzExOTUxZjQifX19",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJmMDQ4OWNhMTI2YTZlOWY5YWZhNTllYjQ5MWIxODUzMzk1YjU4MmI0NTRmYzJhZDQ4MDI3MjI2MjUyZDEyMSJ9fX0=",
	};
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) throws NoSuchFieldException, SecurityException {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		if (inv.getItemInMainHand().equals(Commands.santaKitItems[0])) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				//if (new ManaClass().useMana(p, true, (double) 20)) {
					Block block = p.getTargetBlock(null, 100);
					Location loc = block.getLocation();
					loc.setY(loc.getY() + 1);
					loc.setX(loc.getX() + 0.5f);
					loc.setZ(loc.getZ() + 0.5f);
					SantaElves santaElves = new SantaElves(loc);
					WorldServer world = ((CraftWorld) p.getWorld()).getHandle();
					world.addEntity(santaElves);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Boosts.getPlugin(Boosts.class), new Runnable() {
					    @Override
					    public void run() {
					        santaElves.killEntity();
					    }
					}, 400L);

					final float[] countdown = {20.0f};
					new BukkitRunnable() {
						@Override
						public void run() {
							if (countdown[0] > 0) {
								countdown[0] -= 0.1f;
								santaElves.setCustomName(new ChatComponentText(ChatColor.RED + "slave lol!" + " - [" + (Math.round(countdown[0] * 10.0f) / 10.0f) + "]"));
							} else cancel();
						}
					}.runTaskTimer(Boosts.getPlugin(Boosts.class), 0L, 2L);
				//}
			} else if (e.getAction().equals(Action.LEFT_CLICK_AIR)) {
				ArmorStand gift = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
				int rnd = new Random().nextInt(gifts.length);
				gift.setHelmet(makeSkull(gifts[rnd]));
				gift.setVisible(false);
				
				gift.setVelocity(p.getLocation().getDirection().normalize().multiply(3));
				World world = p.getWorld();
				new BukkitRunnable() {
					@Override
				    public void run() {
						if (gift.isDead()) this.cancel();
						else {
							List<Entity> near = world.getEntities();
					        for(Entity e : near) {
					        	if (e != gift) {
					        		if(e.getLocation().distance(gift.getLocation()) <= 2D) {
				            			if (!(e instanceof Projectile || e instanceof Item || e instanceof Player || e instanceof ExperienceOrb || e instanceof CraftArmorStand || e instanceof EntityTNTPrimed)) {
				            				world.spawnParticle(Particle.EXPLOSION_LARGE, e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), 10);
					            			((Damageable) e).damage(100);
					            			gift.remove();
					            			this.cancel();
				            			}
						            }
					        	}  
					        }
						}
				    }
				}.runTaskTimer(Boosts.getPlugin(Boosts.class), 0L, 2L);
				
				new BukkitRunnable() {
					@Override
					public void run() {
						world.spawnParticle(Particle.EXPLOSION_LARGE, gift.getLocation().getX(), gift.getLocation().getY() + 1, gift.getLocation().getZ(), 10);
				    	gift.remove();
					}
				}.runTaskLater(Boosts.getPlugin(Boosts.class), 40L);
			}
		}
	}
	
	/*public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv =  p.getInventory();
		if (inv.getBoots().equals(Commands.santaKitItems[4])) {
			
		}
	}*/
	
    public ItemStack makeSkull(String base64EncodedString) {
        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64EncodedString));
        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(meta);
        return skull;
    }
    
    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, 999);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }
}
