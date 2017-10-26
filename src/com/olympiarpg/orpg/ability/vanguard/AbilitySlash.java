package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static com.olympiarpg.orpg.ability.effect.EffectLibrary.getTargetedEntity;

public class AbilitySlash extends Ability {

    public AbilitySlash() {
        super("Slash", 30);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity e = getTargetedEntity(p, 15);
        if (e != null) {
            OlympiaRPG.INSTANCE.damage(e, 50, p, false);
            for (int i = 0; i < 50; i++) {
                e.getWorld().playEffect(((LivingEntity) e).getEyeLocation(), Effect.TILE_BREAK, 152);
            }
            return true;
        }
        return false;
    }
}
