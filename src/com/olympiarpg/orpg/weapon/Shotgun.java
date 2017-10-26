package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import com.olympiarpg.orpg.util.Abilities;
import com.olympiarpg.orpg.util.PClass;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Shotgun extends Gun {

    public Shotgun() {
        super(PClass.ENGINEER);
    }

    @Override
    protected void cooldownAction(Action action, Player player, ItemStack item) {
        SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(player.getUniqueId());
        if (sp.hasAbility(Abilities.Shotguns.ab) && action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            EffectLibrary.coneEffect(EnumParticle.SMOKE_LARGE, player.getUniqueId(), 5, 21, null, 0);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_IMPACT, 5, 1);
        }
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.GOLD_HOE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Shotgun");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Shotgun Shot:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Fires a high spread", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "shot once per second."}));
        item.setItemMeta(meta);
        this.r = new ShapedRecipe(item);
        r.shape("iii", "sgg");
        r.setIngredient('i', Material.IRON_INGOT);
        r.setIngredient('s', Material.STICK);
        r.setIngredient('g', Material.SULPHUR);
        Bukkit.getServer().addRecipe(r);
    }
}
