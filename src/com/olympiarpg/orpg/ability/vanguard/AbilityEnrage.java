package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.ability.effect.LingeringParticlesEffect;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AbilityEnrage extends Ability {

    private Ability THIS = this;

    public AbilityEnrage() {
        super("Rampage", 45);
    }

    @Override
    public boolean action(Player p) {
        new LingeringParticlesEffect(EnumParticle.VILLAGER_ANGRY, p.getUniqueId(), p, 5, 0, 10, 0).runTaskTimer(OlympiaRPG.INSTANCE, 1, 1);
        new TimedEffect(10) {
            @EventHandler
            public void onEntityDamage(EntityDamageByEntityEvent e) {
                if (e.getDamager() instanceof Player && isMelee(((Player) e.getDamager()).getInventory().getItemInMainHand().getType())) {
                    SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getDamager().getUniqueId());
                    if (sp.hasAbility(THIS)) {
                        e.setDamage(e.getDamage()*2);
                    }
                }
            }

            private boolean isMelee(Material type) {
                return type.toString().contains("_SWORD") || type.toString().contains("_AXE");
            }
        };
        return true;
    }
}
