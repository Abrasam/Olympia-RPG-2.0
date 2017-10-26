package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class BFScroll extends GenericScroll {

    public BFScroll() {
        super("Scroll of Blind Faith", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Don't look down, trust in", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "your faith. Trust in your", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "ability to walk on air."}, 2, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 30*20, 255));
        new BukkitRunnable() {

            @Override
            public void run() {
                if (p.getLocation().getPitch() > 30) {
                    this.cancel();
                    p.removePotionEffect(PotionEffectType.LEVITATION);
                }
            }

        }.runTaskTimer(OlympiaRPG.INSTANCE, 1, 2);
    }
}
