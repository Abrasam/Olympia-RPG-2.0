package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityIcebolt extends Ability {

    public AbilityIcebolt() {
        super("Icebolt", 3);
    }

    @Override
    public boolean action(Player p) {
        Snowball snowball = (Snowball)p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.SNOWBALL);
        snowball.setShooter(p);
        snowball.setVelocity(p.getLocation().getDirection().multiply(2));
        new BukkitRunnable() {

            @Override
            public void run() {
                if (snowball != null && !snowball.isDead()) {
                    Location l = snowball.getLocation();
                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.SNOWBALL, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0f, 0f, 0f, 0, 30);
                    for (Player player : OlympiaRPG.INSTANCE.getServer().getOnlinePlayers()) {
                        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                    }
                } else {
                    for (org.bukkit.entity.Entity e : snowball.getNearbyEntities(0.5, 0.5, 0.5)) {
                        if (e instanceof LivingEntity && !(e instanceof ArmorStand)) {
                            OlympiaRPG.INSTANCE.damage((LivingEntity)e, 18, p, false);
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 20*3, 4), (LivingEntity)e, p);
                        }
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        return true;
    }
}
