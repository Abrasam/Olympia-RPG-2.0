package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class TpScroll extends CustomItem {

    Location[] locations = new Location[] {
            new Location(Bukkit.getWorld("world"), 1749.679, 71, 10485.944, -90.3f, -1.8f),
            new Location(Bukkit.getWorld("world"), 9491.583, 66, 1239.466, 90, -1.2f),
            new Location(Bukkit.getWorld("world"), -1174.568, 77, 570.859, -133.2f, -3.1f),
            new Location(Bukkit.getWorld("world"), 7106.193, 83, -6442.081, 0.9f, 1.5f),
            new Location(Bukkit.getWorld("world"), -9558.103, 80, 3536.954, -176.6f, -12.3f),
            new Location(Bukkit.getWorld("world"), -9779.456, 63, -6544.3, 179.7f, -20.1f),
            new Location(Bukkit.getWorld("world"), -5068.754, 76, 4793.934, -45.4f, -10.8f)
    };

    public TpScroll() {
        super(null);
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.PAPER, 16);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Scroll of Teleportation");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Warping:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Teleports you to your", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "nearest capital city."}));
        item.setItemMeta(meta);
        this.r = new ShapedRecipe(item);
        r.shape("ara", "rpr", "ara");
        r.setIngredient('a', Material.AIR);
        r.setIngredient('r', Material.REDSTONE);
        r.setIngredient('p', Material.PAPER);
        Bukkit.getServer().addRecipe(r);
    }

    @Override
    protected void action(Action action, Player player, ItemStack item) {
    }

    @EventHandler
    public void onAttack(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().equals((r.getResult().getItemMeta())) && e.getPlayer().getWorld().equals(locations[0].getWorld()) && e.getAction() == Action.RIGHT_CLICK_AIR) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*10, 255), true);
            Player p = e.getPlayer();
            e.setCancelled(true);
            new TimedEffect(10) {

                @EventHandler
                public void onPlayerDamage(EntityDamageByEntityEvent e) {
                    if (e.getEntity() == p || e.getDamager() == p) {
                        p.sendMessage(ChatColor.RED + "Teleportation cancelled due to damage.");
                        this.cancel();
                    }
                }
                @Override
                public void end() {
                    Location pLoc = e.getPlayer().getLocation();
                    Location min = locations[0];
                    double distSq = min.distanceSquared(pLoc);
                    for (Location l : locations) {
                        double testDist = l.distanceSquared(pLoc);
                        if (testDist < distSq) {
                            min = l;
                            distSq = testDist;
                        }
                    }
                    int amount = e.getItem().getAmount();
                    amount--;
                    if (amount <= 0) {
                        e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().first(e.getItem()), null);
                    } else {
                        e.getItem().setAmount(amount);
                    }
                    e.getPlayer().teleport(min);
                    e.getPlayer().sendMessage(ChatColor.YELLOW + "Teleporting you to your nearest capital city.");
                }
            };
        }
    }
}
