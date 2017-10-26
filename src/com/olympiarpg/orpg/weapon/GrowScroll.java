package com.olympiarpg.orpg.weapon;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.entity.Player;
import org.bukkit.material.Crops;
import org.bukkit.material.Sapling;
import org.bukkit.material.Tree;

public class GrowScroll extends GenericScroll {

    public GrowScroll() {
        super("Scroll of Horticulture", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Fertilise the seeds that", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "you have sown."}, 10, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player player) {
        for (int x = -5; x < 6; x++) {
            for (int z = -5; z < 6; z++) {
                Location l = player.getLocation().add(x, 0, z);
                if (l.getBlock().getType() == Material.CROPS || l.getBlock().getType() == Material.CARROT || l.getBlock().getType() == Material.POTATO || l.getBlock().getType() == Material.PUMPKIN_STEM || l.getBlock().getType() == Material.MELON_STEM) {
                    l.getBlock().setData((byte)7);
                } else if (l.getBlock().getType() == Material.NETHER_WARTS && l.getBlock().getType() == Material.BEETROOT) {
                    l.getBlock().setData((byte)3);
                }
            }
        }
    }
}
