package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.PClass;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FireStaff extends CooldownItem {

    public FireStaff() {
        super(PClass.WARLOCK);
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Staff of Embers");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Flame Touch:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Deals a small amount", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "of fire damage."}));
        item.setItemMeta(meta);
        this.r = new ShapedRecipe(item);
        r.shape("f", "s");
        r.setIngredient('f', Material.FIREBALL);
        r.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(r);
    }

    @Override
    protected void cooldownAction(Action action, Player player, ItemStack item) {
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            Location loc = player.getEyeLocation().add(player.getLocation().getDirection());
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.FLAME, false, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), 0.2f, 0.15f, 0.2f, 0, 50));
            loc.add(player.getLocation().getDirection().multiply(2));
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.FLAME, false, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), 0.2f, 0.15f, 0.2f, 0, 50));
            LivingEntity e = EffectLibrary.getTargetedEntity(player, 5);
            if (e != null) {
                e.damage(14, player);
                e.setFireTicks(2*20);
            }
        }
    }
}
