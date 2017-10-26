package com.olympiarpg.orpg.ability.vanguard;

import com.olympiarpg.orpg.ability.InCity;
import com.olympiarpg.orpg.ability.effect.AOEParticlesEffect;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import com.olympiarpg.orpg.util.Utils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AbilityBattleStandard extends Ability implements InCity {

    public AbilityBattleStandard() {
        super("Battle Standard", 30);
    }

    @Override
    public boolean action(Player p) {
        new AOEParticlesEffect(EnumParticle.DAMAGE_INDICATOR, p.getLocation(), p.getUniqueId(), 5*10, 5, 0, 0) {
            @Override
            public void run() {
                super.run();
                for (Entity e : Utils.getNearbyEntities(this.l, this.range, 2, this.range)) {
                    if (e instanceof Player) {
                        SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
                        if (e==p || (e instanceof Player && sp.hasParty() && sp.getParty().inParty((Player)e))) {
                            heal((LivingEntity)e, 8/10d, p);
                        }
                    }
                }
            }
        };
        return true;
    }
}
