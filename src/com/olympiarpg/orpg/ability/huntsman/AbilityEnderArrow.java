package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

public class AbilityEnderArrow extends Ability {

    public AbilityEnderArrow() {
        super("Ender Arrow", 45);
    }

    @Override
    public boolean action(Player p) {
        new TimedEffect(60) {
            @EventHandler
            public void onArrowLand(ProjectileHitEvent e) {
                if (e.getEntity().getShooter()==p) {
                    OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.PORTAL,false,(float)p.getLocation().getX(),(float)p.getLocation().getY(),(float)p.getLocation().getZ(),1f,2f,1f, 0, 100, 0));
                    Location l = e.getEntity().getLocation().setDirection(p.getLocation().getDirection());
                    p.teleport(l);
                    this.run();
                    OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.PORTAL,false,(float)l.getX(),(float)l.getY(),(float)l.getZ(),1f,2f,1f, 0, 100, 0));
                }
            }

        };
        return true;
    }
}
