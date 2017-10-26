package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilitySlow extends Ability {

    public AbilitySlow() {
        super("Cripple", 20);
    }

    @Override
    public boolean action(Player p) {
        for (Entity e : p.getNearbyEntities(3, 3, 3)) {
            Location l = e.getLocation();
            if (e != null && e instanceof LivingEntity) {
                addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 5), (LivingEntity) e, p);
                OlympiaRPG.INSTANCE.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.SMOKE_NORMAL, false, (float) l.getX(), (float) l.getY(), (float) l.getZ(), 1f, 1f, 1f, 0, 200));
            }
        }
        return true;
    }
}
