package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.ability.effect.MovingParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AbilityPyroclasm extends Ability {

    private static final double cos20 = Math.cos((20/180f) * Math.PI);
    private static final double sin20 = Math.sin((20/180f) * Math.PI);

    public AbilityPyroclasm() {
        super("Pyroclasm", 55);
    }

    @Override
    public boolean action(Player p) {
        Vector dir = new Vector(1, 0, 0);
        for (int i = 0; i < 18; i++) {
            new MovingParticlesEffect(p.getUniqueId(), EnumParticle.FLAME, p.getLocation().add(new Vector(0, 1, 0)), dir, 25, 100, 2, Effect.LAVA_POP) {};
            dir = rot(dir);
        }
        return true;
    }

    private Vector rot(Vector init) {
        Vector out = new Vector();
        return out.setX(init.getX()*cos20 + init.getZ()*sin20).setY((init.getY())).setZ(init.getZ()*cos20 - init.getX()*sin20); //Transformation matrix, yeah boi!!!
    }
}
