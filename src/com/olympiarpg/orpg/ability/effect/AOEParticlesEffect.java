package com.olympiarpg.orpg.ability.effect;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class AOEParticlesEffect extends BukkitRunnable {
	
	protected EnumParticle particle;
	protected Location l;
	protected UUID shooter;
	protected int runs;
	protected double damage;
	protected int range;
	protected int[] data;
	protected int count;

	public AOEParticlesEffect(EnumParticle particle, Location l, UUID shooter, int runs, int range, double damage, int... data) {
		super();
		this.particle = particle;
		this.l = l;
		this.damage = damage;
		this.shooter = shooter;
		this.runs = runs;
		this.range = range;
		if (data.length > 0) {
			this.data = data;
		} else {
			this.data = new int[] {0};
		}
		this.count = 0;
		runTaskTimer(OlympiaRPG.INSTANCE, 1, 2);
	}

	@Override
	public void run() {
		count++;
		EffectLibrary.discParticles(l, particle, data[0], range, true);
		if (count%10==0) {
			for (Entity e : Utils.getNearbyEntities(l, range, 2, range)) {
				if (e instanceof LivingEntity && !e.getUniqueId().equals(shooter)) {
					OlympiaRPG.INSTANCE.damage((LivingEntity) e, damage, Bukkit.getPlayer(shooter), false);
				}
			}
		}
		if (this.count > this.runs) {
			this.cancel();
		}
	}

}
