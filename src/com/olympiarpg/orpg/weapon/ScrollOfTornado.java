package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Set;

public class ScrollOfTornado extends GenericScroll {

    public ScrollOfTornado() {
        super("Firenado Scroll", new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Better than Sharknado."}, 1, 1);
    }

    @Override
    protected void addRecipe() {
    }

    @Override
    protected void scrollEffect(Player player) {
        new BukkitRunnable() {
            Location loc;
            Player player;
            int runs = 0;
            int i = 0;

            public BukkitRunnable set(Player p) {
                player = p;
                loc = player.getTargetBlock((Set<Material>)null, 20).getLocation();
                return this;
            }

            public void run() {
                Vector v = EffectLibrary.getRandomVector().normalize().multiply(0.2);
                loc.add(v);
                runs++;
                float radius = 0.3f;
                for(double y = 0; y <= 50; y+=0.05) {
                    double x = radius*y * Math.cos(y+i);
                    double z = radius*y * Math.sin(y+i);
                    Location l = loc.clone();
                    l.setX(loc.getX() + x);
                    l.setZ(loc.getZ() + z);
                    l.setY(loc.getY() + y);
                    OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.FLAME, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0.2f, 0.15f, 0.2f, 0, 1));
                }
                    List<Entity> entities = player.getWorld().getEntities();
                    for (Entity e : entities) {
                        if (e instanceof LivingEntity) {
                            if (e.getLocation().distanceSquared(loc) < 4 && e != player && runs % 5 == 0) {
                                e.setVelocity(new Vector(0, 5, 0).add(EffectLibrary.getRandomVector().setY(0).normalize().multiply(3)));
                            } else if (e.getLocation().distanceSquared(loc) < 100 && e != player) {
                                e.setVelocity(e.getVelocity().add(loc.toVector().subtract(e.getLocation().toVector()).multiply(0.05)));
                            }
                        }
                    }
                loc.subtract(v);
                i--;
                if (runs > 100) {
                    this.cancel();
                }
            }
        }.set(player).runTaskTimer(OlympiaRPG.INSTANCE, 0, 2);
    }
}
