package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AbilityDodge extends Ability {

    public AbilityDodge() {
        super("Dodge", 40);
    }

    @Override
    public boolean action(Player p) {
        new TimedEffect(10) {
            @EventHandler
            public void onDamage(EntityDamageByEntityEvent e) {
                if (e.getEntity() == p && rnd.nextInt(2) == 0) {
                    Location l = e.getEntity().getLocation();
                    OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.SWEEP_ATTACK, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 1, 1, 1, 0, 100));
                    e.setCancelled(true);
                }
            }
        };
        return true;
    }
}
