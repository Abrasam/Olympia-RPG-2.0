package com.olympiarpg.orpg.ability.occultist;

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

public class AbilityCursedGround extends Ability {


    public AbilityCursedGround() {
        super("Cursed Ground", 30);
    }

    @Override
    public boolean action(Player p) {
        Location l = p.getLocation();
        new BukkitRunnable() {

            int count = 0;

            @Override
            public void run() {
                if (count < 10*4) {
                    count++;
                    if (count %4 ==0) {
                        OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.SMOKE_NORMAL, false, (float) l.getX(), (float) l.getY(), (float) l.getZ(), 5, 0, 5, 0, 500));
                        for (Entity e : Utils.getNearbyEntities(l, 5, 5, 5)) {
                            if (e instanceof LivingEntity && e != p) {
                                OlympiaRPG.INSTANCE.damage((LivingEntity) e, 13, p, false);
                            }
                        }
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 0, 5);
        return true;
    }
}
