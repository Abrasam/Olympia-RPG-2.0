package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AbilityStunGrenade extends Ability implements Listener {

    public AbilityStunGrenade() {
        super("Stun Grenade", 35);
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
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.BLINDNESS, 20*3, 5), (LivingEntity)entity, (Player)e.getEntity().getShooter());
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 20*5, 1), (LivingEntity)entity, (Player)e.getEntity().getShooter());
                        }
                    }
                }
            }
        }.runTaskLater(OlympiaRPG.INSTANCE, 20);
    }
}
