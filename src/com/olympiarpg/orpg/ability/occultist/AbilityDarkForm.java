package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityDarkForm extends Ability {

    public AbilityDarkForm() {
        super("Dark Form", 30);
    }

    @Override
    public boolean action(Player p) {
        new LingeringParticlesEffect(EnumParticle.SMOKE_LARGE, p.getUniqueId(), p, 5, 0, 20, 0).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10*20, 2));
        return true;
    }
}
