package com.olympiarpg.orpg.main;

import com.google.common.io.Files;
import com.olympiarpg.orpg.util.PClass;
import com.olympiarpg.orpg.util.Party;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SPlayer implements Listener {

    public static final SimpleDateFormat FORMAT_OUTPUT = new SimpleDateFormat("yyyy.MM.dd@HH:mm:ss");

    public final UUID uuid;
    private int level;
    private int xp;
    private PClass pClass;
    private HashMap<String, Date> cooldowns;
    private List<String> abilities;
    private Party party;
    private int souls;

    public SPlayer(UUID uuid) {
        this.uuid = uuid;
        level = 1;
        xp = 0;
        pClass = PClass.NONE;
        abilities = new ArrayList<String>();
        cooldowns = new HashMap<String, Date>();
        load();
        updateStats();
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    public void updateStats() {
        Player p = Bukkit.getPlayer(uuid);
        if (p != null) {
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(pClass.health);
            p.setWalkSpeed(pClass.speed);
            p.setHealthScale(pClass.health / 5);
        }
    }

    public boolean hasAbility(Ability ab) {
        return ab != null && abilities.contains(ab.name);
    }

    public void addAbility(Ability ab) {
        if (ab != null && !abilities.contains(ab.name)) {
            abilities.add(ab.name);
        }
    }

    public void addSouls(int souls) {
        this.souls+=souls;
    }

    public void takeSouls(int souls) {
        this.souls -= souls;
    }

    public int getSouls(int souls) {
        return this.souls;
    }

    private String[] loots = new String[]{"Common", "Rare", "Epic", "Legendary"};

    public void giveXP(int amount) {
        xp += amount;
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.GOLD + "OlympiaRPG" + ChatColor.DARK_PURPLE + "] " + ChatColor.GOLD + "Gained " + ChatColor.DARK_PURPLE + ChatColor.BOLD + amount + ChatColor.GOLD + " XP!");
        if (Bukkit.getPlayer(uuid).hasPermission("olympiarpg.donor")) {
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.GOLD + "OlympiaRPG" + ChatColor.DARK_PURPLE + "] " + ChatColor.GOLD + "+" + ChatColor.DARK_PURPLE + ChatColor.BOLD + (int) Math.ceil(amount * 0.2) + ChatColor.GOLD + " from premium membership!");
            xp += (int) Math.ceil(amount * 0.2);
        }
        if (hasParty()) {
            if (Arrays.asList(OlympiaRPG.INSTANCE.playerManager.getSortedParties()).indexOf(getParty()) < 10) {
                xp += ((int)Math.ceil(amount*0.5));
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.GOLD + "OlympiaRPG" + ChatColor.DARK_PURPLE + "] " + ChatColor.GOLD + "+" + ChatColor.DARK_PURPLE + ChatColor.BOLD + (int) Math.ceil(amount * 0.5) + ChatColor.GOLD + " from being a top clan!");
            }
        }
        if (pClass == PClass.NONE) {
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED + "You must choose a class. Use /class for help.");
            return;
        }
        while (xp >= nextLevelXP()) {
            xp -= nextLevelXP();
            level++;
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.GOLD + "OlympiaRPG" + ChatColor.DARK_PURPLE + "] " + ChatColor.GOLD + "You've reached level " + ChatColor.DARK_PURPLE + ChatColor.BOLD + level + ChatColor.GOLD + ".");
            FireworkEffect effect = FireworkEffect.builder().trail(true).flicker(false).withColor(Color.YELLOW).withColor(Color.PURPLE).withFade(Color.YELLOW).with(FireworkEffect.Type.BALL).build();
            final Firework fw = Bukkit.getPlayer(uuid).getWorld().spawn(Bukkit.getPlayer(uuid).getEyeLocation(), Firework.class);
            FireworkMeta meta = fw.getFireworkMeta();
            meta.addEffect(effect);
            fw.setFireworkMeta(meta);
            if (level % 5 == 0 && level <= 45) {
                int i = 2 + level / 5;
                abilities.add(pClass.abilities[i].ab.name);
            }
            Bukkit.getPlayer(uuid).setHealth(Bukkit.getPlayer(uuid).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "oadmin loot " + Bukkit.getPlayer(uuid).getName() + " Random");
        }
    }

    public void updateXP(int amount) {
        xp += amount;
        if (pClass == PClass.NONE) {
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED + "You must choose a class. Use /class for help.");
            return;
        }
        while (xp >= nextLevelXP()) {
            xp -= nextLevelXP();
            level++;
            if (level % 5 == 0 && level <= 45) {
                int i = 2 + level / 5;
                abilities.add(pClass.abilities[i].ab.name);
                Bukkit.getLogger().info(level + ": " + pClass.abilities[i].ab.name);
            }
            Bukkit.getPlayer(uuid).setHealth(Bukkit.getPlayer(uuid).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }
    }

    private int nextLevelXP() {
        return 100 * (int) Math.round(Math.pow(this.level + 1, 2));
    }

    private void load() {
        YamlConfiguration yc = new YamlConfiguration();
        try {
            yc.load(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/" + uuid.toString() + ".yml");
            boolean updated = yc.getBoolean("updated", false);
            if (!updated) {
                abilities.clear();
                this.pClass = PClass.valueOf(yc.getString("class", "NONE"));
                if (pClass != pClass.NONE) {
                    abilities.add(pClass.abilities[0].ab.name);
                    abilities.add(pClass.abilities[1].ab.name);
                    abilities.add(pClass.abilities[2].ab.name);
                }
                this.level = 1;
                this.xp = 0;
                int level = yc.getInt("level", 1);
                int xp = yc.getInt("xp", 0);
                Bukkit.getLogger().info("Initial: "+xp);
                if (level >= 15) {
                    level = 15;
                    xp = 0;
                }
                for (int i = 2; i <= level; i++) {
                    Bukkit.getLogger().info("Add: "+xp);
                    xp += 100 * (int) Math.pow(i, 3);
                }
                Bukkit.getLogger().info("Total: "+xp);
                updateXP(xp);
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Welcome to the class update, your levels have been converted to the new system and your abilities may have changed. See olympiarpg.com/wiki for the details of your class after the update.");
            } else {
                this.level = yc.getInt("level", 1);
                this.xp = yc.getInt("xp", 0);
                this.pClass = PClass.valueOf(yc.getString("class", "NONE"));
                this.abilities = (List<String>) yc.get("abilities");
                this.party = OlympiaRPG.INSTANCE.playerManager.getParty(yc.getString("party", ""));
                if (party != null) {Bukkit.getLogger().info(party.getName());}
                List<String> replace = new ArrayList<String>();
                for (String ability : abilities) {
                    if (OlympiaRPG.abilityReplacements.keySet().contains(ability)) {
                        replace.add(ability);
                    }
                }
                for (String ability : replace) {
                    abilities.remove(ability);
                    abilities.add(OlympiaRPG.abilityReplacements.get(ability));
                }
                ConfigurationSection cf = yc.getConfigurationSection("cooldowns");
                Set<String> keys = cf.getKeys(false);
                if ((keys != null) && (keys.size() > 0)) {
                    for (String ability : keys) {
                        try {
                            this.cooldowns.put(ability, FORMAT_OUTPUT.parse((String) cf.get(ability)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            //e.printStackTrace();
        }
    }

    public void save(boolean backup) {
        YamlConfiguration yc = new YamlConfiguration();
        yc.set("level", this.level);
        yc.set("xp", this.xp);
        yc.set("class", this.pClass.toString());
        yc.set("abilities", this.abilities);
        yc.set("updated", true);
        if (party != null) {
            yc.set("party", party.getName());
            Bukkit.getLogger().info(party.getName());
        }
        ConfigurationSection cf = yc.createSection("cooldowns");
        for (String s : cooldowns.keySet()) {
            cf.set(s, FORMAT_OUTPUT.format(cooldowns.get(s)));
        }
        String append = (backup ? "-backup" : "");
        try {
            yc.save(new File(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/" + uuid.toString() + append + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateScoreboard() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("name", "nothing");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.DARK_PURPLE + "   [" + ChatColor.GOLD + Bukkit.getPlayer(uuid).getName() + ChatColor.DARK_PURPLE + "]   ");
        obj.getScore(ChatColor.DARK_PURPLE + "XP: " + ChatColor.LIGHT_PURPLE + this.xp + ChatColor.DARK_PURPLE + "/" + ChatColor.LIGHT_PURPLE + this.nextLevelXP()).setScore(4);
        obj.getScore(ChatColor.DARK_PURPLE + "Lvl: " + ChatColor.GOLD + this.level).setScore(3);
        obj.getScore(ChatColor.DARK_PURPLE + "Class: " + ChatColor.GOLD + WordUtils.capitalize(pClass.toString().toLowerCase())).setScore(2);
        obj.getScore(ChatColor.DARK_PURPLE + "HP: " + ChatColor.RED + (int) Bukkit.getPlayer(uuid).getHealth() + ChatColor.DARK_RED + "/" + ChatColor.RED + (int) Bukkit.getPlayer(uuid).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()).setScore(1);
        obj.getScore(ChatColor.DARK_PURPLE + "Souls: " + ChatColor.GOLD + this.souls).setScore(0);
        if (hasParty()) {
            obj.getScore(ChatColor.DARK_GREEN + "Party: " + ChatColor.GREEN + party.getName()).setScore(-1);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            Team team = board.registerNewTeam(p.getName());
            SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(p.getUniqueId());
            if ((hasParty() && getParty().getPartyMembers().contains(p.getUniqueId())) || p.getUniqueId().equals(uuid)) {
                double hpRatio = p.getHealth() / p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                if (hpRatio < 0.25) {
                    team.setPrefix(ChatColor.DARK_RED + "" + sp.level + " ");
                } else if (hpRatio < 0.5) {
                    team.setPrefix(ChatColor.RED + "" + sp.level + " ");
                } else {
                    team.setPrefix(ChatColor.GREEN + "" + sp.level + " ");
                }
            } else {
                team.setPrefix(sp.level + " ");
            }
            team.addPlayer(p);
            team.setSuffix(ChatColor.DARK_PURPLE + " " + WordUtils.capitalize(sp.getPClass().toString().toLowerCase()));
        }
        Bukkit.getPlayer(uuid).setScoreboard(board);
    }

    public void addCooldown(Ability ab) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, ab.cooldown);
        cooldowns.put(ab.name, cal.getTime());
    }

    public boolean hasCooldown(Ability ab) {
        return cooldowns.containsKey(ab.name) && cooldowns.get(ab.name).after(new Date());
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setPClass(PClass pClass) {
        this.pClass = pClass;
    }

    public PClass getPClass() {
        return this.pClass;
    }

    public int getCooldown(Ability ab) {
        return (int) (Math.ceil((cooldowns.get(ab.name).getTime() - System.currentTimeMillis()) / 1000));
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public boolean hasParty() {
        return party != null;
    }

    public void reset() {
        save(true);
        setPClass(PClass.NONE);
        abilities.clear();
        xp = 0;
        level = 1;
        save(false);
    }

    public void restore(File backup) {
        File current = new File(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/" + uuid.toString() + ".yml");
        File temp = new File(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/" + uuid.toString() + "-temp.yml");
        if (temp.exists()) {
            temp.delete();
        }
        try {
            Files.copy(current, temp);
            current.delete();
            Files.copy(backup, current);
            backup.delete();
            Files.copy(temp, backup);
            temp.delete();
        } catch (IOException e) {
        }
        load();
    }
}
