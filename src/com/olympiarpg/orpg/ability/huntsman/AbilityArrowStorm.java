package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.util.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AbilityArrowStorm extends Ability implements Listener {

    List<Arrow> arrows = new ArrayList<Arrow>();

    public AbilityArrowStorm() {
        super("Arrow Storm", 50);
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    @Override
    public boolean action(Player p) {
        Block b = p.getTargetBlock(OlympiaRPG.transparent, 10);
        for (int i = 0; i < 8; i++) {
            if (Utils.isPathable(b.getRelative(BlockFace.UP).getType())) {
                b = b.getRelative(BlockFace.UP);
            }
        }
        Location loc = b.getLocation();

        new BukkitRunnable() {

            int count = 0;

            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Location l = loc.clone();
                    l.add(EffectLibrary.getRandomVector().setY(0).multiply(5).multiply(rnd.nextDouble()));
                    //l.getWorld().createExplosion(l, 0);
                    Arrow sb = l.getWorld().spawn(l, Arrow.class);
                    sb.setShooter(p);
                    sb.setVelocity(sb.getVelocity().setX(0).setY(-0.75).setZ(0));
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (!sb.isDead()) {
                                sb.remove();
                            }
                        }
                    }.runTaskLater(OlympiaRPG.INSTANCE, 20*5);
                    arrows.add(sb);
                }
                count++;
                if (count > 10*20) {
                    this.cancel();
                }
            }

        }.runTaskTimer(OlympiaRPG.INSTANCE, 5, 1);

        return true;
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity && arrows.contains(e.getDamager())) {
            if (e.getEntity() == ((Projectile)e.getDamager()).getShooter()) {
                e.setCancelled(true);
            } else {
                e.setCancelled(true);
                OlympiaRPG.INSTANCE.damage((LivingEntity) e.getEntity(), 40, (Player)((Arrow)e.getDamager()).getShooter(), false);
            }
        }
    }
}
