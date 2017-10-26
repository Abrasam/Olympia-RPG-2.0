package com.olympiarpg.orpg.ability.effect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class EffectLibrary {

	private static Random random = new Random();

	public static void shotEffect(EnumParticle particle, UUID shooter, int range, double dmg, PotionEffect potion) {
		for (Block b : Bukkit.getPlayer(shooter).getLineOfSight(OlympiaRPG.transparent, range)) {
			Location l = b.getLocation();
			PacketPlayOutWorldParticles p = new PacketPlayOutWorldParticles(particle, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0, 0, 0, 0, 1);
			LivingEntity e = getTargetedEntity(Bukkit.getPlayer(shooter), range);
			if (e != null) {
                OlympiaRPG.INSTANCE.damage(e, dmg, Bukkit.getPlayer(shooter), false);
				if (potion != null) {
					e.addPotionEffect(potion);
				}
			}
			OlympiaRPG.sendParticlePacket(p);
		}
	}

	public static void sphereParticles(Location loc, EnumParticle effect, int data) {
		List<Location> locs = new ArrayList<Location>();
		locs.add(loc.clone().add(0.19999999999999998, 0.0, 0.3464101615137755));
		locs.add(loc.clone().add(-0.09999999999999995, 0.17320508075688773, 0.3464101615137755));
		locs.add(loc.clone().add(-0.10000000000000007, -0.17320508075688767, 0.3464101615137755));
		locs.add(loc.clone().add(0.4, 0.0, 2.4492935982947065E-17));
		locs.add(loc.clone().add(0.24939592074349345, 0.31273259298721195, 2.4492935982947065E-17));
		locs.add(loc.clone().add(-0.08900837358252574, 0.3899711648727295, 2.4492935982947065E-17));
		locs.add(loc.clone().add(-0.36038754716096766, 0.1735534956470233, 2.4492935982947065E-17));
		locs.add(loc.clone().add(-0.36038754716096766, -0.17355349564702321, 2.4492935982947065E-17));
		locs.add(loc.clone().add(-0.08900837358252583, -0.3899711648727295, 2.4492935982947065E-17));
		locs.add(loc.clone().add(0.24939592074349337, -0.312732592987212, 2.4492935982947065E-17));
		locs.add(loc.clone().add(0.19999999999999998, 0.0, -0.3464101615137755));
		locs.add(loc.clone().add(-0.09999999999999995, 0.17320508075688773, -0.3464101615137755));
		locs.add(loc.clone().add(-0.10000000000000007, -0.17320508075688767, -0.3464101615137755));
		for (Location l : locs) {
			PacketPlayOutWorldParticles p = new PacketPlayOutWorldParticles(effect, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0.1f, 0.1f, 0.1f, 0, 1, data);
			OlympiaRPG.sendParticlePacket(p);
		}
	}

	public static void discParticles(Location loc, EnumParticle effect, int data, double rad, boolean flat) {
		for (double i = 0; i < Math.PI*2; i+=0.2) {
			Vector v = getRandomVector();
			v.multiply(rad);
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(effect, false, (float)loc.getX()+(float)v.getX(), (float)loc.getY()+0.5f, (float)loc.getZ()+(float)v.getZ(), 0.1f, 0.1f, 0.1f, 0, 1, data));
		}
		if (!flat) {
			for (int i = 0; i < 50; i++) {
				Vector v = getRandomVector();
				v.multiply(rad);
				OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(effect, false, (float) loc.getX() + (float) v.getX(), (float) (loc.getY() + 0.5f + (v.getY() * 0.3f)), (float) loc.getZ() + (float) v.getZ(), 0.1f, 0.1f, 0.1f, 0, 1, data));
			}
		}
	}

	public static void coneEffect(EnumParticle particle, UUID shooter, int range, double dmg, PotionEffect potion, int data) {
		range *= 10;
		Location loc = Bukkit.getPlayer(shooter).getEyeLocation().clone();
		loc.add(loc.getDirection());
		Vector dir = loc.getDirection().multiply(0.1);
		List<Location> locs = new ArrayList<Location>();
		Set<Entity> los = new HashSet<Entity>();
		float rad = 0;
		for (int rng = 0; rng < range; rng++) {
			Location l = loc.add(dir).clone();
			rad+=0.05;
			los.addAll(Utils.getNearbyEntities(l, rad, rad, rad));
			for (int i = 0; i < 10; i++) {
				Vector vec = getRandomVector().multiply(rad);
				l.add(vec);
				locs.add(l.clone());
				l.subtract(vec);
			}
		}
		for (Location lo : locs) {
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(particle, false, (float)lo.getX(), (float)lo.getY(), (float)lo.getZ(), 0, 0, 0, 0, 3, data));
		}
		for (Entity e : los) {
			if (e instanceof LivingEntity && e.getUniqueId() != shooter) {
                OlympiaRPG.INSTANCE.damage((LivingEntity) e, dmg, Bukkit.getPlayer(shooter), false);
				if (potion != null) {
					((LivingEntity) e).addPotionEffect(potion);
				}
			}
		}
	}

	public static Vector getRandomVector() {
		double x, y, z;
		x = random.nextDouble() * 2 - 1;
		y = random.nextDouble() * 2 - 1;
		z = random.nextDouble() * 2 - 1;

		return new Vector(x, y, z).normalize();
	}

	public static LivingEntity getTargetedEntity(Player player, int range) {
		Set<Material> mats = new HashSet<Material>();
		mats.add(Material.AIR);
		mats.add(Material.WATER);
		mats.add(Material.STATIONARY_WATER);
		List<Entity> entities = player.getNearbyEntities(range, range, range);
		List<LivingEntity> livingEntities = new ArrayList<LivingEntity>();
		for (Entity i : entities) {
			if (i instanceof LivingEntity && i != player) {
				livingEntities.add((LivingEntity)i);
			}
		}

		LivingEntity target = null;

		List<Block> los = player.getLineOfSight(mats, range);
		Collections.reverse(los);
		for (Block block : los) {
			double bx = block.getX();
			double bz = block.getZ();
			double by = block.getY();
			for (LivingEntity e : livingEntities) {
				Location loc = e.getLocation();
				double ex = loc.getX();
				double ez = loc.getZ();
				double ey = loc.getY();
				if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5) && e != player) {
					target = e;
				}
			}
		}
		return target;
	}
}
