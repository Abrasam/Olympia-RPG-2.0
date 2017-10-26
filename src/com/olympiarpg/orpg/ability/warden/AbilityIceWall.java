package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Set;

public class AbilityIceWall extends Ability {

    public AbilityIceWall() {
        super("Ice Wall", 60);
    }

    @Override
    public boolean action(Player p) {
        new IceWallRunnable(p);
        return true;
    }

    public static class IceWallRunnable extends BukkitRunnable {
        public Player attacker;
        public Location loc;
        public Vector direction;
        Vector dir;
        public int Timer = 100;
        public IceWallRunnable(Player p) {
            attacker = p;
            loc = p.getTargetBlock((Set<Material>)null,50).getLocation();
            direction = p.getLocation().getDirection();
            dir = direction.clone();
            direction.setY(0);
            double xtemp = direction.getX();
            direction.setX(direction.getZ());
            direction.setZ(-xtemp);
            //Fix Minor Bug -> LAZY FIX//
            if(direction.getX() == 0) {
                direction.setX(0.000001f);
            }
            direction.normalize().multiply(5);
            this.runTaskTimer(OlympiaRPG.INSTANCE, 0, 2);
        }

        @Override
        public void run() {
            Timer--;
            if(Timer < 0) {
                this.cancel();
                return;
            }
            //FX//
            for(float y = 0; y <= 5; y += 1) {
                for(float v = -1; v <= 1; v+= 0.3333) {
                    float x = (float) (direction.getX() * v);
                    float z = (float) (direction.getZ() * v);
                    loc.getWorld().spigot().playEffect(loc.clone().add(x, y, z), Effect.SNOWBALL_BREAK, 0, 0, 0, 0, 0, 0, 2, 50);
                }
            }

            //EFFECT//
            Collection<Entity> possibilities = loc.getWorld().getNearbyEntities(loc, 5, 5, 5);
            for(Entity e : possibilities) {
                if(e instanceof LivingEntity) {
                    LivingEntity liv = (LivingEntity)e;
                    Location livloc = liv.getLocation();
                    //Find collision Point//
                    double grd1 = direction.getZ() / direction.getX();
                    double grd2 = -1.0 / grd1;
                    double cnst1 = loc.getZ() - (grd1 * loc.getX());
                    double cnst2 = livloc.getZ() - (grd2 * livloc.getX());
                    double xcoll = (cnst1 - cnst2) / (grd2 - grd1);
                    double zcoll = (xcoll * grd1) + cnst1;
                    double deltaX = (livloc.getX() - xcoll);
                    double deltaZ = (livloc.getZ() - zcoll);
                    double distSq = deltaX*deltaX + deltaZ*deltaZ;
                    if(distSq < 1.44f && liv != attacker) {
                        liv.setVelocity(dir);
                    }
                }
            }
        }

    }
}
