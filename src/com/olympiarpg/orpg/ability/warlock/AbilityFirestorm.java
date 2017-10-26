package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class AbilityFirestorm extends Ability {

    public AbilityFirestorm() {
        super("Firestorm", 45);
    }

    @Override
    public boolean action(Player p) {
        Location l = p.getLocation().add(0, 20, 0);
        for (int i = 0; i < 10; i++) {
            Vector v = EffectLibrary.getRandomVector().multiply(rnd.nextDouble()*15);
            l.add(v);
            Vector movement = new Vector(0, -1, 0);
            new BukkitRunnable() {

                Location loc = l.clone();

                @Override
                public void run() {
                    boolean flag = false;
                    loc.add(movement);
                    EffectLibrary.sphereParticles(loc, EnumParticle.FLAME, 0);
                    movement.setY(movement.getY() - 0.01);
                    List<Entity> victims = Utils.getNearbyEntities(loc, 1, 1, 1);
                    for (Entity e : victims) {
                        if (e instanceof LivingEntity && e != p) {
                            LivingEntity l = (LivingEntity) e;
                            OlympiaRPG.INSTANCE.damage((LivingEntity) l, 38, p, false);
                            flag = true;
                        }
                    }
                    if (loc.getWorld().getBlockAt(loc).getType() != Material.AIR || flag) {
                        loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 0);
                        loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                        for (Entity e : Utils.getNearbyEntities(loc, 3, 3, 3)) {
                            if (e instanceof LivingEntity && !e.equals(p)) {
                                OlympiaRPG.INSTANCE.damage((LivingEntity) e, 100, p, false);
                            }
                        }
                        this.cancel();
                    }
                }
            }.runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
            l.subtract(v);
        }
        return true;
    }
}
