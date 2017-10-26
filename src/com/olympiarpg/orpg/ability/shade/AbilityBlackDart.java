package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityBlackDart extends Ability {

    public AbilityBlackDart() {
        super("Black Dart", 2);
    }

    @Override
    public boolean action(Player p) {
        Arrow arrow = p.getWorld().spawnArrow(p.getEyeLocation().add(p.getLocation().getDirection()), p.getLocation().getDirection(), 5, 0.1f);
        arrow.setShooter(p);
        new BlackDartEffect(arrow).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        return true;
    }

    private static class BlackDartEffect extends BukkitRunnable implements Listener {
        Arrow a;

        BlackDartEffect(Arrow a) {
            this.a = a;
            OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
        }

        @Override
        public void run() {
            if (a != null && !a.isDead() && !(a.isOnGround())) {
                Location l = a.getLocation();
                PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FALLING_DUST, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0.1f, 0.1f, 0.1f, 0, 10, 173);
                for (Player player : OlympiaRPG.INSTANCE.getServer().getOnlinePlayers()) {
                    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                }
            } else {
                a = null;
                this.cancel();
                HandlerList.unregisterAll(this);
            }
        }

        @EventHandler
        public void onArrowHit(EntityDamageByEntityEvent e) {
            if (e.getDamager() == this.a) {
                e.setCancelled(true);
                this.cancel();
                OlympiaRPG.INSTANCE.damage((LivingEntity) e.getEntity(), 15, (Player)((Arrow)e.getDamager()).getShooter(), false);
            }
        }
    }
}
