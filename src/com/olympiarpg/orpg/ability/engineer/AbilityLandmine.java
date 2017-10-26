package com.olympiarpg.orpg.ability.engineer;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.ability.effect.MovingParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AbilityLandmine extends Ability {

    public AbilityLandmine() {
        super("Landmine", 30);
    }

    @Override
    public boolean action(Player p) {
        Block b = p.getTargetBlock(OlympiaRPG.transparent, 5);
        if (b == null || b.getType() == Material.AIR) {
            return false;
        }
        Location l = b.getLocation();
        new MovingParticlesEffect(p.getUniqueId(), EnumParticle.SMOKE_NORMAL, l, new Vector(0, 0, 0), 25, 100, 5, Effect.EXPLOSION) {

            int count = 120*4;

            @Override
            public void run() {
                boolean flag = false;
                if (location.distanceSquared(origin) < distance) {
                    location.add(direction);
                    EffectLibrary.sphereParticles(location, particle, 0);
                    for (Entity p : Utils.getNearbyEntities(location, 0.5f, 1f, 0.5f)) {
                        if (p instanceof LivingEntity && !p.getUniqueId().equals(shooter)) {
                            flag = true;
                            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE,false,(float)l.getX(),(float)l.getY()+1.5f,(float)l.getZ(),0,0,0, 0, 1, 0));
                            l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXPLODE, 2, 0.1f);
                            OlympiaRPG.INSTANCE.damage((LivingEntity) p, dmg, Bukkit.getPlayer(shooter), false);
                        }
                    }

                } else if (location.distanceSquared(origin) == distance) {
                    location.getWorld().playEffect(location, end, 0);
                    location.add(direction);
                } else {
                    this.cancel();
                }
                count--;
                if (flag || count <= 0) {
                    this.cancel();
                }
            }

        };
        return true;
    }
}
