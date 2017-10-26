package com.olympiarpg.orpg.main;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Random;

public abstract class Ability {

    public final String name;
    public final int cooldown;

    public static final Random rnd = new Random();

    public Ability(String name, int cooldown) {
        this.name = name;
        this.cooldown = cooldown;
    }

    public abstract boolean action(Player p);

    public static void heal(LivingEntity e, double hp, Player p) {
        SPlayer healer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
        if (e == p || (e instanceof Player && healer != null && healer.hasParty() && healer.getParty().inParty((Player)e)))
        if (e.getHealth() + hp < e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
            e.setHealth(e.getHealth() + hp);
        } else {
            e.setHealth(e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }
    }

    protected boolean addPotionEffectIfAlly(PotionEffect pe, LivingEntity e, Player p) {
        SPlayer attacker = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
        if (e==p || (e instanceof Player && attacker.hasParty() && attacker.getParty().inParty((Player)e))) {
            e.addPotionEffect(pe, true);
            return true;
        }
        return false;
    }

    protected boolean addPotionEffectIfNotAlly(PotionEffect pe, LivingEntity e, Player p) {
        SPlayer attacker = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
        if (e != p && !(e instanceof Player && attacker.hasParty() && attacker.getParty().inParty((Player)e))) {
            e.addPotionEffect(pe, true);
            return true;
        }
        return false;
    }
}