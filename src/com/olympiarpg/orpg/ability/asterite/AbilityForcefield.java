package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityForcefield extends Ability {

    public AbilityForcefield() {
        super("Force Field", 10);
    }

    @Override
    public boolean action(Player p) {
        Location loc = p.getLocation();
        FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.PURPLE).withColor(Color.FUCHSIA).withColor(Color.WHITE).with(FireworkEffect.Type.BALL_LARGE).build();
        final Firework fw = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.addEffect(effect);
        fw.setFireworkMeta(meta);
        new BukkitRunnable() {
            @Override
            public void run() {
                fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
                fw.remove();
            }
        }.runTaskLater(OlympiaRPG.INSTANCE, 2);

        for (Entity e : p.getNearbyEntities(5, 5, 5)) {
            if (e instanceof LivingEntity) {
                e.setVelocity(e.getLocation().subtract(p.getLocation()).toVector().normalize().multiply(2));
            }
        }
        return true;
    }
}
