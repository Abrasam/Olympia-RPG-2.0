package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityFrozenArrow extends Ability {

    public AbilityFrozenArrow() {
        super("Frozen Arrow", 60);
    }

    @Override
    public boolean action(Player p) {
        new TimedEffect(60) {

            @EventHandler
            public void onProjectileHit(ProjectileHitEvent e) {
                if (e.getEntity().getShooter() == p) {
                    if (e.getHitEntity() instanceof LivingEntity) {
                        ((LivingEntity) e.getHitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 2));
                        new LingeringParticlesEffect(EnumParticle.SNOW_SHOVEL, p.getUniqueId(), (LivingEntity)e.getHitEntity(), 5, 0, 3, 0).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
                    }
                }
            }

        };
        return true;
    }
}
