package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
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

public class AbilitySickness extends Ability {

    public AbilitySickness() {
        super("Sickness", 20);
    }

    @Override
    public boolean action(Player p) {
        Entity e = EffectLibrary.getTargetedEntity(p, 25);
        if (e == null) {
            return false;
        }
        Location l = e.getLocation();
        if (e != null && e instanceof LivingEntity) {
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 20*10, 1), (LivingEntity)e, p);
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.POISON, 20*10, 0), (LivingEntity)e, p);
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.WEAKNESS, 20*10, 1), (LivingEntity)e, p);
            OlympiaRPG.INSTANCE.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST, false, (float) l.getX(), (float) l.getY(), (float) l.getZ(), 1f, 1f, 1f, 0, 200, 133));
        }
        return true;
    }
}
