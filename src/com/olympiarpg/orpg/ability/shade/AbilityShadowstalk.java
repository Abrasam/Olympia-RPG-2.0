package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityShadowstalk extends Ability implements InCity {

    public AbilityShadowstalk() {
        super("Shadowstalk", 120);
    }

    @Override
    public boolean action(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60*20, 1));
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.hidePlayer(p);
        }
        new TimedEffect(60) {
            @EventHandler
            public void onQuit(PlayerQuitEvent e) {
                e.getPlayer().showPlayer(p);
            }

            @EventHandler
            public void onJoin(PlayerJoinEvent e) {
                e.getPlayer().hidePlayer(p);
            }

            @EventHandler
            public void onPlayerDamage(EntityDamageByEntityEvent e) {
                if (e.getDamager() == p) {
                    this.run();
                }
            }

            @Override
            protected void end() {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.showPlayer(p);
                }
            }
        };
        return true;
    }
}
