package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.PClass;
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
import java.util.Random;
import java.util.function.ObjLongConsumer;

public class RtpScroll extends CustomItem {

    private static Random rnd = new Random();

    public RtpScroll() {
        super(null);
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.PAPER, 4);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Scroll of Displacement");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Warping:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Teleports you to a", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "random location."}));
        item.setItemMeta(meta);
        this.r = new ShapedRecipe(item);
        r.shape("ara", "cpc", "ara");
        r.setIngredient('a', Material.AIR);
        r.setIngredient('r', Material.REDSTONE);
        r.setIngredient('p', Material.PAPER);
        r.setIngredient('c', Material.COMPASS);
        Bukkit.getServer().addRecipe(r);
    }

    @Override
    protected void action(Action action, Player player, ItemStack item) {
    }

    @EventHandler
    public void onAttack(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().equals((r.getResult().getItemMeta())) && e.getPlayer().getWorld().getName().equals("world") && e.getAction() == Action.RIGHT_CLICK_AIR) {
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

                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*20, 255));
                    Location pLoc = e.getPlayer().getLocation().clone();
                    pLoc.setY(255);
                    int count = 0;
                    do {
                        count++;
                        int x = rnd.nextInt(28000 - 14000);
                        int z = rnd.nextInt(28000 - 14000);
                        pLoc.setX(x);
                        pLoc.setZ(z);
                    } while (!OlympiaRPG.transparent.contains(pLoc.getBlock().getType()) || count > 50);
                    int amount = e.getItem().getAmount();
                    amount--;
                    if (amount <= 0) {
                        e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().first(e.getItem()), null);
                    } else {
                        e.getItem().setAmount(amount);
                    }
                    e.getPlayer().teleport(pLoc);
                    e.getPlayer().sendMessage(ChatColor.YELLOW + "Teleporting you to a random location. You have resistance so don't worry about the fall.");
                }
            };
        }
    }
}
