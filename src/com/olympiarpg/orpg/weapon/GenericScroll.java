package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
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

public abstract class GenericScroll extends CooldownItem {

    protected static Random rnd = new Random();
    private final int warmup;

    public GenericScroll(String name, String[] desc, int warmup, int amount) {
        super(null);
        this.warmup = warmup;
        ItemStack item = new ItemStack(Material.PAPER, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        meta.setLore(Arrays.asList(desc));
        item.setItemMeta(meta);
        this.r = new ShapedRecipe(item);
    }

    protected abstract void addRecipe();

    @Override
    protected void action(Action action, Player player, ItemStack item) {
    }

    @EventHandler
    public void onAttack(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().equals((r.getResult().getItemMeta())) && e.getAction() == Action.RIGHT_CLICK_AIR && !hasCooldown(e.getPlayer())) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*warmup, 255), true);
            Player p = e.getPlayer();
            e.setCancelled(true);
            new TimedEffect(warmup) {
                @Override
                public void end() {
                    scrollEffect(e.getPlayer());
                }
            };
            int amount = e.getItem().getAmount();
            amount--;
            e.getItem().setAmount(amount);
            addCooldown(e.getPlayer(), 100);
        }
    }

    @Override
    protected void cooldownAction(Action action, Player player, ItemStack item) {
    }

    protected abstract void scrollEffect(Player player);
}
