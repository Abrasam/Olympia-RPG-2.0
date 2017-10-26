package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.ability.effect.AOEParticlesEffect;
import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AbilitySporepatch extends Ability {

    public AbilitySporepatch() {
        super("Sporepatch", 30);
    }

    @Override
    public boolean action(Player p) {
        new AOEPoison(EnumParticle.FALLING_DUST, p.getLocation(), p.getUniqueId(), 10*10, 5, 0, 18);
        return true;
    }

    private class AOEPoison extends AOEParticlesEffect {

        public AOEPoison(EnumParticle particle, Location l, UUID shooter, int runs, int range, double damage, int... data) {
            super(particle, l, shooter, runs, range, damage, data);
        }

        @Override
        public void run() {
            count++;
            EffectLibrary.discParticles(l, particle, data[0], range, true);
                for (Entity e : Utils.getNearbyEntities(this.l, this.range, 2, this.range)) {
                    if (e instanceof LivingEntity && !(e.getUniqueId().equals(shooter))) {
                        if (count % 10 == 0) {
                            OlympiaRPG.INSTANCE.damage((LivingEntity) e, 20, Bukkit.getPlayer(shooter), false);
                        }
                    }
                }
            if (this.count > this.runs) {
                this.cancel();
            }
        }
    }
}
