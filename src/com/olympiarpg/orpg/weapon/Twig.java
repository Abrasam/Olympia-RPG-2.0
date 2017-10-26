package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
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

public class Twig extends CooldownItem {

    public Twig() {
        super(PClass.MYSTIC);
    }

    @Override
    protected void cooldownAction(Action action, Player player, ItemStack item) {
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            Location loc = player.getEyeLocation().add(player.getLocation().getDirection());
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST, false, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), 0.2f, 0.15f, 0.2f, 0, 50, 18));
            loc.add(player.getLocation().getDirection().multiply(2));
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST, false, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), 0.2f, 0.15f, 0.2f, 0, 50, 18));
            LivingEntity e = EffectLibrary.getTargetedEntity(player, 5);
            if (e != null) {
                SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(player.getUniqueId());
                if (sp != null && sp.hasParty() && e instanceof Player && sp.getParty().inParty((Player) e)) {
                    Ability.heal(e, 3, player);
                    OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.HEART, false, (float)e.getEyeLocation().getX(), (float)e.getEyeLocation().getY(), (float)e.getEyeLocation().getZ(), 0.2f, 0.15f, 0.2f, 0, 5));
                } else {
                    e.damage(10, player);
                }
            }
        }
    }

    @Override
    protected void addRecipe() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Twig");
        meta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Discriminate:", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Heals friends but hurts", ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "your foes."}));
        item.setItemMeta(meta);
        this.r = new ShapedRecipe(item);
        r.shape("f", "s");
        r.setIngredient('f', Material.EMERALD);
        r.setIngredient('s', Material.STICK);
        Bukkit.getServer().addRecipe(r);
    }
}
