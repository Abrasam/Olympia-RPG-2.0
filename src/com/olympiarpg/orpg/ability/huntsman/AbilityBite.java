package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.ability.effect.BleedEffect;
import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AbilityBite extends Ability {

    public AbilityBite() {
        super("Bite", 15);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity e = EffectLibrary.getTargetedEntity(p, 10);
        Location l = e.getEyeLocation();
        if (e != null) {
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_CRACK, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0f, 0f, 0f, 0f, 25, 152, 0));
        }
        new BleedEffect(p.getUniqueId(), e, 7, 7);
        return true;
    }
}
