package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.ability.effect.MovingParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class AbilityGrapplingHook extends Ability implements InCity {

    public AbilityGrapplingHook() {
        super("Grappling Hook", 15);
    }

    @Override
    public boolean action(Player p) {
        new MovingParticlesEffect(p.getUniqueId(), EnumParticle.CRIT, p.getEyeLocation(), p.getLocation().getDirection().multiply(0.5f), 40, 2, 1) {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    if (location.distanceSquared(origin) < distance) {
                        location.add(direction);
                        OlympiaRPG.INSTANCE.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.CRIT, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0.1f, 0.1f, 0.1f, 0, 1, 0));
                        if (!OlympiaRPG.transparent.contains(location.getBlock().getType())) {
                            propel(p, location);
                            this.cancel();
                            return;
                        }
                    } else {
                        this.cancel();
                        return;
                    }
                }
            }
        };
        return true;
    }

    public static void propel(Entity e, Location loc) {
        Location eLoc = e.getLocation().add(0, 0.5, 0); //Boop up a little.
        e.teleport(eLoc);

        double g = -0.08;
        double s = loc.distance(eLoc);
        double t = s;

        //Calculate velocity, taking into account drag in a kinda rubbish way.
        double vx = (1.0 + 0.07 * t) * (loc.getX() - eLoc.getX()) / t;
        double vy = (1.0 + 0.03 * t) * (loc.getY() - eLoc.getY()) / t - 0.5 * g * t;
        double vz = (1.0 + 0.07 * t) * (loc.getZ() - eLoc.getZ()) / t;

        //Give player new velocity.
        Vector v = new Vector(vx, vy, vz);
        e.setVelocity(v);
    }

}
