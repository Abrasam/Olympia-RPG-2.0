package com.olympiarpg.orpg.ability.effect;

import java.util.UUID;

import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class LingeringParticlesEffect extends BukkitRunnable {

	protected LivingEntity target;
	protected UUID uuid;
	protected double dmg;
	protected int hits;
	protected int count;
	protected int[] moreData;
	protected EnumParticle particle;
	protected float data;
	protected int howMany;

	public LingeringParticlesEffect(EnumParticle particle, UUID shooter, LivingEntity target, int howMany, double dmg, int hits, float data, int... moreData) {
		this.target = target;
		this.uuid = shooter;
		this.hits = hits;
		this.dmg = dmg;
		this.particle = particle;
		this.data = data;
		this.moreData = moreData;
		count = hits*20;
		this.howMany = howMany;
	}

	@Override
	public void run() {
		if (count > 0 && target != null && !target.isDead()) {
			Location l1 = target.getEyeLocation();
			l1.add(EffectLibrary.getRandomVector().normalize().multiply(0.25));
			Location l2 = target.getLocation();
			l2.add(EffectLibrary.getRandomVector().normalize().multiply(0.25));
			Location l3 = l1.clone();
			l3.setY((l1.getY() + l2.getY()) / 2);
			for (int i = 0; i < howMany; i++) {
				Vector v = EffectLibrary.getRandomVector().multiply(new Vector(0.3, 1, 0.3));
				l3.add(v);
				OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(particle, false, (float)l3.getX(), (float)l3.getY(), (float)l3.getZ(), 0, 0, 0, data, 1, moreData));
				l3.subtract(v);
			}
			if (dmg != 0) {
				if (count % 20 == 0) {
					OlympiaRPG.INSTANCE.damage(target, dmg, Bukkit.getPlayer(uuid), false);
				}
			}
			count -= 1;
		} else {
			this.cancel();
		}
	}
}
