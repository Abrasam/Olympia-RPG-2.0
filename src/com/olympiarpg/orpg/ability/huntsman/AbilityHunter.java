package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AbilityHunter extends PassiveAbility {

    public AbilityHunter() {
        super("Hunter"); //"Hunter, Hunter, oh God, stand down Goddamnit, stand down, Hunter I'm so sorry, it was all my fault."
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && !(e.getEntity() instanceof Player)) {
            SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getDamager().getUniqueId());
            if (sp.hasAbility(this)) {
                e.setDamage(e.getDamage()*1.5);
            }
        }
        if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
            SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(((Player) ((Projectile) e.getDamager()).getShooter()).getUniqueId());
            if (sp.hasAbility(this)) {
                e.setDamage(e.getDamage()*2);
                if (!(e.getEntity() instanceof Player)) {
                    e.setDamage(e.getDamage()*1.5);
                }
            }
        }
    }
}
