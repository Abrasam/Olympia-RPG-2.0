package com.olympiarpg.orpg.weapon;

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
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Polearm extends CooldownItem {

    public Polearm() {
        super(PClass.OCCULTIST);
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Polearm");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Safe Space:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Keeps your foes away and", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "slightly nauseated."}));
        item.setItemMeta(meta);
        item.addEnchantment(Enchantment.KNOCKBACK, 2);
        this.r = new ShapedRecipe(item);
        r.shape("f", "f", "s");
        r.setIngredient('f', Material.SLIME_BALL);
        r.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(r);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            if (p.getItemInHand().getType() == Material.DIAMOND_SWORD && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().getLore().get(0).equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Safe Space:")) {
                if (e.getEntity() instanceof LivingEntity) {
                    SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getDamager().getUniqueId());
                    if (splayer != null && splayer.getPClass() == PClass.OCCULTIST) {
                        e.setDamage(12);
                        if (!hasCooldown(p)) {
                            ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 5));
                            ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3, 3));
                            addCooldown(p, 10000);
                        }
                    }

                }
            }
        }

    }

    @Override
    protected void cooldownAction(Action action, Player player, ItemStack item) {
        return;
    }
}
