package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.main.Ability;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class AbilityScroll extends GenericScroll {

    private final Ability ab;

    public AbilityScroll(Ability ab) {
        super("Ability Scroll: " + ab.name, new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Ability:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "You can cast this ability", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "without gaining it", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "through your class."}, 1, 1);
        this.ab = ab;
    }

    @Override
    protected void addRecipe() {
    }

    @Override
    protected void scrollEffect(Player player) {
        ab.action(player);
    }
}
