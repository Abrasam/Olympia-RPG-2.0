package com.olympiarpg.orpg.ability.mystic;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.main.Ability;
import com.olympiarpg.orpg.main.OlympiaRPG;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityLick extends Ability {

    public AbilityLick() {
        super("Lick", 35);
    }

    @Override
    public boolean action(Player p) {
        LivingEntity en = EffectLibrary.getTargetedEntity(p, 5);
        if (en != null) {
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.CONFUSION, 20*15, 5), en, p);
            addPotionEffectIfNotAlly(new PotionEffect(PotionEffectType.POISON, 20*5, 1), en, p);
            Location l = en.getEyeLocation();
            OlympiaRPG.sendParticlePacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_CRACK, false, (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0.3f, 0.3f, 0.3f, 0f, 25, 103));
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Nobody to lick...");
        return false;
    }
}
