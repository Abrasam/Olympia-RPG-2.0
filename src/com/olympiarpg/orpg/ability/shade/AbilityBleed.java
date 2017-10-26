package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.ability.effect.BleedEffect;
import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AbilityBleed extends Ability {

    public AbilityBleed() {
        super("Bleed", 15);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity e = EffectLibrary.getTargetedEntity(p, 15);
        if (e != null) {
            new BleedEffect(p.getUniqueId(), e, 4, 5);
            return true;
        }
        return false;
    }
}
