package com.olympiarpg.orpg.weapon;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CalmScroll extends GenericScroll {

    public CalmScroll() {
        super("Scroll of Calm", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Let the sun shine", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "and the clouds dispel."}, 10, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player player) {
        player.getWorld().setStorm(false);
        player.getWorld().setThunderDuration(0);
    }
}
