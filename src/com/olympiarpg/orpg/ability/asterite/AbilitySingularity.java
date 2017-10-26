package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilitySingularity extends Ability {

    public AbilitySingularity() {
        super("Singularity", 40);
    }

    @Override
    public boolean action(Player p) {
        new BlackHole(p, p.getTargetBlock(OlympiaRPG.transparent, 25).getLocation().add(new Vector(0, 10, 0)), 25, 5).runTaskTimer(OlympiaRPG.INSTANCE, 1, 4);
        return true;
    }

    private class BlackHole extends BukkitRunnable {

        int range;
        Location l;
        int runs;
        int count;
        Player p;

        private BlackHole(Player p, Location l, int range, int time) {
            this.p = p;
            this.l = l;
            this.range = range;
            this.runs = time * 5;
            this.count = 0;
        }

        @Override
        public void run() {
            count++;
            for (Entity e : Utils.getNearbyEntities(l, range, range, range)) {
                if ((e instanceof LivingEntity || e instanceof Item) && !(e.equals(p)) && !(e instanceof ArmorStand)) {
                    SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
                    if (e instanceof Player && sp.hasParty() && sp.getParty().inParty((Player)e)) {
                        continue;
                    }
                    Vector vec = e.getLocation().toVector();
                    Vector vec2 = l.toVector();
                    e.setVelocity(vec2.subtract(vec).multiply(0.1f));
                }
            }
            for (int i = 0; i < 100; i++) {
                Vector vec = EffectLibrary.getRandomVector().multiply(5);
                l.add(vec);
                l.getWorld().playEffect(l, Effect.WITCH_MAGIC, 0);
                l.getWorld().playEffect(l, Effect.LARGE_SMOKE, 0);
                l.subtract(vec);
            }
            EffectLibrary.sphereParticles(l, EnumParticle.EXPLOSION_LARGE, 0);
            if (count > runs) {
                this.cancel();
            }
        }
    }
}
