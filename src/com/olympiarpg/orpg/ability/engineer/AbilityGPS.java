package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.main.Ability;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class AbilityGPS extends Ability {

    public AbilityGPS() {
        super("GPS", 5);
    }

    @Override
    public boolean action(Player p) {
        int count = 0;
        for (Entity e : p.getNearbyEntities(1000, 1000, 1000)) {
            if (e instanceof Player) {
                count++;
            }
        }
        p.sendMessage(ChatColor.DARK_GREEN + " There are " + count + " players within 1000 blocks.");
        count = 0;
        for (Entity e : p.getNearbyEntities(500, 500, 500)) {
            if (e instanceof Player) {
                count++;
            }
        }
        p.sendMessage(ChatColor.DARK_GREEN + " There are " + count + " players within 500 blocks.");
        count = 0;
        for (Entity e : p.getNearbyEntities(100, 100, 100)) {
            if (e instanceof Player) {
                count++;
            }
        }
        p.sendMessage(ChatColor.DARK_GREEN + " There are " + count + " players within 100 blocks.");
        count = 0;
        for (Entity e : p.getNearbyEntities(50, 50, 50)) {
            if (e instanceof Player) {
                count++;
            }
        }
        p.sendMessage(ChatColor.DARK_GREEN + " There are " + count + " players within 50 blocks.");
        return true;
    }
}
