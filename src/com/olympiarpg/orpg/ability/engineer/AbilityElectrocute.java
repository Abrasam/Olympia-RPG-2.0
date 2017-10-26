package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;


public class AbilityElectrocute extends Ability {

    public AbilityElectrocute() {
        super("Electrocute", 5);
    }

    @Override
    public boolean action(Player p) {
        Entity e = EffectLibrary.getTargetedEntity(p, 15);
        if (e != null && e instanceof LivingEntity) {
            OlympiaRPG.INSTANCE.damage((LivingEntity) e, 35, p, false);
            for (int i = 0; i < 15; i++) {
                e.getWorld().playEffect(e.getLocation(), Effect.TILE_BREAK, 22);
                e.getWorld().playEffect(((LivingEntity) e).getEyeLocation(), Effect.TILE_BREAK, 22);
            }

            List<Block> los = p.getLineOfSight(OlympiaRPG.transparent, 15);
            for (Block b : los) {
                for (int i = 0; i < 10; i++) {
                    b.getWorld().playEffect(b.getLocation(), Effect.TILE_BREAK, 22);
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
