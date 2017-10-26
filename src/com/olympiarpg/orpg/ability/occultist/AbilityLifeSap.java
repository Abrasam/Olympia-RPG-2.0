package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.ability.effect.BleedEffect;
import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AbilityLifeSap extends Ability {

    public AbilityLifeSap() {
        super("Life Sap", 20);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity e = EffectLibrary.getTargetedEntity(p, 20);
        new BleedEffect(p.getUniqueId(), e, 3.5, 10){
            @Override
            public void run() {
                super.run();
                OlympiaRPG.INSTANCE.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.HEART, false, (float) p.getLocation().getX(), (float) p.getLocation().getY(), (float) p.getLocation().getZ(), 1f, 1f, 1f, 0, 25, 0));
                heal(p, 3, p);
            }
        };
        return true;
    }
}
