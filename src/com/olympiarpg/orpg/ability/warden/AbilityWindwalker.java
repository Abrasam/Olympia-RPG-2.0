package com.olympiarpg.orpg.ability.warden;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityWindwalker extends PassiveAbility {

    private Ability THIS = this;

    public AbilityWindwalker() {
        super("Windwalker");
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    SPlayer sp  = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
                    if (sp != null && sp.hasAbility(THIS)) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*20, 1));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*20, 2));
                    }
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 20, 20*10);
    }

}
