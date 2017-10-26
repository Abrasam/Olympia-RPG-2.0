package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityDash extends Ability implements InCity {

    public AbilityDash() {
        super("Dash", 15);
    }

    @Override
    public boolean action(Player p) {
        Block b = getValidLocation(p.getTargetBlock(OlympiaRPG.transparent, 15));
        Location l = b.getLocation().clone();
        l.setDirection(p.getLocation().getDirection());
        for (Block bl : p.getLineOfSight(OlympiaRPG.transparent, 15)) {
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE,false,(float)bl.getLocation().getX(),(float)bl.getLocation().getY(),(float)bl.getLocation().getZ(),0.1f,0.1f,0.1f, 0, 3, 0));
        }
        OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.SWEEP_ATTACK,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),2,2,2, 0, 100, 0));
        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_OPEN, 1, 5);
        for (Entity e : Utils.getNearbyEntities(l, 5, 5, 5)) {
            if (e instanceof LivingEntity && e != p) {
                OlympiaRPG.INSTANCE.damage((LivingEntity) e, 65, p, false);
                addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.WEAKNESS, 15*20, 2), (LivingEntity)e, p);
            }
        }
        p.teleport(l);
        return true;
    }

    private Block getValidLocation(Block l) {
        if (OlympiaRPG.transparent.contains(l.getType()) || l.getLocation().getY() >= 255) {
            return l;
        } else {
            return getValidLocation(l.getRelative(BlockFace.UP));
        }
    }
}
