package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class AbilityPhoenixHeart extends Ability {

    private Ability THIS = this;

    public AbilityPhoenixHeart() {
        super("Phoenix Heart", 180);
    }

    @Override
    public boolean action(Player p) {
        new TimedEffect(30) {
            @EventHandler(priority = EventPriority.HIGHEST)
            public void onDeath(EntityDamageEvent e) {
                if (e.getEntity() == p && ((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0) {
                    SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
                    e.setCancelled(true);
                    Location l = ((Player) e.getEntity()).getLocation();
                    OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.LAVA, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 1, 1, 1, 0, 100));
                    OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.HEART, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 1, 1, 1, 0, 100));
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.25);
                    run();
                }
            }
        };
        return true;
    }
}
