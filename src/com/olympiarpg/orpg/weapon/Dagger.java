package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.BleedEffect;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import com.olympiarpg.orpg.util.PClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Dagger extends CooldownItem {

    public Dagger() {
        super(PClass.SHADE);
    }

    @Override
    protected void cooldownAction(Action action, Player player, ItemStack item) {
        return;
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Dagger");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Bleed:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Your target takes bleed", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "damage for 3 seconds", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "after being hit."}));
        item.setItemMeta(meta);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 5);
        this.r = new ShapedRecipe(item);
        r.shape("f", "f", "s");
        r.setIngredient('f', Material.OBSIDIAN);
        r.setIngredient('s', Material.DIAMOND);
        Bukkit.getServer().addRecipe(r);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
            Player p = (Player)e.getDamager();
            if (p.getItemInHand().getType() == Material.DIAMOND_SWORD && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().getLore().get(0).equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Bleed:")) {
                SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getDamager().getUniqueId());
                if (splayer != null  && splayer.getPClass() == PClass.SHADE) {
                    if (!hasCooldown(p)) {
                        new BleedEffect(p.getUniqueId(), (LivingEntity)e.getEntity(), 2, 3);
                        addCooldown(p, 3500);
                    }
                }
            }
        }
    }
}
