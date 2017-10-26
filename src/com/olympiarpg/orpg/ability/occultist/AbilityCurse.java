package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AbilityCurse extends Ability {

    public AbilityCurse() {
        super("Curse", 60);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity e = EffectLibrary.getTargetedEntity(p, 30);
        if (e != null) {
            new LingeringParticlesEffect(EnumParticle.SPELL_WITCH, p.getUniqueId(), e, 5, 0, 30, 0).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
            new TimedEffect(20) {
                @EventHandler
                public void onDamage(EntityDamageByEntityEvent ev) {
                    if (ev.getEntity()==e) {
                        ev.setDamage(ev.getDamage()*1.5);
                    }
                }
            };
            return true;
        }
        return false;
    }
}
