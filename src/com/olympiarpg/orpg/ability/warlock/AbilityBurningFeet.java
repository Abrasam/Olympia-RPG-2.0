package com.olympiarpg.orpg.ability.warlock;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityBurningFeet extends Ability {

    public AbilityBurningFeet() {
        super("Blazing Feet", 40);
    }

    @Override
    public boolean action(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 3));
        new BukkitRunnable() {
            int count = 10*10;
            public void run() {
                count--;
                EffectLibrary.sphereParticles(p.getLocation(), EnumParticle.LAVA, 0);
                if (count <= 0) {
                    this.cancel();
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 1, 2);
        return true;
    }
}
