package dev.christmasbear.Boosts.Events;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class SantaElves extends EntityZombie {
	
	public SantaElves(Location loc) {
		super(EntityTypes.ZOMBIE, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());

		this.setCustomNameVisible(true);
		this.setHealth(50);
		this.setBaby(true);
		this.setInvulnerable(true);
	}
	
	@Override
	public void initPathfinder() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		
		this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 0.2D));
		this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1D));
		this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
		this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		
		this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityZombie>(this, EntityZombie.class, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntitySkeleton>(this, EntitySkeleton.class, true));
	}
}
