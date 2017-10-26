package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.ability.effect.BleedEffect;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityCrippleShot extends Ability {

    private Ability THIS = this;

    public AbilityCrippleShot() {
        super("Cripple Shot", 35);
    }

    @Override
    public boolean action(Player p) {
        new TimedEffect(30) {
            @EventHandler
            public void onArrowLand(ProjectileHitEvent e) {
                if (e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity && e.getEntity().getShooter() instanceof Player) {
                    SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(((Player)e.getEntity().getShooter()).getUniqueId());
                    if (sp != null && sp.hasAbility(THIS)) {
                        addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.SLOW, 7*20, 2), (LivingEntity) e.getHitEntity(), p);
                        new BleedEffect(p.getUniqueId(), (LivingEntity)e.getHitEntity(), 3, 7);
                        this.run();
                    }
                }
            }
        };
        return true;
    }
}
