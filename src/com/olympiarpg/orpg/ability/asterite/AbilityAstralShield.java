package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityAstralShield extends Ability {

    public AbilityAstralShield THIS = this;

    public AbilityAstralShield() {
        super("Astral Shield", 40);
    }

    @Override
    public boolean action(Player p) {
        BukkitRunnable br = new LingeringParticlesEffect(EnumParticle.CRIT_MAGIC, p.getUniqueId(), p, 5, 0, 30, 0);
        br.runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        new TimedEffect(30) {

            int amount = 70;


            @EventHandler
            public void onDamage(EntityDamageEvent e) {
                if (e.getEntity() == p) {
                    if (amount - e.getDamage() > 0) {
                        e.setDamage(0);
                        amount -= e.getDamage();
                    } else {
                        e.setDamage(e.getDamage()-amount);
                        this.run();
                        br.cancel();
                    }
                }
            }
        };
        return true;
    }
}

