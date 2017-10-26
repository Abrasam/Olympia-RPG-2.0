package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AbilityBlink extends Ability {

    public AbilityBlink() {
        super("Blink", 15);
    }

    @Override
    public boolean action(Player p) {
        Block b = getValidLocation(p.getTargetBlock(OlympiaRPG.transparent, 10));
        Location l = b.getLocation().clone();
        Vector v = p.getLocation().getDirection();
        for (double d = 1; d < 50; d++) {
            Location loc = p.getEyeLocation().clone().add(v.multiply(d));
            v.multiply(1/d);
            EffectLibrary.sphereParticles(loc, EnumParticle.SMOKE_LARGE, 0);
        }
        l.setDirection(v);
        p.teleport(l);
        return true;
    }

    private Block getValidLocation(Block l) {
        if (OlympiaRPG.transparent.contains(l.getType()) || l.getLocation().getY() >= 255) {
            return l;
        } else {
            return getValidLocation(l.getRelative(BlockFace.UP));
        }
    }
}
