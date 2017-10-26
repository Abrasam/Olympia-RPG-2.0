package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

public class AbilityExplosiveArrow extends Ability {

    public AbilityExplosiveArrow() {
        super("Explosive Arrow", 30);
    }

    @Override
    public boolean action(Player p) {
        new TimedEffect (10) {
            @EventHandler
            public void projHit(ProjectileHitEvent e) {
                if (e.getEntity().getShooter() ==p) {
                    e.getEntity().getLocation().getWorld().playEffect(e.getEntity().getLocation(), Effect.EXPLOSION_HUGE, 0);
                    for (Entity en : e.getEntity().getNearbyEntities(4, 4, 4)) {
                        if (en instanceof LivingEntity) {
                            OlympiaRPG.INSTANCE.damage((LivingEntity)en, 35, p, false);
                        }
                    }
                }
            }
        };
        return true;
    }
}
