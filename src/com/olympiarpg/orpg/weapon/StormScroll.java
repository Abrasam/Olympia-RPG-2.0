package com.olympiarpg.orpg.weapon;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StormScroll extends GenericScroll {

    public StormScroll() {
        super("Scroll of Tempestas", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Bring forth a", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "tumultuous storm."}, 10, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player player) {
        player.getWorld().setStorm(true);
        player.getWorld().setThunderDuration(20*60);
    }
}
