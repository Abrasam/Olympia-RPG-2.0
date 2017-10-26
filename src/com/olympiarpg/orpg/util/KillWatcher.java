package com.olympiarpg.orpg.util;

import com.olympiarpg.orpg.main.OlympiaRPG;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillWatcher implements Listener {

    TitleManagerAPI api =(TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");

    public KillWatcher() {
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    private Map<UUID, KillCount> kills = new HashMap<UUID, KillCount>();

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            p.playSound(p.getEyeLocation(), Sound.BLOCK_NOTE_BASS, 0.2f, 1);
            if (kills.containsKey(p.getUniqueId()) && kills.get(p.getUniqueId()).isValid() /*&& kills.get(p.getUniqueId()).getCount() < 6*/) {
                kills.get(p.getUniqueId()).bump();
                api.sendTitle(p, getType(kills.get(p.getUniqueId()).getCount()), 0, 20, 0);
            } else {
                kills.put(p.getUniqueId(), new KillCount());
            }
        }
    }

    private String getType(int count) {
        switch (count) {
            case 2:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Double Kill!";
            case 3:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Triple Kill!";
            case 4:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Quadruple Kill!";
            case 5:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Quintuple Kill!";
            case 6:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Sextuple Kill!";
            case 7:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Septuple Kill!";
            case 8:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Octuple Kill!";
            case 9:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Nonuple Kill!";
            case 10:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Decuple Kill!";
            default:
                return ChatColor.DARK_RED + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "Ultra Kill!";
        }
    }

    private class KillCount {
        private int count = 1;
        private long end = System.currentTimeMillis()+2000;

        public boolean isValid() {
            return System.currentTimeMillis() < end;
        }

        public void bump() {
            count++;
            end = System.currentTimeMillis() + 2000;
        }

        public int getCount() {
            return count;
        }
    }
}
