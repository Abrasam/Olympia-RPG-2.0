package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AbilityPurpleRain extends Ability {

    public AbilityPurpleRain() {
        super("Purple Rain", 15);
    }

    @Override
    public boolean action(Player p) {
        Location l = p.getTargetBlock(OlympiaRPG.transparent, 15).getLocation();
        List<Location> locs = new ArrayList<Location>();
        for (int in = 0; in < 75; in++) {
            locs.add(l.clone().add(EffectLibrary.getRandomVector().multiply(10).setY(0)));
        }
        for (Location loc : locs) {
            for (float y = 0; y < 10; y +=0.5) {
                OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.FALLING_DUST, false, (float)loc.getX(), (float)loc.getY() + y, (float)loc.getZ(), 0, 0, 0, 0, 1, 201));
            }
            loc.add(0, 11, 0);
            loc.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 0);
        }
        for (Entity en : Utils.getNearbyEntities(l, 10, 20, 10)) {
            if (en instanceof LivingEntity && !(en == p)) {
                OlympiaRPG.INSTANCE.damage((LivingEntity) en, 55, p, false);
            }
        }

        return true;
    }
}
