package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.huntsman.AbilityGrapplingHook;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TelekinesisScroll extends GenericScroll {

    public TelekinesisScroll() {
        super("Scroll of Telekinesis", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Accio horcrux. Accio bum."}, 3, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player player) {
        List<Item> toTp = new ArrayList<>();
        for (Entity e : player.getNearbyEntities(30, 10, 30)) {
            if (e instanceof Item) {
                AbilityGrapplingHook.propel(e, player.getLocation());
                toTp.add((Item) e);
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Item i : toTp) {
                    if (i.isDead()) continue;
                    i.teleport(player);
                }
            }
        }.runTaskLater(OlympiaRPG.INSTANCE, 20);
    }
}
