package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

import static com.olympiarpg.orpg.ability.effect.EffectLibrary.getTargetedEntity;

public class AbilityFlamethrower extends Ability {

    public AbilityFlamethrower() {
        super("Flamethrower", 15);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity e = getTargetedEntity(p, 15);
        if (e instanceof LivingEntity) {
            OlympiaRPG.INSTANCE.damage(e, 70, p, false);
            List<Block> los = p.getLineOfSight(OlympiaRPG.transparent, 15);
            for (Block b : los) {
                for (int i = 0; i < 10; i++) {
                    b.getWorld().playEffect(b.getLocation(), Effect.LAVA_POP, 1);
                }
            }
            for (int i = 0; i < 15; i++) {
                e.getWorld().playEffect(e.getLocation(), Effect.LAVA_POP, 1);
                e.getWorld().playEffect(((LivingEntity) e).getEyeLocation(), Effect.LAVA_POP, 1);
            }
            return true;
        }
        return false;
    }
}
