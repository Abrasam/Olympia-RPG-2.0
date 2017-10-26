package com.olympiarpg.orpg.main;

import com.olympiarpg.orpg.util.Party;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class PlayerManager implements Listener {

    private final Map<UUID, SPlayer> splayers = new HashMap<UUID, SPlayer>();
    public final Map<String, Party> parties = new HashMap<String, Party>();
    public final Map<UUID, Integer> pvpKills = new HashMap<UUID, Integer>();
    public final Map<UUID, Integer> pveKills = new HashMap<UUID, Integer>();
    public final Map<UUID, Integer> pvpDeaths = new HashMap<UUID, Integer>();
    public final Map<UUID, Integer> pveDeaths = new HashMap<UUID, Integer>();

    public PlayerManager() {
        for (File p : new File(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/party").listFiles()) {
            String name = p.getName().substring(0, p.getName().length() - 4);
            Bukkit.getLogger().info(name);
            parties.put(name, new Party(name));
            Bukkit.getLogger().info(Arrays.toString(parties.get(name).getPartyMembers().toArray()));
        }
        YamlConfiguration yc = new YamlConfiguration();
        try {
            yc.load(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/kills.yml");

            ConfigurationSection pk = yc.getConfigurationSection("pk");
            for (String key : pk.getKeys(false)) {
                pvpKills.put(UUID.fromString(key), pk.getInt(key));
            }
            ConfigurationSection pd = yc.getConfigurationSection("pd");
            for (String key : pd.getKeys(false)) {
                pvpDeaths.put(UUID.fromString(key), pd.getInt(key));
            }
            ConfigurationSection ek = yc.getConfigurationSection("ek");
            for (String key : ek.getKeys(false)) {
                pveKills.put(UUID.fromString(key), ek.getInt(key));
            }
            ConfigurationSection ed = yc.getConfigurationSection("ed");
            for (String key : ed.getKeys(false)) {
                pveDeaths.put(UUID.fromString(key), ed.getInt(key));
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SPlayer getSPlayer(UUID uuid) {
        if (splayers.containsKey(uuid)) {
            return splayers.get(uuid);
        } else {
            return null;
        }
    }

    public Party getParty(String name) {
        Bukkit.getLogger().severe(name);
        Bukkit.getLogger().severe("" + (parties.get(name) == null));
        return parties.get(name);
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent e) {
        handlePlayer(e.getPlayer());
    }

    protected void handlePlayer(Player player) {
        splayers.put(player.getUniqueId(), new SPlayer(player.getUniqueId()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        List<UUID> remove = new ArrayList<UUID>();
        for (UUID uuid : splayers.keySet()) {
            if (e.getPlayer().getUniqueId().equals(uuid)) {
                splayers.get(uuid).save(false);
                /*if (splayers.get(uuid).hasParty()) {
                    splayers.get(uuid).getParty().removePlayer(e.getPlayer());
                }*/
                remove.add(uuid);
            }
        }
        for (UUID u : remove) {
            splayers.remove(u);
        }
    }

    public void save() {
        for (SPlayer sp : splayers.values()) {
            sp.save(false);
        }
        YamlConfiguration yc = new YamlConfiguration();
        ConfigurationSection pk = yc.createSection("pk");
        for (UUID p : pvpKills.keySet()) {
            pk.set(p.toString(), pvpKills.get(p));
        }
        ConfigurationSection pd = yc.createSection("pd");
        for (UUID p : pvpDeaths.keySet()) {
            pd.set(p.toString(), pvpDeaths.get(p));
        }
        ConfigurationSection ek = yc.createSection("ek");
        for (UUID p : pveKills.keySet()) {
            ek.set(p.toString(), pveKills.get(p));
        }
        ConfigurationSection ed = yc.createSection("ed");
        for (UUID p : pveDeaths.keySet()) {
            ed.set(p.toString(), pveDeaths.get(p));
        }
        try {
            yc.save(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/kills.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Party p : parties.values()) {
            p.save();
        }
    }

    public void bumpKills(UUID p, boolean pvp) {
        Bukkit.getLogger().info("Kill " + pvp);
        if (pvp) {
            pvpKills.put(p, pvpKills.get(p) == null ? 1 : pvpKills.get(p) + 1);
        } else {
            pveKills.put(p, pveKills.get(p) == null ? 1 : pveKills.get(p) + 1);
        }
    }

    public void bumpDeaths(UUID p, boolean pvp) {
        Bukkit.getLogger().info("Death " + pvp);
        if (pvp) {
            pvpDeaths.put(p, pvpDeaths.get(p) == null ? 1 : pvpDeaths.get(p) + 1);
        } else {
            pveDeaths.put(p, pveDeaths.get(p) == null ? 1 : pveDeaths.get(p) + 1);
        }
    }

    public void update() {
        for (SPlayer sp : splayers.values()) {
            try {
                sp.updateStats();
                sp.updateScoreboard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Party addParty(String name, Player starter) {
        Party p = new Party(name, starter);
        parties.put(name, p);
        return p;
    }

    public void removeParty(String name) {
        Bukkit.getLogger().info(name);
        File party = new File(OlympiaRPG.INSTANCE.getDataFolder() + "/party/" + name + ".yml");
        Bukkit.getLogger().warning("KITTENS1");
        if (party.exists()) {
            Bukkit.getLogger().warning("KITTENS2");
            party.delete();
        }
        Bukkit.getLogger().warning(parties.toString());
        Bukkit.getLogger().warning(parties.size() + "");
        parties.remove(name);
        Bukkit.getLogger().warning(parties.toString());
        Bukkit.getLogger().warning(parties.size() + "");
    }

    public Party[] getSortedParties() {
        Party[] parties = new Party[this.parties.values().size()];
        int i = 0;
        for (Party p : this.parties.values()) {
            parties[i] = p;
            i++;
        }
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (i = 0; i < parties.length-1; i++) {
                if (parties[i].getScore() < parties[i+1].getScore()) {
                    Party temp = parties[i];
                    parties[i] = parties[i+1];
                    parties[i+1] = temp;
                    swapped = true;
                }
            }
        }
        return parties;
    }
}
