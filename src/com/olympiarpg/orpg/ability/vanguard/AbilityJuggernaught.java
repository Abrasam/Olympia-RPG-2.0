package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityJuggernaught extends Ability {

    public AbilityJuggernaught() {
        super("Juggernaught", 30);
    }

    @Override
    public boolean action(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*10, 3));
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*10, 1, true));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*10, 3));
        new LingeringParticlesEffect(EnumParticle.CLOUD, p.getUniqueId(), p, 3, 0, 10, 0).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        return true;
    }
}
