package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AbilityThornyBush extends Ability {

    public AbilityThornyBush() {
        super("Thorny Bush", 30);
    }

    @Override
    public boolean action(Player p) {
        new LingeringParticlesEffect(EnumParticle.CRIT, p.getUniqueId(), p, 5, 0, 30, 0).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        new TimedEffect(10) {
            @EventHandler
            public void onDamage(EntityDamageByEntityEvent e) {
                if (e.getEntity() ==p && e.getDamager() instanceof LivingEntity) {
                    OlympiaRPG.INSTANCE.damage((LivingEntity) e.getDamager(), 27, p, false);
                }
            }
        };
        return true;
    }
}
