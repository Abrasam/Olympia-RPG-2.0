package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static com.olympiarpg.orpg.ability.effect.EffectLibrary.getTargetedEntity;

public class AbilityEntangle extends Ability {

    public AbilityEntangle() {
        super("Entangle", 30);
    }

    public void addEffect(PotionEffect pe, LivingEntity r, Player p) {
        addPotionEffectIfNotAlly(pe, r, p);
    }

    public static class EntangleRunnable extends BukkitRunnable {

        public LivingEntity ent;
        public int ticks;
        public EntangleRunnable(LivingEntity e, int ticks, AbilityEntangle ab, Player p) {
            this.runTaskTimer(OlympiaRPG.INSTANCE, 0, 2);
            ent = e;
            this.ticks = ticks;
            PotionEffect noMove = new PotionEffect(PotionEffectType.SLOW, ticks, 127);
            ab.addPotionEffectIfNotAlly(noMove, e, p);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void run() {
            ticks -= 2;
            if(ticks < 0) {
                this.cancel();
            }
            Location l = ent.getLocation();
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),0.5f,0.75f,0.5f, 0, 15, Material.LEAVES.getId()));
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),0.5f,0.75f,0.5f, 0, 5, Material.LOG.getId()));
        }
    }

    @Override
    public boolean action(Player p) {
        LivingEntity en = getTargetedEntity(p, 15);
        if (en != null) {
            new EntangleRunnable(en, 8*20, this, p);
            return true;
        }
        return false;
    }
}
