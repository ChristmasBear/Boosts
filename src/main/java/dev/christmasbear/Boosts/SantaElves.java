package dev.christmasbear.Boosts;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class SantaElves extends EntityZombie {
	
	public SantaElves(Location loc) {
		super(EntityTypes.ZOMBIE, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		this.setCustomName(new ChatComponentText(ChatColor.RED + "slave lol!"));
		this.setCustomNameVisible(true);
		this.setHealth(50);
		this.setBaby(true);
		this.setInvulnerable(true);
		this.getWorld().addEntity(this);
	}
	
	@Override
	public void initPathfinder() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.2D));
		this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1D));
		this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		
		this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityZombie>(this, EntityZombie.class, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntitySkeleton>(this, EntitySkeleton.class, true));
		this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget<EntitySlime>(this, EntitySlime.class, true));
	}
}
