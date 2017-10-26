package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AbilityAssassinsCredence extends PassiveAbility {

    public AbilityAssassinsCredence() {
        super("Backstab");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity().equals(e.getDamager())) {
            return;
        }

        if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof Player) {
            Player damager = (Player)e.getDamager();
            SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(damager.getUniqueId());
            if (splayer != null && splayer.hasAbility(this)) {
                float kYaw = damager.getLocation().getYaw();
                float vYaw = e.getEntity().getLocation().getYaw();
                float yawDif = (kYaw - vYaw >= 0 ? kYaw - vYaw: vYaw - kYaw);
                if (yawDif <= 45) {
                    e.setDamage(e.getDamage() * 3);
                    LivingEntity alive = (LivingEntity) e.getEntity();
                    /*for (int i = 0; i < 10; i++) {
                        alive.getWorld().playEffect(alive.getEyeLocation(), Effect.SMOKE, 0);
                    }*/
                }
            }
        }
    }
}
