package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static com.olympiarpg.orpg.ability.effect.EffectLibrary.getTargetedEntity;

public class AbilityForcePush extends Ability {

    public AbilityForcePush() {
        super("Force Push", 15);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity e = getTargetedEntity(p, 25);
        if (e != null) {
            OlympiaRPG.INSTANCE.damage(e, 30, p, false);
            LivingEntity pl = (LivingEntity) e;
            pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1, 0.1f);
            pl.setVelocity(e.getLocation().subtract(p.getLocation()).toVector().normalize().multiply(2).setY(1.5));
            return true;
        }
        return false;
    }
}
