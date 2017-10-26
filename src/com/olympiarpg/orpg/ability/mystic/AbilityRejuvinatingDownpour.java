package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AbilityRejuvinatingDownpour extends Ability implements InCity {

    public AbilityRejuvinatingDownpour() {
        super("Rejuvinating Downpour", 30);
    }

    @Override
    public boolean action(Player p) {
        OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.DRIP_WATER, false, (float)p.getLocation().getX(), (float)p.getLocation().getY(), (float)p.getLocation().getZ(), 5f, 5f, 5f, 1, 4000));
        OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.FALLING_DUST, false, (float)p.getLocation().getX(), (float)p.getLocation().getY(), (float)p.getLocation().getZ(), 5f, 5f, 5f, 1, 1000, 133));
        for (Entity en : Utils.getNearbyEntities(p.getLocation(), 6, 6, 6)) {
            if (en instanceof Player) {
                heal((LivingEntity) en, 90, p);
            }
        }
        return true;
    }
}
