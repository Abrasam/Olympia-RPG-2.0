package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class AbilitySelfDestruct extends PassiveAbility {

    public AbilitySelfDestruct() {
        super("Self Destruct");
    }

    @EventHandler(ignoreCancelled = true)
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
            if (splayer != null && splayer.hasAbility(this)) {
                p.getWorld().playEffect(p.getLocation(), Effect.EXPLOSION_HUGE, 0);
                for (Entity en : p.getNearbyEntities(4, 4, 4)) {
                    if (en instanceof LivingEntity) {
                        OlympiaRPG.INSTANCE.damage((LivingEntity)en, 75, p, false);
                    }
                }
            }
        }
    }
}
