package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityRepair extends Ability {
    public AbilityRepair() {
        super("Repair", 20);
    }

    @Override
    public boolean action(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*2, 100));
        new BukkitRunnable() {
            @Override
            public void run() {
                heal(p, 40, p);
                OlympiaRPG.INSTANCE.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.HEART, false, (float) p.getLocation().getX(), (float) p.getLocation().getY(), (float) p.getLocation().getZ(), 1f, 1f, 1f, 0, 25, 0));
            }
        }.runTaskLater(OlympiaRPG.INSTANCE, 20*2);
        return true;
    }
}
