package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilityBladewraith extends Ability {

    public AbilityBladewraith() {
        super("Bladewraith", 30);
    }

    private static final double cos10 = Math.cos((10/180f) * Math.PI);
    private static final double sin10 = Math.sin((10/180f) * Math.PI);

    @Override
    public boolean action(Player p) {

        new Bladewraith(p, new Vector(2, 0, 0)).runTaskTimer(OlympiaRPG.INSTANCE, 0, 1);
        new Bladewraith(p, new Vector(-2, 0, 0)).runTaskTimer(OlympiaRPG.INSTANCE, 0, 1);
        new Bladewraith(p, new Vector(0, 0, 2)).runTaskTimer(OlympiaRPG.INSTANCE, 0, 1);
        new Bladewraith(p, new Vector(0, 0, -2)).runTaskTimer(OlympiaRPG.INSTANCE, 0, 1);

        return true;
    }

    private Vector rot(Vector init) {
        Vector out = new Vector();
        return out.setX(init.getX()*cos10 + init.getZ()*sin10).setY((init.getY())).setZ(init.getZ()*cos10 - init.getX()*sin10); //Transformation matrix, yeah boi!!!
    }

    private class Bladewraith extends BukkitRunnable {

        Player p;
        Vector dir;
        int count = 72;

        Bladewraith(Player p, Vector v) {
            this.p = p;
            this.dir = v;
        }

        @Override
        public void run() {
            Arrow arrow = p.getWorld().spawn(p.getEyeLocation(), Arrow.class);
            arrow.setVelocity(dir);
            arrow.setShooter(p);
            arrow.setBounce(false);
            new BlackDartEffect(arrow).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
            dir = rot(dir);
            count--;
            if (count <= 0) {
                this.cancel();
            }
        }
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
            } else {
                if (a!=null) {a.remove();}
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
                if (!(e.getEntity() == this.a.getShooter())) {
                    OlympiaRPG.INSTANCE.damage((LivingEntity) e.getEntity(), 35, (Player) ((Arrow) e.getDamager()).getShooter(), false);
                }
            }
        }
    }
}
