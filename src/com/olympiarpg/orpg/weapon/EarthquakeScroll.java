package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EarthquakeScroll extends GenericScroll {

    public EarthquakeScroll() {
        super("Scroll of Earthquake", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Rock my world."}, 1, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player player) {
        if (player.getWorld().getName().equals("darkzone")) {
            player.getInventory().addItem(this.r.getResult());
            return;
        }
        for (int x = -10; x < 11; x++) {
            for (int z = -10; z < 11; z++) {
                for (int y = -1; y < 2; y++) {
                    Location l = player.getLocation().add(x, y, z);
                    if (TownyUniverse.getTownBlock(l) != null && TownyUniverse.getTownBlock(l).hasTown()) continue;
                    Block b = l.getBlock();
                    l.getWorld().spawnFallingBlock(l, b.getType(), (byte)0x00).setVelocity(EffectLibrary.getRandomVector().setY(1).normalize().multiply(2));
                    b.setType(Material.AIR);
                }
            }
        }
    }
}
