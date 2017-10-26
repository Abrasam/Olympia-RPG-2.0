package com.olympiarpg.orpg.main;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PassiveAbility extends Ability implements Listener {
    public PassiveAbility(String name) {
        super(name, 0);
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    @Override
    public boolean action(Player p) {
        return false;
    }
}
