package com.olympiarpg.orpg.ability.warden;

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

public class AbilityEncase extends Ability {

    public AbilityEncase() {
        super("Encase", 35);
    }

    public static class FreezeRunnable extends BukkitRunnable {

        public LivingEntity ent;
        public int ticks;
        public FreezeRunnable(LivingEntity e, int ticks, AbilityEncase ab, Player p) {
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
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),0.5f,0.75f,0.5f, 0, 15, Material.ICE.getId()));
        }
    }

    @Override
    public boolean action(Player p) {
        LivingEntity en = getTargetedEntity(p, 25);
        if (en != null) {
            new FreezeRunnable(en, 7*20, this, p);
            return true;
        }
        return false;
    }
}
