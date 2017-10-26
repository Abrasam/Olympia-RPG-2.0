package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by sam on 13/07/17.
 */
public class AbilityMeleeMaster extends PassiveAbility {

    public AbilityMeleeMaster() {
        super("Melee Master");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && isMelee(((Player) e.getDamager()).getInventory().getItemInMainHand().getType())) {
            SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getDamager().getUniqueId());
            if (sp.hasAbility(this)) {
                e.setDamage(e.getDamage()*2);
            }
        }
    }

    private boolean isMelee(Material type) {
        return type.toString().contains("_SWORD") || type.toString().contains("_AXE");
    }
}
