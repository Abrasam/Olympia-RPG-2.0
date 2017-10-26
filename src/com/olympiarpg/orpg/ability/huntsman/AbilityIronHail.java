package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AbilityIronHail extends Ability implements Listener {

    public AbilityIronHail() {
        super("Iron Hail", 15);
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    @Override
    public boolean action(Player p) {
        List<UUID> uuids = new ArrayList<UUID>();
        for (int i = 0; i < 50; i++) {
            p.getWorld().spawnArrow(p.getEyeLocation().add(p.getLocation().getDirection().multiply(0.75)), p.getLocation().getDirection(), 2f, 20f).setShooter(p);
        }
        uuids.add(p.getUniqueId());
        new TimedEffect(30) {

            List<Entity> hit = new ArrayList<Entity>();
            @EventHandler
            public void onArrowHit(EntityDamageByEntityEvent e) {
                if (!hit.contains(e.getEntity()) && e.getDamager() instanceof Arrow && uuids.contains(((LivingEntity)((Arrow)e.getDamager()).getShooter()).getUniqueId()) && e.getEntity() instanceof LivingEntity) {
                    e.setCancelled(true);
                    OlympiaRPG.INSTANCE.damage((LivingEntity) e.getEntity(), 50, (Player)((Arrow)e.getDamager()).getShooter(), false);
                    hit.add(e.getEntity());
                    ((LivingEntity) e.getEntity()).setNoDamageTicks(20);
                }
            }

            @Override
            public void end() {
                uuids.clear();
                hit.clear();
                hit = null;
            }
        };
        return true;
    }
}
