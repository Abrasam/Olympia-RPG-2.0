package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityPestilence extends Ability {

    public AbilityPestilence() {
        super("Pestilence", 30);
    }

    @Override
    public boolean action(Player p) {
        Location l = p.getLocation();
        new BukkitRunnable() {

            int count = 0;

            @Override
            public void run() {
                if (count < 20) {
                    count++;
                    for (int i = 0; i < 10; i++) {
                        EffectLibrary.discParticles(l, EnumParticle.BLOCK_DUST, 3, count, true);
                    }
                    EffectLibrary.discParticles(l, EnumParticle.TOTEM, 0, count, true);
                } else {
                    for (Entity e : Utils.getNearbyEntities(l, 20, 3, 20)) {
                        if (e instanceof LivingEntity && e != p) {
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.POISON, 10*20,0), (LivingEntity) e, p);
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 10*20,10), (LivingEntity) e, p);
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.CONFUSION, 10*20,1), (LivingEntity) e, p);
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.WEAKNESS, 10*20,1), (LivingEntity) e, p);
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.BLINDNESS, 3*20,1), (LivingEntity) e, p);
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.HUNGER, 10*20,1), (LivingEntity) e, p);
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW_DIGGING, 10*20,3), (LivingEntity) e, p);
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.WITHER, 10*20,1), (LivingEntity) e, p);
                        }
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        return true;
    }
}
