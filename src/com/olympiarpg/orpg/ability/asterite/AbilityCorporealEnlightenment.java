package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityCorporealEnlightenment extends PassiveAbility {

    public AbilityCorporealEnlightenment() {
        super("Corporeal Enlightenment");
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
            SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getDamager().getUniqueId());
            if (sp!= null && sp.hasAbility(this)) {
                ((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 15*20, 1), true);
            }
        }
    }

}
