package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityPainTrain extends Ability {

    public AbilityPainTrain() {
        super("Pain Train", 55);
    }

    @Override
    public boolean action(Player p) {
        new LingeringParticlesEffect(EnumParticle.CRIT, p.getUniqueId(), p, 5, 0, 10, 0).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*10, 10));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*10, 3));
        new BukkitRunnable() {

            int count = 20*15;

            @Override
            public void run() {
                for (Entity e : p.getNearbyEntities(0.5f, 0.5f, 0.5f)) {
                    if (e instanceof LivingEntity && p != e) {
                        OlympiaRPG.INSTANCE.damage((LivingEntity) e, 80, p, false);
                        e.setVelocity(e.getLocation().subtract(p.getLocation()).toVector().normalize().multiply(2).setY(1));
                    }
                }
                count--;
                if (count <= 0) {
                    this.cancel();
                }
            }

        }.runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        return true;
    }
}
