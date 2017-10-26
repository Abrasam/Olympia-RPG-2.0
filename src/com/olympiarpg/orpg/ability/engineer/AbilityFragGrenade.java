package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AbilityFragGrenade extends Ability implements Listener {

    public AbilityFragGrenade() {
        super("Frag Grenade", 5);
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    private List<Snowball> sbs = new ArrayList<Snowball>();

    @Override
    public boolean action(Player p) {
        Snowball sb = (Snowball)p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.SNOWBALL);
        sb.setVelocity(p.getLocation().getDirection().multiply(1.5));
        sb.setShooter(p);
        sbs.add(sb);
        return true;
    }

    @EventHandler
    public void onProjectileLand(ProjectileHitEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (sbs.contains(e.getEntity())) {
                    e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.EXPLOSION_HUGE, 0);
                    e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 0.25f);
                    for (Entity entity : e.getEntity().getNearbyEntities(5, 5, 5)) {
                        if (entity instanceof LivingEntity && entity != e.getEntity().getShooter()) {
                            OlympiaRPG.INSTANCE.damage((LivingEntity) entity, 25, (Player)e.getEntity().getShooter(), false);
                        }
                    }
                }
            }
        }.runTaskLater(OlympiaRPG.INSTANCE, 20);
    }
}
