package com.olympiarpg.orpg.util;

import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class BlackMarket implements Listener {

    List<ItemStack> items = new ArrayList<ItemStack>();

    public BlackMarket() {
        Bukkit.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
        for (int i = 0; i < 100; i++) {
            items.add(new ItemStack(Material.RED_ROSE, 1));
        }
    }

    public void openBMPage(Player p, int page) {
        Inventory inv = Bukkit.createInventory(p, 54, "Black Market - Page " + page);
        for (int i = 0; i < 54-9; i++) {
            if ((page-1)*45+i < items.size()) {
                ItemStack item = items.get((page - 1) * 45 + i);
                inv.setItem(i, item);
            }
        }
        ItemStack next = new ItemStack(Material.GREEN_GLAZED_TERRACOTTA, 1);
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName("Next Page");
        next.setItemMeta(nextMeta);
        ItemStack previous = new ItemStack(Material.RED_GLAZED_TERRACOTTA, 1);
        ItemMeta previousMeta = next.getItemMeta();
        previousMeta.setDisplayName("Previous Page");
        previous.setItemMeta(previousMeta);
        inv.setItem(53, next);
        inv.setItem(45, previous);
        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String title = ChatColor.stripColor(e.getInventory().getName());
        String itemName = null;
        if (e.getCurrentItem().hasItemMeta()) {
            itemName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
        }
        if (title.startsWith("Black Market")) {
            e.setCancelled(true);
            if (itemName.equals("Next Page")) {
                int page = Integer.valueOf(title.replace("Black Market - Page ", "")) + 1;
                openBMPage((Player) e.getWhoClicked(), page);
            } else if (itemName.equals("Previous Page")) {
                int page = Integer.valueOf(title.replace("Black Market - Page ", "")) - 1;
                openBMPage((Player) e.getWhoClicked(), (page<1 ? 1 : page));
            }
        }
    }

}
