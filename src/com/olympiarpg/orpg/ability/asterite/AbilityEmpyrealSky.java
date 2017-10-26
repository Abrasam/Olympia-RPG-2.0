package com.olympiarpg.orpg.ability.asterite;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class AbilityEmpyrealSky extends Ability implements Listener {

    private HashMap<Entity, UUID> inFlight = new HashMap<org.bukkit.entity.Entity, UUID>();

    public AbilityEmpyrealSky() {
        super("Empyreal Sky", 45);
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    @Override
    public boolean action(Player p) {
        Location l;
        Entity en = EffectLibrary.getTargetedEntity(p, 25);
        if (en instanceof LivingEntity && !(en instanceof Player)) {
            l = en.getLocation().add(new Vector(0, 15, 0));
        } else {
            l = p.getTargetBlock(OlympiaRPG.transparent, 25).getLocation().add(new Vector(0, 15, 0));
        }
        for (int j =0; j < 10; j++) {
            l.setDirection(new Vector(0, -1, 0));
            Vector vec = EffectLibrary.getRandomVector().multiply(new Vector(5, 2, 5));
            l.add(vec);
            Entity f = p.getWorld().spawnEntity(l, EntityType.FIREBALL);
            f.setVelocity(f.getLocation().getDirection().multiply(2));
            inFlight.put(f, p.getUniqueId());
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 5, 0f);
            l.subtract(vec);
        }
        return true;
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onBulletHit(ExplosionPrimeEvent e) {
        if (inFlight.keySet().contains(e.getEntity())) {
            e.setCancelled(true);
            for (Entity entity : e.getEntity().getNearbyEntities(5, 5, 5)) {
                if (entity instanceof LivingEntity && !(entity == Bukkit.getPlayer(inFlight.get(e.getEntity())))) {
                    OlympiaRPG.INSTANCE.damage((LivingEntity) entity, 72, Bukkit.getPlayer(inFlight.get(e.getEntity())), false);
                    ((LivingEntity) entity).setNoDamageTicks(20);
                }
            }
            inFlight.remove(e.getEntity());
            Location loc = e.getEntity().getLocation();
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, false, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), 0, 0, 0, 0, 1));
        }
    }
}
