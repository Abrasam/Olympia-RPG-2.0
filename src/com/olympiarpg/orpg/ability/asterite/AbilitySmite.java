package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AbilitySmite extends Ability {

    public AbilitySmite() {
        super("Smite", 15);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity entity = EffectLibrary.getTargetedEntity(p, 15);
        if (entity != null) {
            Location loc = entity.getLocation();
            entity.getWorld().strikeLightningEffect(loc);
            OlympiaRPG.INSTANCE.damage(entity, 50, p, false);
            return true;
        } else {
            return false;
        }

    }
}
