package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AbilitySnowCannon extends Ability {

    public AbilitySnowCannon() {
        super("Snow Cannon", 35);
    }

    @Override
    public boolean action(Player p) {

        List<Snowball> sb = new ArrayList<Snowball>();
        new TimedEffect(20) {
            @EventHandler
            public void onProjHit(ProjectileHitEvent e) {
                if (e.getEntity() != null && sb.contains(e.getEntity()) && e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity) {
                    OlympiaRPG.INSTANCE.damage((LivingEntity) e.getHitEntity(), 15, p, false);
                }
            }

            @Override
            public void end() {
                sb.clear();
            }
        };
        new BukkitRunnable() {

            int count = 5*4;

            @Override
            public void run() {
                Snowball s = p.launchProjectile(Snowball.class);
                sb.add(s);
                count--;
                if (count <= 0) {
                    this.cancel();
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 0, 5);
        return true;
    }
}
