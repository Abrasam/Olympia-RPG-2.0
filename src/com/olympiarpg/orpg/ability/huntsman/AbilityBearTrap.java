package com.olympiarpg.orpg.ability.huntsman;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AbilityBearTrap extends Ability {

    public AbilityBearTrap() {
        super("Bear Trap", 30);
    }

    @Override
    public boolean action(Player p) {
        Block b = p.getTargetBlock(OlympiaRPG.transparent, 5);
        if (b == null || b.getType() == Material.AIR) {
            return false;
        }
        Location l = b.getLocation();
        //TODO: SEE HARRY ABOUT EFFECTS HERE.
        new MovingParticlesEffect(p.getUniqueId(), EnumParticle.FOOTSTEP, l.add(0, .5, 0), new Vector(0, 0, 0), 25, 50, 5, Effect.EXPLOSION) {

            Player player = p;
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
                            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.SWEEP_ATTACK,false,(float)l.getX(),(float)l.getY()+1.5f,(float)l.getZ(),0.1f,0.1f,0.1f, 0, 5, 0));
                            l.getWorld().playSound(l, Sound.BLOCK_ANVIL_PLACE, 2, 1f);
                            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 20*15, 127), (LivingEntity)p, Bukkit.getPlayer(shooter));
                            OlympiaRPG.INSTANCE.damage((LivingEntity)p, 50, player, false);

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
