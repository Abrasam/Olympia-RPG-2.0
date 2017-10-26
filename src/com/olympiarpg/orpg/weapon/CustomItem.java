package com.olympiarpg.orpg.weapon;

import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.SPlayer;
import com.olympiarpg.orpg.util.PClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public abstract class CustomItem implements Listener {

    private PClass pClass;
    public ShapedRecipe r;

    public CustomItem(PClass pClass) {
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
        this.pClass = pClass;
        addRecipe();
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.getRecipe().getResult().getItemMeta().equals(r.getResult().getItemMeta())) {
            SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getWhoClicked().getUniqueId());
            if (splayer != null && splayer.getPClass() != pClass && pClass != null) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAttack(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().equals((r.getResult().getItemMeta()))) {
            SPlayer splayer = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getPlayer().getUniqueId());
            if (splayer != null && (splayer.getPClass() == pClass || pClass == null)) {
                action(e.getAction(), e.getPlayer(), e.getItem());
            }
        }
    }

    protected abstract void addRecipe();

    protected abstract void action(Action action, Player player, ItemStack item);
}
