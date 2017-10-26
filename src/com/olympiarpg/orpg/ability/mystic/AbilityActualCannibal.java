package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class AbilityActualCannibal extends PassiveAbility {

    public AbilityActualCannibal() {
        super("Actual Cannibal");
    }

    @EventHandler
    public void onDeathEvent(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getEntity().getKiller().getUniqueId());
            if (sp != null && sp.hasAbility(this)) {
                heal(Bukkit.getPlayer(sp.uuid), e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), e.getEntity().getKiller());
                e.getEntity().getKiller().getWorld().playEffect(((Player) e.getEntity().getKiller()).getEyeLocation(), Effect.HEART, 0);
                e.getEntity().getKiller().getWorld().playSound(e.getEntity().getKiller().getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1);
            }
        }
    }
}
