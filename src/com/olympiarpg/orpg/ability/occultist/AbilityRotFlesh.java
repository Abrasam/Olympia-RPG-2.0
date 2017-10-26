package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class AbilityRotFlesh extends PassiveAbility {

    Random rnd = new Random();

    public AbilityRotFlesh() {
        super("Rotflesh");
    }

    @EventHandler
    public void onKill(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getEntity() != e.getDamager() && e.getDamager() != null) {
            SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getDamager().getUniqueId());
            if (splayer != null && splayer.hasAbility(this) && rnd.nextInt(100) < 5) {
                heal((LivingEntity)e.getDamager(), 5, (Player)e.getDamager());
                OlympiaRPG.INSTANCE.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.HEART, false, (float) e.getDamager().getLocation().getX(), (float) e.getDamager().getLocation().getY(), (float) e.getDamager().getLocation().getZ(), 1f, 1f, 1f, 0, 25, 0));
            }
        }
    }
}
