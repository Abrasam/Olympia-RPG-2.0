package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class AbilityPropulsion extends Ability implements InCity {

    public AbilityPropulsion() {
        super("Propulsion", 15);
    }

    @Override
    public boolean action(Player p) {
        p.setVelocity(p.getVelocity().add(p.getLocation().getDirection().multiply(3)));
        Location l = p.getLocation();
        for(int x = 0; x < 20; x++) {
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),1,1,1, 0, 15, Material.ICE.getId()));
        }
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1, 0.01f);
        new TimedEffect(15) {
            @EventHandler
            public void onDamage(EntityDamageEvent e) {
                if (e.getEntity() == p && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                }
            }
        };
        return true;
    }
}
