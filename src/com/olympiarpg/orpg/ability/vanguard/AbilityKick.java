package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static com.olympiarpg.orpg.ability.effect.EffectLibrary.getTargetedEntity;

public class AbilityKick extends Ability {
    
    public AbilityKick() {
        super("Kick", 10);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity e = getTargetedEntity(p, 10);
		if (e instanceof LivingEntity) {
            e.setVelocity(p.getLocation().getDirection().multiply(2).setY(e.getVelocity().getY() + 2));
            OlympiaRPG.INSTANCE.damage(e, 45, p, false);
            return true;
        }
        return false;
    }
}
