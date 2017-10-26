package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityStrengthOfTheWild extends Ability implements InCity {

    public AbilityStrengthOfTheWild() {
        super("Strength of the Wild", 120);
    }

    @Override
    public boolean action(Player p) {
        for (Entity en : Utils.getNearbyEntities(p.getLocation(), 10, 10, 10)) {
            if (en instanceof Player) {
                if (addPotionEffectIfAlly(new PotionEffect(PotionEffectType.NIGHT_VISION, 20*60, 1), (Player) en, p)) {
                    new LingeringParticlesEffect(EnumParticle.TOTEM, p.getUniqueId(), (Player) en, 10, 0, 60, 0).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
                }
                addPotionEffectIfAlly(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*60, 3), (Player) en, p);
                addPotionEffectIfAlly(new PotionEffect(PotionEffectType.SPEED, 20*60, 4), (Player) en, p);
                addPotionEffectIfAlly(new PotionEffect(PotionEffectType.JUMP, 20*60, 3), (Player) en, p);
                addPotionEffectIfAlly(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*60, 14), (Player) en, p);
            }
        }
        return true;
    }
}