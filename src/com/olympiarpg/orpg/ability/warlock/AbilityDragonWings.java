package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class AbilityDragonWings extends Ability implements InCity {

    public AbilityDragonWings() {
        super("Dragon Wings", 15);
    }

    @Override
    public boolean action(Player p) {
        p.setVelocity(new Vector(0, 2, 0));
        Location l = p.getLocation();
        for(int x = 0; x < 20; x++) {
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),1,1,1, 0, 15, Material.MAGMA.getId()));
        }
        OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),1,1,1, 0, 15,0));
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
