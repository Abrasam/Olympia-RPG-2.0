package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.util.PClass;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public abstract class Gun extends CooldownItem {

    public Gun(PClass pClass) {
        super(pClass);
    }

    @Override
    protected void action(Action action, Player player, ItemStack item) {
        if (!hasCooldown(player)) {
            cooldownAction(action, player, item);
            addCooldown(player, 1500);
        }
    }
}
