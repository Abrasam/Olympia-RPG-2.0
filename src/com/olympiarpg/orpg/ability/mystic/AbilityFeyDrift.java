package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityFeyDrift extends Ability implements InCity {

    public AbilityFeyDrift() {
        super("Fey Drift", 20);
    }

    @Override
    public boolean action(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 8*20, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 8*20, 4));
        new AscensionRunnable(p);
        new BukkitRunnable() {

            @Override
            public void run() {
                p.setFlying(false);
                p.setAllowFlight(false);
            }

        }.runTaskLater(OlympiaRPG.INSTANCE, 20*10);
        return true;
    }

    private class AscensionRunnable extends BukkitRunnable implements Listener {
        static final double Height = 7;
        public Player p;
        public int Timer = 80;
        public boolean oldFlag = false;
        public boolean flightFlag = true;
        public AscensionRunnable(Player p) {
            this.p = p;
            p.setAllowFlight(true);
            p.setFlying(true);
            //p.setFlySpeed(0.2f);

            OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
            this.runTaskTimer(OlympiaRPG.INSTANCE, 0, 2);
        }
        @SuppressWarnings("deprecation")
        @Override
        public void run() {
            Timer--;
            if(Timer < 0) {
                this.cancel();
                p.setFlying(false);
                p.setAllowFlight(false);
            }
            Location l = p.getLocation();
            Location bloc = l.clone();
            double diff = 1000;
            for(int i = 0; i < 100; i++) {
                if(!bloc.getBlock().isEmpty()) {
                    diff = l.getY() - bloc.getY();
                    break;
                }
                bloc.setY(bloc.getY() - 1.0);
            }
            if(diff > Height) {
                p.setFlying(false);
                p.setAllowFlight(false);
                flightFlag = false;
            }else if(!flightFlag){
                p.setAllowFlight(true);
                p.setFlying(true);
                flightFlag = true;
            }
            boolean flag1 = diff > (Height / 2);
            if(oldFlag && !flag1) {
                p.setFlySpeed(0.3f);
                oldFlag = false;
            }
            if(!oldFlag && flag1) {
                p.setFlySpeed(0.15f);
                oldFlag = true;
            }
            //TODO remove the bit above :P
            p.setFlySpeed(0.05f);
            ////RENDER FX////
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.WATER_WAKE, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0.05f,0.05f, 0.05f, 0, 500));
            EffectLibrary.sphereParticles(l, EnumParticle.FALLING_DUST, Material.EMERALD_BLOCK.getId());
            EffectLibrary.sphereParticles(l, EnumParticle.TOTEM, 0);
        }

    }
}
