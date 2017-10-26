package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;



public class AbilityArcaneAura extends Ability {

    public AbilityArcaneAura() {
        super("Arcane Aura", 60);
    }

    @Override
    public boolean action(Player p) {
        new LingeringParticlesEffect(EnumParticle.BLOCK_DUST, p.getUniqueId(), p, 5, 0, 60, 0, 199) {
            @Override
            public void run() {
                EffectLibrary.discParticles(p.getLocation(), EnumParticle.SPELL_WITCH, 0, 5, true);
                super.run();
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        new BukkitRunnable() {
            int count = 60/5;
            @Override
            public void run() {
                for (Entity e : p.getNearbyEntities(6, 3, 6)) {
                    if (e instanceof LivingEntity) {
                        OlympiaRPG.INSTANCE.damage((LivingEntity)e, 20, p, false);
                        Location loc = ((LivingEntity)e).getEyeLocation();
                        FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.PURPLE).withColor(Color.FUCHSIA).withColor(Color.WHITE).with(FireworkEffect.Type.BALL).build();
                        final Firework fw = loc.getWorld().spawn(loc, Firework.class);
                        FireworkMeta meta = fw.getFireworkMeta();
                        meta.addEffect(effect);
                        fw.setFireworkMeta(meta);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
                                fw.remove();
                            }
                        }.runTaskLater(OlympiaRPG.INSTANCE, 2);
                    }
                }
                count--;
                if (count < 0) {
                    this.cancel();
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 20, 20*5);
        return true;
    }
}
