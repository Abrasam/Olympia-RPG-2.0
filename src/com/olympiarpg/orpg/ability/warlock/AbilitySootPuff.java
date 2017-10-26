package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.olympiarpg.orpg.ability.effect.EffectLibrary.getTargetedEntity;

public class AbilitySootPuff extends Ability {

    public AbilitySootPuff() {
        super("Soot Puff", 35);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity en = getTargetedEntity(p, 20);
        if (en != null) {
            new LingeringParticlesEffect(EnumParticle.SMOKE_LARGE, p.getUniqueId(), en, 5, 0, 5, 0).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.BLINDNESS, 20*7, 5), en, p);
            return true;
        }
        return false;
    }
}
