package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class AbilityDragonsBreath extends Ability {

    public AbilityDragonsBreath() {
        super("Dragon's Breath", 15);
    }

    @Override
    public boolean action(Player p) {
        Fireball f = p.launchProjectile(Fireball.class);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 0.1f);
        new TimedEffect(30) {
          @EventHandler
            public void onProjectileHit(ProjectileHitEvent e) {
              if (e.getEntity() == f) {
                  e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.EXPLOSION_HUGE, 0);
                  e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 0.25f);
                  for (Entity en : f.getNearbyEntities(3, 3, 3)) {
                    if (en instanceof LivingEntity && en != p) {
                        OlympiaRPG.INSTANCE.damage((LivingEntity)en, 85, p, false);
                    }
                  }
              }
          }

          @EventHandler
            public void onExplode(EntityExplodeEvent e) {
              if (e.getEntity() == f) {
                  e.setCancelled(true);
              }
          }
        };
        return true;
    }
}
