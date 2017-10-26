package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.ability.effect.BleedEffect;
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

public class AbilityPlague extends Ability {

    public AbilityPlague() {
        super("Plague", 15);
    }

    @Override
    public boolean action(Player p) {
        Entity e = EffectLibrary.getTargetedEntity(p, 25);
        if (e == null) {
            return false;
        }
        Location l = e.getLocation();
        if (e != null && e instanceof LivingEntity) {
            new BleedEffect(p.getUniqueId(), (LivingEntity) e, 2, 10);
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 20*5, 1), (LivingEntity)e, p);
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.CONFUSION, 20*5, 1), (LivingEntity)e, p);
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 1), (LivingEntity)e, p);
            OlympiaRPG.INSTANCE.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST, false, (float) l.getX(), (float) l.getY(), (float) l.getZ(), 1f, 1f, 1f, 0, 200, 170));
        }
        return true;
    }
}
