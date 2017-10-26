package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.ability.effect.MovingParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilityBurningTrail extends Ability {

    public AbilityBurningTrail() {
        super("Burning Trail", 25);
    }

    @Override
    public boolean action(Player p) {
        new BukkitRunnable() {

            int count = 10*4;

            @Override
            public void run() {
                count--;
                new MovingParticlesEffect(p.getUniqueId(), EnumParticle.FLAME, p.getLocation(), new Vector(0, 0, 0), 25, 18, 2) {

                    int count = 5*20;

                    @Override
                    public void run() {
                        super.run();
                        count--;
                        hit.clear();
                        if (count <= 0) {
                            this.cancel();
                        }
                    }

                };
                if (count < 0) {
                    this.cancel();
                }
            }

        }.runTaskTimer(OlympiaRPG.INSTANCE, 5, 5);
        return true;
    }
}
