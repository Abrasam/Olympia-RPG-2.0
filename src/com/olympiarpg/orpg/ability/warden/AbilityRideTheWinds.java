package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityRideTheWinds extends Ability {

    public AbilityRideTheWinds() {
        super("Riding The Winds", 55);
    }

    @Override
    public boolean action(Player p) {
        new AscensionRunnable(p);
        return true;
    }

    private class AscensionRunnable extends BukkitRunnable implements Listener {
        static final double Height = 20;
        public Player p;
        public int Timer = 300;
        public boolean oldFlag = false;
        public boolean flightFlag = true;
        public AscensionRunnable(Player p) {
            this.p = p;
            p.setAllowFlight(true);
            p.setFlying(true);
            p.setFlySpeed(0.1f);

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
                p.setFlySpeed(0.1f);
                oldFlag = false;
            }
            if(!oldFlag && flag1) {
                p.setFlySpeed(0.1f);
                oldFlag = true;
            }
            //TODO remove the bit above :P
            p.setFlySpeed(0.1f);
            ////RENDER FX////
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0.05f,0.05f, 0.05f, 0, 500, Material.ICE.getId()));
            EffectLibrary.sphereParticles(l, EnumParticle.FALLING_DUST, Material.ICE.getId());
        }

    }
}
