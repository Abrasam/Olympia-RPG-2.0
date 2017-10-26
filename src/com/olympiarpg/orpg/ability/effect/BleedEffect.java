package com.olympiarpg.orpg.ability.effect;

import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class BleedEffect extends BukkitRunnable implements Listener {

	double dmg;
	UUID uuid;
	LivingEntity target;
	int count;
	boolean armourIgnore = true;

	public BleedEffect(UUID uuid, LivingEntity target, double damage, int noOfBleeds) {
		this.uuid = uuid;
		this.target = target;
		this.dmg = damage;
		this.count = noOfBleeds;
		this.runTaskTimer(OlympiaRPG.INSTANCE, 20, 20);
	}

	public BleedEffect(UUID uuid, LivingEntity target, double damage, int noOfBleeds, boolean armourIgnore) {
		this.uuid = uuid;
		this.target = target;
		this.dmg = damage;
		this.count = noOfBleeds;
		this.armourIgnore = armourIgnore;
		this.runTaskTimer(OlympiaRPG.INSTANCE, 20, 20);
	}

	@Override
	public void run() {
		if (Bukkit.getPlayer(uuid) != null && target != null && !target.isDead()) {
			OlympiaRPG.INSTANCE.damage(target, dmg, Bukkit.getPlayer(uuid), armourIgnore);
			Location l = target.getEyeLocation();
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.BLOCK_CRACK, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0f, 0f, 0f, 0f, 25, 152, 0);
			for (Player p : Bukkit.getOnlinePlayers()) {
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
			}
		}
		count--;
		if (count <= 0) {
			this.cancel();
		}
	}
}
