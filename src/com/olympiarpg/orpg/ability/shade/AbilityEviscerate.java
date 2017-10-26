package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.ability.effect.AOEParticlesEffect;
import com.olympiarpg.orpg.ability.effect.BleedEffect;
import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AbilityEviscerate extends Ability {

    public AbilityEviscerate() {
        super("Eviscerate", 15);
    }

    @Override
    public boolean action(Player p) {
        new AOEParticlesEffect(EnumParticle.SWEEP_ATTACK, p.getLocation(), p.getUniqueId(), 1, 8, 60, 0) {
            @Override
            public void run() {
                count++;
                EffectLibrary.discParticles(l, particle, data[0], range, true);
                for (Entity e : Utils.getNearbyEntities(l, range, 3, range)) {
                    if (e instanceof LivingEntity && !e.getUniqueId().equals(shooter)) {
                        OlympiaRPG.INSTANCE.damage((LivingEntity) e, damage, p, false);
                        //new BleedEffect(p.getUniqueId(), (LivingEntity)e, 10, 5);
                    }
                }
                if (this.count > this.runs) {
                    this.cancel();
                }
            }
        };
        return true;
    }
}
