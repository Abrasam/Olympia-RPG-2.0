package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;


public class AbilityBackflip extends Ability {

    public AbilityBackflip() {
        super("Backflip", 15);
    }

    @Override
    public boolean action(Player p) {
        if (p.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
            p.setVelocity(p.getLocation().getDirection().multiply(-3).setY(p.getVelocity().getY() + 2));
            new TimedEffect(5) {
                @EventHandler
                public void onDmg(EntityDamageEvent e) {
                    if (e.getEntity() == p && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        e.setCancelled(true);
                        run();
                    }
                }
            };
            return true;
        }
        return false;
    }
}
