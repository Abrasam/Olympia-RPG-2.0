package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import com.olympiarpg.orpg.util.PClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BowOfBurningGold extends CustomItem {

    public BowOfBurningGold() {
        super(PClass.HUNTSMAN);
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Bow of Burning Gold");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Triple Shot:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "You fire 3 arrows per shot."}));
        item.setItemMeta(meta);
        item.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
        item.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
        item.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        item.addEnchantment(Enchantment.DURABILITY, 3);
        this.r = new ShapedRecipe(item);
        r.shape("sga", "sag", "sga");
        r.setIngredient('s', Material.STRING);
        r.setIngredient('g', Material.GOLD_INGOT);
        r.setIngredient('a', Material.AIR);
        Bukkit.getServer().addRecipe(r);
    }

    @Override
    protected void action(Action action, Player player, ItemStack item) {
        return;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player && ((Player)e.getEntity().getShooter()).getItemInHand().hasItemMeta() && ((Player)e.getEntity().getShooter()).getItemInHand().getItemMeta().hasLore() && ((Player)e.getEntity().getShooter()).getItemInHand().getItemMeta().getLore().get(0).equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Triple Shot:")) {
            SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(((Player) e.getEntity().getShooter()).getUniqueId());
            if (splayer != null && splayer.getPClass() == PClass.HUNTSMAN) {
                for (int i = 0; i < 5; i++) {
                    Arrow a = (Arrow) ((Player) e.getEntity().getShooter()).getLocation().getWorld().spawnEntity(e.getEntity().getLocation().add(((Player) e.getEntity().getShooter()).getLocation().getDirection()), EntityType.ARROW);
                    a.setShooter(e.getEntity().getShooter());
                    a.setBounce(false);
                    a.setVelocity(e.getEntity().getVelocity().add(EffectLibrary.getRandomVector().multiply(0.2)));
                }
            }
        }
    }
}
