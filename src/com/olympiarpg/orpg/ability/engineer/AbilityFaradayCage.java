package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilityFaradayCage extends Ability {

    public AbilityFaradayCage() {
        super("Tesla Coil", 35);
    }

    @Override
    public boolean action(Player p) {
        Block b = p.getTargetBlock(OlympiaRPG.transparent, 40);
        Location l = b.getLocation();
        new BukkitRunnable() {

            int count = 0;

            @Override
            public void run() {
                Vector v = EffectLibrary.getRandomVector().multiply(10).setY(0).multiply(rnd.nextDouble());
                l.add(v);
                l.getWorld().strikeLightningEffect(l);
                for (Entity e : Utils.getNearbyEntities(l, 1, 2, 1)) {
                    if (e instanceof LivingEntity && e != p) {
                        OlympiaRPG.INSTANCE.damage((LivingEntity) e, 40, p, false);
                    }
                }
                l.subtract(v);
                count++;
                if (!(count < 20*20)) {
                    this.cancel();
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 2, 1);
        return true;
    }
}
