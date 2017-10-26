package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AbilityFlurry extends Ability implements Listener {

    public AbilityFlurry() {
        super("Flurry", 20);
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    List<UUID> uuids = new ArrayList<UUID>();

    @Override
    public boolean action(Player p) {
        uuids.add(p.getUniqueId());
        new BukkitRunnable() {

            UUID uuid;

            public BukkitRunnable set(UUID uuid) {
                this.uuid = uuid;
                return this;
            }

            public void run() {
                uuids.remove(uuid);
            }
        }.runTaskLater(OlympiaRPG.INSTANCE, 20*20);
        for (int i = 0; i < 50; i++) {
            p.launchProjectile(Snowball.class, p.getLocation().getDirection().add(EffectLibrary.getRandomVector().multiply(rnd.nextDouble()*0.5))).setShooter(p);
        }

        return true;
    }

    @EventHandler
    public void onSnowballLand(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof Snowball && ((Snowball)e.getDamager()).getShooter() instanceof Player && uuids.contains(((Player)((Snowball)e.getDamager()).getShooter()).getUniqueId())) {
            e.setCancelled(true);
            OlympiaRPG.INSTANCE.damage((LivingEntity) e.getEntity(), 60, (Player)((Snowball)e.getDamager()).getShooter(), false);
        }
    }
}
