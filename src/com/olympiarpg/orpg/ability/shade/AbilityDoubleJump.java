package com.olympiarpg.orpg.ability.shade;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class AbilityDoubleJump extends PassiveAbility {

    public AbilityDoubleJump() {
        super("Double Jump");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR && !(player.isFlying())) {
            SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(player.getUniqueId());
            if (splayer != null && splayer.hasAbility(this)) {
                int hunger = player.getFoodLevel();
                hunger -= 4;
                if (hunger > 0) {
                    player.setAllowFlight(true);
                    /*new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.setAllowFlight(false);
                        }
                    }.runTaskLater(OlympiaRPG.INSTANCE, 20);*/
                } else {
                    player.setAllowFlight(false);
                }
            }
        }
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
        if (splayer != null && splayer.hasAbility(this)) {
            if (p.getGameMode() != GameMode.CREATIVE) {
                int hunger = p.getFoodLevel();
                hunger -= 4;
                if (hunger >= 0) {
                    p.setFoodLevel(hunger);
                    e.setCancelled(true);
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.setVelocity(p.getLocation().getDirection().multiply(1.5).setY(1));
                } else {
                    p.sendMessage("Not enough stamina.");
                    p.setAllowFlight(false);
                }
            }
        }
    }
}
