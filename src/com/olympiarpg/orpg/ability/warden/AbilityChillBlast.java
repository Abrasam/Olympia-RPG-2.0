package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityChillBlast extends Ability {

    public AbilityChillBlast() {
        super("Chill Blast", 3);
    }

    @Override
    public boolean action(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 0.01f);
        EffectLibrary.coneEffect(EnumParticle.BLOCK_DUST, p.getUniqueId(), 10, 13, new PotionEffect(PotionEffectType.SLOW, 20*2, 1), Material.ICE.getId());
        return true;
    }
}

/** For later use ;)
 * Snowball snowball = (Snowball)p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.SNOWBALL);
 snowball.setShooter(p);
 snowball.setVelocity(p.getLocation().getDirection().multiply(2));
 new BukkitRunnable() {

 Snowball snowball;

 @Override
 public void run() {
 if (snowball != null && !snowball.isDead()) {
 Location l = snowball.getLocation();
 PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.SNOWBALL, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0f, 0f, 0f, 0, 30);
 OlympiaRPG.sendParticlePacket(packet);
 } else {
 for (org.bukkit.entity.Entity e : snowball.getNearbyEntities(0.5, 0.5, 0.5)) {
 if (e instanceof LivingEntity && !(e instanceof ArmorStand)) {
 ((LivingEntity)e).damage(10d, (Player)snowball.getShooter());
 }
 }
 snowball = null;
 this.cancel();
 }
 }

 public BukkitRunnable set(Snowball s) {
 this.snowball = s;
 return this;
 }
 }.set(snowball).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
 */
