package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.PClass;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class AstralStaff extends CooldownItem {

    public AstralStaff() {
        super(PClass.ASTERITE);
    }

    @Override
    protected void cooldownAction(Action action, Player player, ItemStack item) {
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            Location loc = player.getEyeLocation().add(player.getLocation().getDirection().multiply(5));
            LivingEntity e = EffectLibrary.getTargetedEntity(player, 20);
            if (e != null) {
                e.damage(10, player);
                loc = e.getEyeLocation();
            }
            FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.PURPLE).withColor(Color.FUCHSIA).withColor(Color.WHITE).with(FireworkEffect.Type.BALL).build();
            final Firework fw = loc.getWorld().spawn(loc, Firework.class);
            FireworkMeta meta = fw.getFireworkMeta();
            meta.addEffect(effect);
            fw.setFireworkMeta(meta);

            new BukkitRunnable() {
                @Override
                public void run() {
                    fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
                    fw.remove();
                }
            }.runTaskLater(OlympiaRPG.INSTANCE, 2);
        }
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Astral Staff");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Starburst:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Creates an explosion on", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "your target dealing", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "some damage."}));
        item.setItemMeta(meta);
        this.r = new ShapedRecipe(item);
        r.shape("f", "s");
        r.setIngredient('f', Material.ENDER_PEARL);
        r.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(r);
    }
}
