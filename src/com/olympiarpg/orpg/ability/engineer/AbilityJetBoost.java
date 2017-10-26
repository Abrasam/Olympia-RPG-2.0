package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class AbilityJetBoost extends Ability implements InCity {

    public AbilityJetBoost() {
        super("Jet Boost", 18);
    }

    @Override
    public boolean action(Player p) {
        p.setVelocity(p.getLocation().getDirection().multiply(0.25).setY(2));
        Location l = p.getLocation();
        for(int x = 0; x < 20; x++) {
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.FLAME,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),1,1,1, 0, 15, 0));
        }
        new TimedEffect(15) {
            @EventHandler
            public void onDamage(EntityDamageEvent e) {
                if (e.getEntity() == p && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                    e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.EXPLOSION_HUGE, 0);
                    for (Entity en : e.getEntity().getNearbyEntities(5, 3, 5)) {
                        if (en instanceof LivingEntity && en != p) {
                            OlympiaRPG.INSTANCE.damage((LivingEntity) en, 40, p, false);
                        }
                    }
                    this.run();
                }
            }
        };
        return true;
    }
}
