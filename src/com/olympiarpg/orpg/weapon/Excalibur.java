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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Excalibur extends CustomItem {

    public Excalibur() {
        super(PClass.VANGUARD);
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Excalibur");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Soulbinder:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Steal players souls", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "when killing them.", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Each soul boosts", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "your damage.", ChatColor.DARK_PURPLE + "Souls: 0"}));
        item.setItemMeta(meta);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 5);
        this.r = new ShapedRecipe(item);
        r.shape("e", "e", "d");
        r.setIngredient('e', Material.EMERALD);
        r.setIngredient('d', Material.DIAMOND_SWORD);
        Bukkit.getServer().addRecipe(r);
    }

    @Override
    protected void action(Action action, Player player, ItemStack item) {
        return;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            if (p.getItemInHand().getType() == Material.DIAMOND_SWORD && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().getLore().get(0).equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Soulbinder:")) {
                if (e.getEntity() instanceof LivingEntity) {
                    SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getDamager().getUniqueId());
                    if (splayer != null  && splayer.getPClass() == PClass.VANGUARD) {
                        ArrayList<String> lore = (ArrayList<String>) p.getItemInHand().getItemMeta().getLore();
                        int count = Integer.valueOf(ChatColor.stripColor(lore.get(5)).substring(7));
                        double mod = (100+(1*count))/100f;
                        mod = Math.min(mod, 2);
                        e.setDamage(e.getDamage() * mod);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getEntity().getKiller() instanceof Player) {
                Player p = (Player)e.getEntity().getKiller();
                if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().getLore().get(0) != null && p.getItemInHand().getItemMeta().getLore().get(0).equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Soulbinder:")) {
                    ArrayList<String> lore = (ArrayList<String>) p.getItemInHand().getItemMeta().getLore();
                    int count = Integer.valueOf(ChatColor.stripColor(lore.get(5)).substring(7));
                    lore.remove(5);
                    count++;
                    lore.add(ChatColor.DARK_PURPLE + "Souls: " + count);
                    ItemMeta meta = p.getItemInHand().getItemMeta();
                    meta.setLore(lore);
                    ItemStack item = p.getItemInHand();
                    item.setItemMeta(meta);
                    p.setItemInHand(item);
                }
            }
        }
    }
}
