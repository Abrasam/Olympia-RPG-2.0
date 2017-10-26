package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.PClass;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class HandGun extends Gun {

    public HandGun() {
        super(PClass.ENGINEER);
    }

    @Override
    protected void cooldownAction(Action action, Player p, ItemStack item) {
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            LivingEntity e = EffectLibrary.getTargetedEntity(p, 30);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_IMPACT,5,1);
            double distance = 30;
            if (e!= null) {
                e.damage(17, p);
                distance = e.getLocation().distance(p.getLocation());
            }
            Vector direction = p.getLocation().getDirection();
            for (double q = 0.5f; q <= distance; q += 0.5f) {
                Location l = p.getEyeLocation().add(direction.clone().multiply(q));
                OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.CRIT, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0f, 0f, 0f, 0, 50));
            }

        }
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.GOLD_HOE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Handgun");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Pistol Shot:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Fires a single", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "shot once per second."}));
        item.setItemMeta(meta);
        this.r = new ShapedRecipe(item);
        r.shape("ii", "sg");
        r.setIngredient('i', Material.IRON_INGOT);
        r.setIngredient('s', Material.STICK);
        r.setIngredient('g', Material.SULPHUR);
        Bukkit.getServer().addRecipe(r);
    }
}
