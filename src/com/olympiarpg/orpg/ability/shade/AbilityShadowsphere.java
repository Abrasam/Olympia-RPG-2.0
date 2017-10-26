package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.util.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityShadowsphere extends Ability {

    public AbilityShadowsphere() {
        super("Shadowsphere", 30);
    }

    @Override
    public boolean action(Player p) {
        for (Entity en : Utils.getNearbyEntities(p.getLocation(), 10, 10, 10)) {
            if (en instanceof Player) {
                addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.BLINDNESS, 7*20, 1), (Player) en, p);
                addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 7*20, 2), (Player) en, p);
            }
        }
        return true;
    }
}
