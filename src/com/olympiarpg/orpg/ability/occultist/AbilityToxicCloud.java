package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.ability.effect.BleedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AbilityToxicCloud extends Ability {

    public AbilityToxicCloud() {
        super("Toxic Cloud", 30);
    }

    @Override
    public boolean action(Player p) {
        Location location = p.getLocation().clone();
        new BukkitRunnable() {

            List<LivingEntity> bleeding = new ArrayList<LivingEntity>();

            int count = 0;

            @Override
            public void run() {
                if (count < 10*2) {
                    count++;
                    OlympiaRPG.INSTANCE.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 5, 5, 5, 0, 500, 103));
                    for (Entity e : Utils.getNearbyEntities(location, 7, 7, 7)) {
                        if (e instanceof LivingEntity && e != p) {
                            if (!bleeding.contains(e)) {
                                new BleedEffect(p.getUniqueId(), (LivingEntity) e, 7, 10, false);
                                bleeding.add((LivingEntity)e);
                            }
                        }
                    }
                } else {
                    this.cancel();
                    bleeding.clear();
                    bleeding = null;
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 0, 10);
        return true;
    }
}
