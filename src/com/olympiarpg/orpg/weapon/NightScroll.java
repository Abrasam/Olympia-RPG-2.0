package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class NightScroll extends GenericScroll {

    public NightScroll() {
        super("Scroll of Nox", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Plunge the world into darkness."}, 10, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player player) {
        Location location = player.getLocation();
        player.getWorld().setTime(14000);
        FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(true).withColor(Color.BLACK).withColor(Color.NAVY).with(FireworkEffect.Type.BALL_LARGE).build();
        final Firework fw = location.getWorld().spawn(location, Firework.class);
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
    }
}
