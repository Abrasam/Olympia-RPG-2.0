package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.main.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AbilityScavenge extends Ability {

    List<Material> available = new ArrayList<Material>();

    public AbilityScavenge() {
        super("Scavenge", 60);
        putMats();
    }

    @Override
    public boolean action(Player p) {
        ItemStack item = new ItemStack(available.get(rnd.nextInt(9)), rnd.nextInt(64)+1);
        if (p.getInventory().firstEmpty() != -1) {
            p.getInventory().setItem(p.getInventory().firstEmpty(), item);
            return true;
        } else {
            p.sendMessage(ChatColor.DARK_RED + "No space in your inventory.");
            return false;
        }
    }

    private void putMats() {
        available.add(Material.APPLE);
        available.add(Material.ARROW);
        available.add(Material.COOKIE);
        available.add(Material.RED_MUSHROOM);
        available.add(Material.BROWN_MUSHROOM);
        available.add(Material.MELON);
        available.add(Material.WHEAT);
        available.add(Material.PUMPKIN);
        available.add(Material.RAW_FISH);
    }
}
