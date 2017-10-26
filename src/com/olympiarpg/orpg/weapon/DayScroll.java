package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class DayScroll extends GenericScroll {

    public DayScroll() {
        super("Scroll of Lux", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Let there be light!"}, 10, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player player) {
        Location location = player.getLocation();
        player.getWorld().setTime(0);
        FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(true).withColor(Color.BLUE).withColor(Color.WHITE).with(FireworkEffect.Type.BALL_LARGE).build();
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
