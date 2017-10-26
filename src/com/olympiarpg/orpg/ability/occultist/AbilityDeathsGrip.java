package com.olympiarpg.orpg.ability.occultist;

        import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.ability.huntsman.AbilityGrapplingHook;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityDeathsGrip extends Ability {

    public AbilityDeathsGrip() {
        super("Death's Grip", 10);
    }

    @Override
    public boolean action(Player p) {
        Entity e = EffectLibrary.getTargetedEntity(p, 30);
        if (e != null && e instanceof LivingEntity) {
            for (Block b : p.getLineOfSight(OlympiaRPG.transparent, 30)) {
                Location l = b.getLocation();
                OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.CRIT, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0, 0, 0, 0, 1));
            }
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 5*20, 127), (LivingEntity) e, p);
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.WEAKNESS, 5*20, 127), (LivingEntity) e, p);
            AbilityGrapplingHook.propel(e, p.getLocation());
            return true;
        }
        return false;
    }
}
