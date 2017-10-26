package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilitySunderstrike extends Ability {

    public AbilitySunderstrike() {
        super("Sunderstrike", 10);
    }

    public boolean action(Player p) {
        EffectLibrary.coneEffect(EnumParticle.CRIT, p.getUniqueId(), 8, 30, new PotionEffect(PotionEffectType.SLOW, 20*5, 1), 0);
        return true;
    }

}