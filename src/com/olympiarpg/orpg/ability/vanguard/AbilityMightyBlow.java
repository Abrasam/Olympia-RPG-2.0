package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static com.olympiarpg.orpg.ability.effect.EffectLibrary.getTargetedEntity;

public class AbilityMightyBlow extends Ability {

    public AbilityMightyBlow() {
        super("Mighty Blow", 45);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity e = getTargetedEntity(p, 8);
        if (e != null) {
            OlympiaRPG.INSTANCE.damage(e, 120, p, false);
            for (int i = 0; i < 50; i++) {
                e.getWorld().playEffect(((LivingEntity)e).getEyeLocation(), Effect.CRIT, 0);
            }
            return true;
        }
        return false;
    }
}
