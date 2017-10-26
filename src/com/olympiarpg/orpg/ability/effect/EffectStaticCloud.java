package com.olympiarpg.orpg.ability.effect;

import java.util.Collection;

import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectStaticCloud extends BukkitRunnable{
	
	public Location loc;
	public float Radius;
	public Effect FX;
	public PotionEffect Effect;
	public int Timer;
	public Player Caster;
	public float Damage;
	
	
	public EffectStaticCloud(Location l, float rad, Effect fx, PotionEffect eff, int timer, Player p, float damage) {
		loc = l;
		Radius = rad;
		FX = fx;
		Effect = eff;
		Timer = timer;
		Caster = p;
		Damage = damage;
		this.runTaskTimer(OlympiaRPG.INSTANCE, 0, 1);
	}

    public void run() {
		Timer--;
		if(Timer < 0) {
			this.cancel();
			return;
		}
		
		Collection<Entity> Locations = loc.getWorld().getNearbyEntities(loc, Radius, Radius, Radius);
		//Damage//
		for(Entity e : Locations) {
			if(e instanceof LivingEntity && !e.getUniqueId().equals(Caster)) {
				LivingEntity liv = (LivingEntity)e;
				if(Damage > 0) {
					OlympiaRPG.INSTANCE.damage(liv, Damage, Caster, false);
				}
				if(Effect != null) {
					PotionEffect Eff = new PotionEffect(Effect.getType(), Effect.getDuration(), 
							Effect.getAmplifier(), Effect.isAmbient(), Effect.hasParticles(), Effect.getColor());
					Eff.apply(liv);
				}
			}
		}
		//FX//
		loc.getWorld().spigot().playEffect(loc, FX, 0, 0, Radius, Radius, Radius, 0,(int)(Radius * Radius * Radius * 9), 40);
	}
	
	
	
}
