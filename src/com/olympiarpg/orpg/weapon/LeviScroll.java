package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class LeviScroll extends GenericScroll {

    public LeviScroll() {
        super("Scroll of Levitation", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Make anything surrounding", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "you float up into", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "the sky!"}, 5, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player player) {
        List<LivingEntity> toFly = new ArrayList<LivingEntity>();
        for (Entity e : player.getNearbyEntities(10, 10, 10)) {
            if (e instanceof LivingEntity) {
                toFly.add((LivingEntity)e);
            }
        }
        int x = Math.max(1,toFly.size() / 5);
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                int y = i+x;
                for (;i<=y && i < toFly.size();i++) {
                    ((LivingEntity) toFly.get(i)).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20*15, rnd.nextInt(1)));
                }
                System.out.println(i);
                if (i >= toFly.size() -1) {
                    this.cancel();
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 0, 10);
    }
}
