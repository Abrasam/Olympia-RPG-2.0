package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.util.PClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class CooldownItem extends CustomItem {

    protected Map<UUID, Date> cooldowns = new HashMap<UUID, Date>();

    public CooldownItem(PClass pClass) {
        super(pClass);
    }

    protected boolean hasCooldown(Player p) {
        return cooldowns.containsKey(p.getUniqueId()) && cooldowns.get(p.getUniqueId()).after(new Date());
    }

    protected void addCooldown(Player p, int millis) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MILLISECOND, millis);
        cooldowns.put(p.getUniqueId(), cal.getTime());
    }

    @Override
    protected void action(Action action, Player player, ItemStack item) {
        if (!hasCooldown(player)) {
            cooldownAction(action, player, item);
            addCooldown(player, 200);
        }
    }

    protected abstract void cooldownAction(Action action, Player player, ItemStack item);
}
