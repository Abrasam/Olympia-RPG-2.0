package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class AbilityWeb extends Ability {

    public AbilityWeb() {
        super("Web", 15);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity entity = EffectLibrary.getTargetedEntity(p, 20);
        if (entity != null) {
            LivingEntity e = (LivingEntity)entity;
            ArrayList<Location> locs = new ArrayList<Location>();
            for (double x = -2; x < 3; x++) {
                for (double z = -2; z < 3; z++) {
                    for (double y = 0; y < 2; y++) {
                        Location l = e.getLocation().clone();
                        l.setX(l.getX() + x);
                        l.setZ(l.getZ() + z);
                        l.setY(l.getY() + y);
                        if (l.getBlock().getType() == Material.AIR) {
                            locs.add(l.clone());
                        }
                    }
                }
            }
            //OlympiaRPG.INSTANCE.damage(e, 60, p, false);
            List<BlockState> bs = new ArrayList<BlockState>();
            for (Location loc : locs) {
                bs.add(loc.getBlock().getState());
                loc.getBlock().setType(Material.WEB);
            }
            new EndWeb(bs);
            return true;
        }
        return false;
    }

    private class EndWeb extends TimedEffect {

        List<BlockState> bs;

        public EndWeb(List<BlockState> bs) {
            super(5);
            this.bs = bs;
        }

        @Override
        public void end() {
            for (BlockState b : bs) {
                b.update(true);
            }
        }
    }
}
