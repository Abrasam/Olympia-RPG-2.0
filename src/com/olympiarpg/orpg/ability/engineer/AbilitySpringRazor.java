package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilitySpringRazor extends Ability {

    public AbilitySpringRazor() {
        super("Spring Razor", 15);
    }

    @Override
    public boolean action(Player p) {
        Block b = p.getTargetBlock(OlympiaRPG.transparent, 15);
        Location l = b.getLocation();
        FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.GRAY).withColor(Color.WHITE).withColor(Color.BLACK).with(FireworkEffect.Type.BURST).build();
        final Firework fw = l.getWorld().spawn(l, Firework.class);
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
        new BukkitRunnable() {
            @Override
            public void run() {
                OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.SWEEP_ATTACK,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),4,4,4, 0, 100, 0));
                l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_OPEN, 1, 5);
                for (Entity e : Utils.getNearbyEntities(l, 6, 6, 6)) {
                    if (e instanceof LivingEntity && e != p) {
                        OlympiaRPG.INSTANCE.damage((LivingEntity) e, 60, p, false);
                        addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.WEAKNESS, 15*20, 2), (LivingEntity)e, p);
                    }
                }
            }
        }.runTaskLater(OlympiaRPG.INSTANCE, 20);
        return true;
    }
}
