package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DiamondScroll extends GenericScroll {

    public DiamondScroll() {
        super("Scroll of Riches", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Diamonds are forever!"}, 5, 1);
    }

    @Override
    protected void addRecipe() {

    }

    @Override
    protected void scrollEffect(Player player) {
        Block b = player.getTargetBlock(OlympiaRPG.transparent, 10);
        for (int i = 0; i < 8; i++) {
            if (Utils.isPathable(b.getRelative(BlockFace.UP).getType())) {
                b = b.getRelative(BlockFace.UP);
            }
        }
        Location loc = b.getLocation();

        new BukkitRunnable() {
            int count = 0;
            List<Item> toDie = new ArrayList<>();

            @Override
            public void run() {
                for (int i = 0; i < 1; i++) {
                    Location l = loc.clone();
                    l.add(EffectLibrary.getRandomVector().setY(0).multiply(5).multiply(rnd.nextDouble()));
                    Item it = l.getWorld().dropItem(l, new ItemStack((rnd.nextBoolean() ? Material.DIAMOND : Material.GOLD_INGOT), 64));
                    if (rnd.nextInt(20) < 19) {
                        it.setPickupDelay(Integer.MAX_VALUE);
                        toDie.add(it);
                    }
                    count++;
                    if (count > 10*20) {
                        this.cancel();
                        for (Item item : toDie) {
                            if (item.isDead()) continue;
                            item.remove();
                        }
                    }
                }
            }

        }.runTaskTimer(OlympiaRPG.INSTANCE, 5, 1);
    }
}
