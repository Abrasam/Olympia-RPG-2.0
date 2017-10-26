package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilityStellarBurst extends Ability {

    public AbilityStellarBurst() {
        super("Stellar Burst", 25);
    }

    @Override
    public boolean action(Player p) {
        for (Entity entity : Utils.getNearbyEntities(p.getLocation(), 8, 8, 8)) {
            if (entity instanceof LivingEntity && !(entity == p)) {
                addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 6*20, 6), (LivingEntity)entity, p);
                addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.BLINDNESS, 6*20, 1), (LivingEntity)entity, p);
            }
        }

        new BukkitRunnable() {

            Location l;
            int count;

            BukkitRunnable set(Location l) {
                this.l = l;
                return this;
            }

            public void run() {
                for (int j = 0; j < 25; j++) {
                    Vector v = EffectLibrary.getRandomVector().multiply(4);
                    l.add(v);
                    OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.END_ROD, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 2, 2, 2, 0, 3));
                    OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.DRAGON_BREATH, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 2, 2, 2, 0, 5));
                    l.subtract(v);
                }
                count++;
                if (count >= 40) {
                    this.cancel();
                }
            }
        }.set(p.getEyeLocation()).runTaskTimer(OlympiaRPG.INSTANCE, 1, 5);
        return true;
    }
}
