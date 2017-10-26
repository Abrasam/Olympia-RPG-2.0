package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import com.olympiarpg.orpg.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilitySoulMist extends PassiveAbility {

    private final Ability THIS = this;

    public AbilitySoulMist() {
        super("Soul Mist");
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
                    if (sp != null && sp.hasAbility(THIS)) {
                        for (Entity e : Utils.getNearbyEntities(p.getLocation(), 10, 10, 10)) {
                            if (e instanceof Player) {
                                addPotionEffectIfAlly(new PotionEffect(PotionEffectType.REGENERATION, 20*5, 1), (Player)e, p);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(OlympiaRPG.INSTANCE, 20, 20*5);
    }
}
