package com.olympiarpg.orpg.util;

import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Party {

    private List<UUID> players = new ArrayList<UUID>();
    private final String name;


    public Party(String name, Player starter) {
        addPlayer(starter);
        this.name = name;
        load();
    }

    public Party(String name) {
        this.name = name;
        load();
    }

    public void addPlayer(Player p) {
        if (!players.contains(p.getUniqueId())) {
            players.add(p.getUniqueId());
        }
    }

    public void removePlayer(Player p) {
        while (players.contains(p.getUniqueId())) {
            players.remove(p.getUniqueId());
            for (UUID uuid : players) {
                if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                    Bukkit.getPlayer(uuid).sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Party" + ChatColor.DARK_GREEN + "] " + ChatColor.GREEN + p.getName() + " has left the party.");
                }
            }
            if (players.size() == 0) {
                OlympiaRPG.INSTANCE.playerManager.removeParty(this.name);
            }
        }
    }

    public boolean inParty(Player p) {
        return players.contains(p.getUniqueId());
    }

    public List<UUID> getPartyMembers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public int getTotalKills() {
        return getPvPKills() + getPvEKills();
    }

    public int getTotalDeaths() {
        return getPvPDeaths() + getPvEDeaths();
    }

    public int getPvPKills() {
        int total = 0;
        for (UUID u : players) {
            total += OlympiaRPG.INSTANCE.playerManager.pvpKills.getOrDefault(u, 0);
        }
        return total;
    }
    public int getPvEKills() {
        int total = 0;
        for (UUID u : players) {
            total += OlympiaRPG.INSTANCE.playerManager.pveKills.getOrDefault(u, 0);
        }
        return total;
    }

    public int getPvPDeaths() {
        int total = 0;
        for (UUID u : players) {
            total += OlympiaRPG.INSTANCE.playerManager.pvpDeaths.getOrDefault(u, 0);
        }
        return total;
    }
    public int getPvEDeaths() {
        int total = 0;
        for (UUID u : players) {
            total += OlympiaRPG.INSTANCE.playerManager.pveDeaths.getOrDefault(u, 0);
        }
        return total;
    }

    public void save() {
        YamlConfiguration yc = new YamlConfiguration();
        yc.set("name", name);
        List<String> players = new ArrayList<String>();
        for (UUID p : this.players) {
            players.add(p.toString());
        }
        yc.set("players", players);
        try {
            yc.save(new File(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/party/" + name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if (new File(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/party/" + name + ".yml").exists()) {
            YamlConfiguration yc = new YamlConfiguration();
            try {
                yc.load(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/party/" + name + ".yml");
                List<String> players = (List<String>)yc.get("players");
                for (String p : players) {
                    this.players.add(UUID.fromString(p));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public float getScore() {
        return ((int)(100*getPvPKills()/(double)Math.max(1,getPvPDeaths())));
    }
}
