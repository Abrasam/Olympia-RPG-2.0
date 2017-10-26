package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.main.Ability;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityWarCry extends Ability {

    public AbilityWarCry() {
        super("War Cry", 25);
    }

    @Override
    public boolean action(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, 0.1f);
        for (Entity e : p.getNearbyEntities(30, 30, 30)) {
            if (e instanceof LivingEntity) {
                addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 20*10, 3), (LivingEntity) e, p);
                addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.WEAKNESS, 20*10, 3), (LivingEntity) e, p);
            }
        }
        return true;
    }
}
