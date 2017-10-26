package com.olympiarpg.orpg.ability.occultist;

import com.olympiarpg.orpg.ability.effect.MovingParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
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
import org.bukkit.util.Vector;

public class AbilityBoneSpear extends Ability {

    public AbilityBoneSpear() {
        super("Bone Spear", 15);
    }

    @Override
    public boolean action(Player p) {
        Location l = p.getEyeLocation();
        Vector v = l.getDirection();
        new MovingParticlesEffect(p.getUniqueId(), EnumParticle.SPIT, l, v, 40, 55, 1) {

            @Override
            public void run() {
                super.run();
                boolean flag = false;
                for (Entity e : Utils.getNearbyEntities(this.location, 0.5f, 0.5f, 0.5f)) {
                    if (e instanceof LivingEntity && !(e == p)) {
                        flag = true;
                    }
                }
                if (flag) {
                    this.cancel();
                    FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(true).withColor(Color.WHITE).with(FireworkEffect.Type.STAR).build();
                    final Firework fw = location.getWorld().spawn(location, Firework.class);
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
        };
        return true;
    }
}
