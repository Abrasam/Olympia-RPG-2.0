package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilityCharge extends Ability {

    public AbilityCharge() {
        super("Charge", 20);
    }

    @Override
    public boolean action(Player p) {
        Vector v = p.getLocation().getDirection();
        v.multiply(4);
        p.setVelocity(v);
        new BukkitRunnable() {

            int count = 20;

            @Override
            public void run() {
                for (Entity e : p.getNearbyEntities(0.5f, 0.5f, 0.5f)) {
                    if (e instanceof LivingEntity && p != e) {
                        OlympiaRPG.INSTANCE.damage((LivingEntity) e, 10, p, false);
                        e.setVelocity(new Vector(0, 1, 0));
                    }
                }
                count--;
                if (count <= 0) {
                    this.cancel();
                }
            }

        }.runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        return true;
    }
}
