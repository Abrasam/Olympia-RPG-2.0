package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static com.olympiarpg.orpg.ability.effect.EffectLibrary.getTargetedEntity;

public class AbilityIgnition extends Ability {

    public AbilityIgnition() {
        super("Ignition", 30);
    }

    @Override
    public boolean action(Player p) {
        Entity en = getTargetedEntity(p, 25);
        if (en instanceof LivingEntity) {
            new LingeringParticlesEffect(EnumParticle.FLAME, p.getUniqueId(), (LivingEntity)en, 3, 15, 5, 0.05f).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
            return true;
        } else {
            return false;
        }
    }
}
