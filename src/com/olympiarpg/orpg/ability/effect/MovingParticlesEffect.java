package com.olympiarpg.orpg.ability.effect;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MovingParticlesEffect extends BukkitRunnable {

	protected EnumParticle particle;
	protected UUID shooter;
	protected int count = 0;
	protected double dmg;
	protected int distance;
	protected Vector direction;
	protected Location location;
	protected Location origin;
	protected Effect end;

	//Lower interval value gives faster effect.
	public MovingParticlesEffect(UUID shooter, EnumParticle particleType, Location loc, Vector direction, int distance, double dmg, int interval) {
		this.shooter = shooter;
		this.particle = particleType;
		this.dmg = dmg;
		this.distance = distance*distance;
		this.direction = direction;
		this.location = loc;
		this.origin = loc.clone();
		this.runTaskTimer(OlympiaRPG.INSTANCE, 1, interval);
	}
	
	public MovingParticlesEffect(UUID shooter, EnumParticle particleType, Location loc, Vector direction, int distance, double dmg, int interval, Effect end) {
		this.shooter = shooter;
		this.particle = particleType;
		this.end = end;
		this.dmg = dmg;
		this.distance = distance*distance;
		this.direction = direction;
		this.location = loc;
		this.origin = loc.clone();
		this.runTaskTimer(OlympiaRPG.INSTANCE, 1, interval);
	}

	protected List<UUID> hit = new ArrayList<UUID>();

	@Override
	public void run() {
		if (location.distanceSquared(origin) < distance) {
			location.add(direction);
			EffectLibrary.sphereParticles(location, particle, 0);
			for (Entity p : Utils.getNearbyEntities(location, 0.5f, 0.5f, 0.5f)) {
				if (!hit.contains(p.getUniqueId()) && p instanceof LivingEntity && !p.getUniqueId().equals(shooter)) {
					OlympiaRPG.INSTANCE.damage((LivingEntity)p, dmg, Bukkit.getPlayer(shooter), false);
					hit.add(p.getUniqueId());
				}
			}
		} else if (location.distanceSquared(origin) == distance && end != null) {
			location.getWorld().playEffect(location, end, 0);
			location.add(direction);
		} else {
			this.cancel();
		}
	}
}
