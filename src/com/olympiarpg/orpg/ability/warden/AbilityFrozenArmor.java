package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class AbilityFrozenArmor extends Ability {

    public AbilityFrozenArmor() {
        super("Frozen Armour", 60);
    }

    @Override
    public boolean action(Player p) {
        new LingeringParticlesEffect(EnumParticle.BLOCK_DUST, p.getUniqueId(), p, 10, 0, 20, 0, Material.FROSTED_ICE.getId()).runTaskTimer(OlympiaRPG.INSTANCE, 0, 1);
        new TimedEffect(20) {


            @EventHandler
            public void onDamage(EntityDamageEvent e) {
                if (e.getEntity()==p) {
                    e.setDamage(e.getDamage()*0.65);
                }
            }
        };
        return true;
    }
}
