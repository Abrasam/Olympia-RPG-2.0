package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import com.olympiarpg.orpg.util.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityShrooms extends Ability {

    public AbilityShrooms() {
        super("Shrooms", 40);
    }

    @Override
    public boolean action(Player p) {
        SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
        for (Entity en : Utils.getNearbyEntities(p.getLocation(), 6, 6, 6)) {
            if (en instanceof LivingEntity) {
                addPotionEffectIfAlly(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 14, 4), (LivingEntity) en, p);
                addPotionEffectIfAlly(new PotionEffect(PotionEffectType.CONFUSION, 20 * 7, 0), (LivingEntity) en, p);
                addPotionEffectIfAlly(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 14, 5), (LivingEntity) en, p);
            }
        }
        return true;
    }
}