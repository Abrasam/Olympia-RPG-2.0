package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.ability.effect.AOEParticlesEffect;
import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilityAstralFire extends Ability {

    public AbilityAstralFire() {
        super("Astral Fire", 20);
    }

    @Override
    public boolean action(Player p) {
        Location loc = p.getEyeLocation();
        Vector movement = p.getEyeLocation().getDirection();
        new BukkitRunnable() {

            @Override
            public void run() {
                boolean flag = false;
                loc.add(movement);
                EffectLibrary.sphereParticles(loc, EnumParticle.DRAGON_BREATH, 0);
                movement.setY(movement.getY() - 0.01);
                if (loc.getWorld().getBlockAt(loc).getType() != Material.AIR) {
                    loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 0);
                    loc.getWorld().playSound(loc, Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 1, 1);
                    new AOEParticlesEffect(EnumParticle.DRAGON_BREATH, loc, p.getUniqueId(), 10*10, 4, 15);
                    this.cancel();
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);

        return true;
    }
}
