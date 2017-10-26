package com.olympiarpg.orpg.main;

import com.olympiarpg.orpg.ability.effect.EffectLibrary;
import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.util.*;
import com.olympiarpg.orpg.weapon.*;
import com.palmergames.bukkit.towny.command.NationCommand;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.*;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class OlympiaRPG extends JavaPlugin implements Listener {

    public static OlympiaRPG INSTANCE;
    public static Set<Material> transparent = new HashSet<Material>();
    public static Map<String, String> abilityReplacements = new HashMap<String, String>();
    public PlayerManager playerManager;
    public final Map<UUID, Party> invites = new HashMap<UUID, Party>();
    private CustomItem[] items;
    private CustomItem[] scrolls;
    private KillWatcher kw;
    private MobHealth mobHealth;
    private List<UUID> trail = new ArrayList<UUID>();
    public WorldGuardPlugin wg;
    private Calendar cal = Calendar.getInstance();
    private Random rnd = new Random();
    private ClassInventoryManager cim;
    private List<GenericScroll> abScrolls;

    public void onEnable() {
        INSTANCE = this;
        playerManager = new PlayerManager();
        mobHealth = new MobHealth();
        setTransparent();
        setReplacements();
        getServer().getPluginManager().registerEvents(playerManager, this);
        getServer().getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            public void run() {
                playerManager.update();
            }
        }.runTaskTimer(this, 10, 20);

        new BukkitRunnable() {
            public void run() {
                playerManager.save();
            }
        }.runTaskTimer(this, 10, 30*20);

        //Handle reloads.
        for (Player p : Bukkit.getOnlinePlayers()) {
            playerManager.handlePlayer(p);
        }
        kw = new KillWatcher();
        items = new CustomItem[] {new FireStaff(), new AstralStaff(), new HandGun(), new Excalibur(), new IceStaff(), new Twig(), new Dagger(), new BowOfBurningGold(), new Polearm(), new Shotgun(), new TpScroll(), new RtpScroll()};
        scrolls = new CustomItem[] {new BFScroll(), new LeviScroll(), new TelekinesisScroll(), new DayScroll(), new NightScroll(), new StormScroll(), new CalmScroll(), new GrowScroll(), new DiamondScroll(), new Mem(), new ScrollOfTornado(), new ScrollofWaterNado(), new PlumbusTornadoScroll(), new MudnadoScroll(), new EarthquakeScroll()};
        abScrolls = new ArrayList<GenericScroll>();
        for (Abilities ab: Abilities.values()) {
            if (ab.ab instanceof PassiveAbility) {continue;}
            abScrolls.add(new AbilityScroll(ab.ab));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID uuid : trail) {
                    if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                        EffectLibrary.sphereParticles(Bukkit.getPlayer(uuid).getLocation(), EnumParticle.DRAGON_BREATH, 0);
                    }
                }
            }
        }.runTaskTimer(INSTANCE, 5, 5);
        wg = (WorldGuardPlugin)getServer().getPluginManager().getPlugin("WorldGuard");
        cim = new ClassInventoryManager();
    }

    public void onDisable() {
        playerManager.save();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) {
            e.getPlayer().getInventory().addItem(items[10].r.getResult());
        }
    }

    private void setTransparent() {
        transparent.add(Material.AIR);
        transparent.add(Material.WATER);
        transparent.add(Material.STATIONARY_WATER);
        transparent.add(Material.LONG_GRASS);
        transparent.add(Material.FENCE);
        transparent.add(Material.VINE);
        transparent.add(Material.FENCE_GATE);
        transparent.add(Material.YELLOW_FLOWER);
        transparent.add(Material.RED_ROSE);
        transparent.add(Material.LADDER);
    }

    private void setReplacements() {
        abilityReplacements.put("Blink", "Eviscerate");
    }

    public static void sendParticlePacket(PacketPlayOutWorldParticles p) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(p);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("oadmin") && (sender.isOp() || sender instanceof ConsoleCommandSender)) {
            if (args.length == 2 && args[0].equalsIgnoreCase("xp")) {
                int xp = Integer.parseInt(args[1]);
                SPlayer sp = playerManager.getSPlayer(((Player) sender).getUniqueId());
                sp.giveXP(xp);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("test")){
                cim.openClassInventory((Player)sender);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("wibble")) {
                Location l = new Location(Bukkit.getWorld("world"), 0, 100, 0);
                l.getBlock().setType(Material.TNT);
            } else if (args.length == 3 && args[0].equals("bonus")) {
                int amount = Integer.parseInt(args[2]);
                Player p = Bukkit.getPlayer(args[1]);
                if (p != null) {
                    SPlayer sp = playerManager.getSPlayer(p.getUniqueId());
                    sp.giveXP(amount);
                }
            } else if (args.length == 2 && args[0].equals("scroll")){
                String ability = args[1].replace("_", " ");
                for (CustomItem item : abScrolls) {
                    if (ChatColor.stripColor(item.r.getResult().getItemMeta().getDisplayName()).equals(ability)) {
                        ((Player)sender).getInventory().addItem(item.r.getResult());
                    }
                }
                for (CustomItem item : scrolls) {
                    if (ChatColor.stripColor(item.r.getResult().getItemMeta().getDisplayName()).equals(ability)) {
                        ((Player)sender).getInventory().addItem(item.r.getResult());
                    }
                }
            } else if (args.length == 3 && args[0].equals("loot")) {
                if (args[2].equalsIgnoreCase("Random")) {
                    int rand = rnd.nextInt(100);
                    if (rand < 2) {
                        args[2] = "Legendary";
                    } else if (rand < 12) {
                        args[2] = "Epic";
                    } else if (rand < 50) {
                        args[2] = "Rare";
                    } else {
                        args[2] = "Common";
                    }
                }
                ItemStack item = new ItemStack(Material.GLASS_BOTTLE, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(Arrays.asList(new String[]{ChatColor.RESET + "" + ChatColor.BLACK + args[2]}));
                meta.setDisplayName(ChatColor.GOLD + args[2] + " Loot Bag");
                item.setItemMeta(meta);
                Player p = Bukkit.getPlayer(args[1]);
                if (p != null) {
                    if (p.getInventory().firstEmpty() == -1) {
                        p.getWorld().dropItem(p.getEyeLocation(), item);
                    } else {
                        p.getInventory().addItem(item);
                    }
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("voted")) {
                File f = new File(getDataFolder().getParentFile().getAbsolutePath() + "/VoteLog/votes-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR) + ".yml");
                YamlConfiguration votes = new YamlConfiguration();
                try {
                    if (!f.exists()) {
                        f.getParentFile().mkdirs();
                        f.createNewFile();
                    }
                    votes.load(f);
                    votes.set(args[1], votes.getInt(args[1], 0) + 1);
                    votes.save(f);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidConfigurationException e) {
                }
                sender.sendMessage("Vote logged for " + args[1] + ".");
            } else if (args.length == 1 && args[0].equalsIgnoreCase("bm")) {
                new BlackMarket().openBMPage((Player) sender, 1);
            } else {
                String add = StringUtils.join(args, " ");
                SPlayer sp = playerManager.getSPlayer(((Player) sender).getUniqueId());
                sp.addAbility(Abilities.get(add));
            }
        } else if (cmd.getName().equalsIgnoreCase("ostats")) {
            stats(sender);
        } else if (cmd.getName().equalsIgnoreCase("reset")) {
            SPlayer sp = playerManager.getSPlayer(((Player)sender).getUniqueId());
            sender.sendMessage(ChatColor.RED + "You will lose all skills and XP. Are you sure? Say " + ChatColor.DARK_RED + ChatColor.BOLD + "\"accept\"" + ChatColor.RED + " to continue.");
            new TimedEffect(10) {
                UUID p = ((Player) sender).getUniqueId();
                @EventHandler
                public void onChat(AsyncPlayerChatEvent e) {
                    if (e.getPlayer().getUniqueId().equals(p) && e.getMessage().equalsIgnoreCase("accept")) {
                        e.setCancelled(true);
                        sp.reset();
                        Bukkit.getPlayer(sp.uuid).setAllowFlight(false);
                        sender.sendMessage(ChatColor.RED + "All gone... If you're a premium member, you can revert to your old save with /revert.");
                    }
                }
            };
        } else if (cmd.getName().equalsIgnoreCase("revert")) {
            if (sender.hasPermission("olympiarpg.donor")) {
                File f = new File(OlympiaRPG.INSTANCE.getDataFolder().getAbsoluteFile() + "/" + ((Player) sender).getUniqueId().toString() + "-backup.yml");
                if (f.exists()) {
                    SPlayer sp = playerManager.getSPlayer(((Player) sender).getUniqueId());
                    if (sp != null) {
                        sp.restore(f);
                        Bukkit.getPlayer(sp.uuid).setAllowFlight(false);
                        sender.sendMessage(ChatColor.DARK_PURPLE + "Welcome back!");
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("daily") && sender instanceof Player) {
            YamlConfiguration yc = new YamlConfiguration();
            try {
                yc.load(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/rewards.yml");
            } catch (IOException e) {
            } catch (InvalidConfigurationException e) {
            }
            try {
                String data = yc.getString(((Player)sender).getUniqueId().toString());
                if (data == null || !SPlayer.FORMAT_OUTPUT.parse(data).after(new Date())) {
                    SPlayer sp = playerManager.getSPlayer(((Player) sender).getUniqueId());
                    if (sp.hasParty() && Arrays.asList(playerManager.getSortedParties()).indexOf(sp.getParty()) < 5) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "oadmin loot " + sender.getName() + " Legendary");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "You received a legendary loot bag for being in one of the top 5 clans.");
                    } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "oadmin loot " + sender.getName() + " Random");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "You received a random loot bag. If your clan is one of the top 5 you receive a legendary loot bag every day.");
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.HOUR, 24);
                    yc.set(sp.uuid.toString(), SPlayer.FORMAT_OUTPUT.format(cal.getTime()));
                    yc.save(OlympiaRPG.INSTANCE.getDataFolder().getAbsolutePath() + "/rewards.yml");
                } else {
                    Date date = SPlayer.FORMAT_OUTPUT.parse(data);
                    long diff = Math.abs(date.getTime() - new Date().getTime());
                    long diffHours = diff / (60 * 60 * 1000);
                    sender.sendMessage(ChatColor.RED + "You must wait " + ChatColor.DARK_RED + diffHours + ChatColor.RED + " hours before you can get your next daily reward.");
                }
            } catch (ParseException e) {
            } catch (IOException e) {
            }
        } else if (cmd.getName().equalsIgnoreCase("class")) {
            SPlayer sp = playerManager.getSPlayer(((Player)sender).getUniqueId());
            if (sp != null && sp.getPClass() == PClass.NONE && args.length == 1) {
                PClass pClass = null;
                try {
                    pClass = PClass.valueOf(args[0].toUpperCase());
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatColor.RED + "Invalid class.");
                    return true;
                }
                if (pClass != null || pClass != pClass.NONE) {
                    sp.setPClass(pClass);
                    for (int i = 0; i < 3; i++) {
                        sp.addAbility(pClass.abilities[i].ab);
                    }
                    ((Player)sender).setHealth(((Player)sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                }
            } else if (sp.getPClass() != PClass.NONE && args.length == 1) {
                sender.sendMessage(ChatColor.RED + "You already have a class, you must /reset in order to change.");
            } else {
                cim.openClassInventory((Player)sender);
            }
        } else if (cmd.getName().equalsIgnoreCase("skills")) {
            SPlayer sp = playerManager.getSPlayer(((Player)sender).getUniqueId());
            if (sp != null) {
                Inventory abs = Bukkit.createInventory(null, 36, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Your Skills:");
                for (String ab : sp.getAbilities()) {
                    ItemStack skillItem = new ItemStack(Material.MAGMA_CREAM);
                    ItemMeta skillMeta = skillItem.getItemMeta();
                    skillMeta.setDisplayName(ChatColor.GOLD + ab);
                    skillMeta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Right-click to use."}));
                    skillItem.setItemMeta(skillMeta);
                    abs.addItem(skillItem);
                }
                ((Player)sender).openInventory(abs);
            }
        } else if (cmd.getName().equalsIgnoreCase("party")) {
            if (args.length > 0) {
                SPlayer splayer = playerManager.getSPlayer(((Player)sender).getUniqueId());
                if (splayer == null) {return false;}
                if (args[0].equalsIgnoreCase("create")) {
                    if (args.length == 2) {
                        if (args[1].length() > 10) {
                            sender.sendMessage(ChatColor.RED + "Party name cannot be over 10 characters.");
                            return true;
                        }
                        if (playerManager.getParty(args[1]) != null) {
                            sender.sendMessage(ChatColor.RED + "This party name is already taken.");
                            return true;
                        }
                        if (splayer.hasParty()) {
                            splayer.getParty().removePlayer((Player) sender);
                        }
                        splayer.setParty(playerManager.addParty(args[1], (Player) sender));
                        sender.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Party" + ChatColor.DARK_GREEN + "] " + ChatColor.GREEN + "Created a new party. You have left your old party.");
                    } else {
                        sender.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Party" + ChatColor.DARK_GREEN + "] " + ChatColor.GREEN + "Please specify a party name.");
                    }
                } else if (args[0].equalsIgnoreCase("invite") && args.length == 2) {
                    if (args.length != 2) {
                        sender.sendMessage(ChatColor.DARK_RED + "Please specify a player.");
                        return true;
                    }
                    if (splayer.getParty() == null) {
                        sender.sendMessage(ChatColor.DARK_RED + "You are not in a party.");
                        return true;
                    }
                    String invite = args[1];
                    Player p = Bukkit.getPlayer(invite);
                    if (p == null || !p.isOnline()) {
                        sender.sendMessage(ChatColor.DARK_RED + "They're not online.");
                        return true;
                    }
                    invites.put(p.getUniqueId(), splayer.getParty());
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (invites.containsKey(p.getUniqueId())) {
                                invites.remove(p.getUniqueId());
                            }
                        }

                    }.runTaskLater(OlympiaRPG.INSTANCE, 20*30);
                    sender.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Party" + ChatColor.DARK_GREEN + "] " + ChatColor.GREEN + "Invited " + args[1] + " to the party.");
                    p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Party" + ChatColor.DARK_GREEN + "] " + ChatColor.DARK_GREEN + sender.getName() + ChatColor.GREEN + " has invited you to their party " + ChatColor.DARK_GREEN + splayer.getParty().getName() + ChatColor.GREEN + ". Type " + ChatColor.BOLD + "/party accept" + ChatColor.GREEN + " to join. This invite expires in 30 seconds.");
                } else if (args[0].equalsIgnoreCase("accept")) {
                    if (invites.containsKey(((Player)sender).getUniqueId())) {
                        splayer.setParty(invites.get(((Player)sender).getUniqueId()));
                        splayer.getParty().addPlayer((Player)sender);
                        for (UUID uuid : splayer.getParty().getPartyMembers()) {
                            if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                                Player player = Bukkit.getPlayer(uuid);
                                player.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Party" + ChatColor.DARK_GREEN + "] " + ChatColor.GREEN + sender.getName() + " has joined the party!");
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "No pending invite.");
                    }
                } else if (args[0].equalsIgnoreCase("info")) {
                    if (!splayer.hasParty()) {
                        sender.sendMessage(ChatColor.DARK_RED + "You are not in a party.");
                        return true;
                    }
                    String people = "";
                    for (UUID uuid : splayer.getParty().getPartyMembers()) {
                        people += ChatColor.DARK_GREEN + Bukkit.getOfflinePlayer(uuid).getName() + ChatColor.GREEN + ", ";
                    }
                    people = people.substring(0, people.length() - 2);
                    sender.sendMessage(ChatColor.DARK_GREEN + "----------[" + ChatColor.GREEN + splayer.getParty().getName() + ChatColor.DARK_GREEN + "]----------");
                    sender.sendMessage(ChatColor.GREEN + "Members: " + people);
                    Party par = splayer.getParty();
                    sender.sendMessage(ChatColor.DARK_GREEN + "Clan");
                    sender.sendMessage(ChatColor.DARK_GREEN + " PvP" + ChatColor.DARK_GREEN + " Kills: " + ChatColor.GREEN + par.getPvPKills() + ChatColor.DARK_GREEN + " Deaths: " + ChatColor.GREEN + par.getPvPDeaths() + ChatColor.DARK_GREEN + " K/D: " + ChatColor.GREEN + (int) (par.getPvPKills() / (double) Math.max(1, par.getPvPDeaths()) * 100) / 100f);
                    sender.sendMessage(ChatColor.DARK_GREEN + " PvE" + ChatColor.DARK_GREEN + " Kills: " + ChatColor.GREEN + par.getPvEKills() + ChatColor.DARK_GREEN + " Deaths: " + ChatColor.GREEN + par.getPvEDeaths() + ChatColor.DARK_GREEN + " K/D: " + ChatColor.GREEN + (int) (par.getPvEKills() / (double) Math.max(1, par.getPvEDeaths()) * 100) / 100f);
                    sender.sendMessage(ChatColor.DARK_GREEN + " Overall" + ChatColor.DARK_GREEN + " Kills: " + ChatColor.GREEN + par.getTotalKills() + ChatColor.DARK_GREEN + " Deaths: " + ChatColor.GREEN + par.getTotalDeaths() + ChatColor.DARK_GREEN + " K/D: " + ChatColor.GREEN + (int) (par.getTotalKills() / (double) Math.max(1, par.getTotalDeaths()) * 100) / 100f);
                    sender.sendMessage(ChatColor.DARK_GREEN + "You");
                    sender.sendMessage(ChatColor.DARK_GREEN + " PvP" + ChatColor.DARK_GREEN + " Kills: " + ChatColor.GREEN + playerManager.pvpKills.getOrDefault(splayer.uuid, 0) + ChatColor.DARK_GREEN + " Deaths: " + ChatColor.GREEN + playerManager.pvpDeaths.getOrDefault(splayer.uuid, 0) + ChatColor.DARK_GREEN + " K/D: " + ChatColor.GREEN + (int) (playerManager.pvpKills.getOrDefault(splayer.uuid, 0) / (double) playerManager.pvpDeaths.getOrDefault(splayer.uuid, 1) * 100) / 100f);
                    sender.sendMessage(ChatColor.DARK_GREEN + " PvE" + ChatColor.DARK_GREEN + " Kills: " + ChatColor.GREEN + playerManager.pveKills.getOrDefault(splayer.uuid, 0) + ChatColor.DARK_GREEN + " Deaths: " + ChatColor.GREEN + playerManager.pveDeaths.getOrDefault(splayer.uuid, 0) + ChatColor.DARK_GREEN + " K/D: " + ChatColor.GREEN + (int) (playerManager.pveKills.getOrDefault(splayer.uuid, 0) / (double) playerManager.pveDeaths.getOrDefault(splayer.uuid, 1) * 100) / 100f);
                    sender.sendMessage(ChatColor.DARK_GREEN + " Overall" + ChatColor.DARK_GREEN + " Kills: " + ChatColor.GREEN + (playerManager.pveKills.getOrDefault(splayer.uuid, 0) + playerManager.pvpKills.getOrDefault(splayer.uuid, 0)) + ChatColor.DARK_GREEN + " Deaths: " + ChatColor.GREEN + (playerManager.pveDeaths.getOrDefault(splayer.uuid, 0) + playerManager.pvpDeaths.getOrDefault(splayer.uuid, 0)) + ChatColor.DARK_GREEN + " K/D: " + ChatColor.GREEN + ((int) ((playerManager.pvpKills.getOrDefault(splayer.uuid, 0) + playerManager.pveKills.getOrDefault(splayer.uuid, 0)) * 100 / (double) Math.max(1,playerManager.pvpDeaths.getOrDefault(splayer.uuid, 0) + playerManager.pveDeaths.getOrDefault(splayer.uuid, 0)))) / 100f);
                } else if (args[0].equalsIgnoreCase("rank") && splayer.hasParty()) {
                    Party[] parties = new Party[playerManager.parties.values().size()];
                    int i = 0;
                    for (Party p : playerManager.parties.values()) {
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
                    sender.sendMessage(""+(1+Arrays.asList(parties).indexOf(splayer.getParty())));
                } else if (args[0].equalsIgnoreCase("top")) {
                    Party[] parties = new Party[playerManager.parties.values().size()];
                    int i = 0;
                    for (Party p : playerManager.parties.values()) {
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
                    for (i = 0; i < Math.min(10,parties.length); i++) {
                        sender.sendMessage("[" + (i+1) + "] " + parties[i].getName());
                    }
                } else if (args[0].equalsIgnoreCase("leave")) {
                    if (splayer.hasParty()) {
                        splayer.getParty().removePlayer((Player)sender);
                        splayer.setParty(null);
                        sender.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Party" + ChatColor.DARK_GREEN + "] " + ChatColor.GREEN + "You dropped the mic and left.");
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You are not in a party.");
                    }
                } else if (args[0].equalsIgnoreCase("chat") && args.length > 1) {
                    if (splayer.hasParty()) {
                        String[] send = new String[args.length - 1];
                        System.arraycopy(args, 1, send, 0, args.length - 1);
                        String msg = Strings.join(send, " ");
                        for (UUID uuid : splayer.getParty().getPartyMembers()) {
                            Bukkit.getPlayer(uuid).sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Party" + ChatColor.DARK_GREEN + "] " + ChatColor.DARK_GREEN + sender.getName() + ChatColor.GREEN + ": " + msg);
                        }
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED + "You are not in a party.");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "Valid subcommands are create, invite, accept, leave and chat.");
            }
        } else if (cmd.getName().equalsIgnoreCase("setnation")) {
            if (args.length == 1) {
                String nation = WordUtils.capitalize(args[0]);
                Resident player = null;
                try {
                    player = TownyUniverse.getDataSource().getResident(sender.getName());
                    if (player.getTown().getMayor().getName().equals(player.getName())) {
                        Nation n = TownyUniverse.getDataSource().getNation(nation);
                        Town t = player.getTown();
                        NationCommand.nationAdd(n, Arrays.asList(new Town[] {t}));
                    }
                } catch (NotRegisteredException e) {
                    sender.sendMessage(ChatColor.DARK_RED + "You must supply a valid nation and be the mayor of a town that is not already in a nation.");
                    return true;
                } catch (AlreadyRegisteredException e) {
                    sender.sendMessage(ChatColor.DARK_RED + "You must supply a valid nation and be the mayor of a town that is not already in a nation.");
                    return true;
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("glow")) {
            if (sender.hasPermission("olympiarpg.donor")) {
                if (((Player)sender).hasPotionEffect(PotionEffectType.GLOWING)) {
                    ((Player)sender).removePotionEffect(PotionEffectType.GLOWING);
                } else {
                    ((Player) sender).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("trail")) {
            if (sender.hasPermission("olympiarpg.donor")) {
                if (trail.contains(((Player)sender).getUniqueId())) {
                    trail.remove(((Player)sender).getUniqueId());
                } else {
                    trail.add(((Player)sender).getUniqueId());
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("mount")) {
            /*Location l = ((Player)sender).getLocation();
            Horse e = (Horse) l.getWorld().spawnEntity(l, EntityType.HORSE);
            e.setAdult();
            e.setTamed(true);
            e.setOwner((AnimalTamer)sender);
            e.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
            e.setPassenger((Entity) sender);
            e.setCustomName("temphorse");
            e.setCustomNameVisible(false);*/
            sender.sendMessage("Horse suspended for being naughty.");
        }
        return true;
    }

    private void stats(CommandSender sender) {
        File folder = getDataFolder();
        String[] playerFiles = folder.list();
        int total = 0;
        int max = 0;
        int levelTotal = 0;
        Map<PClass, Integer> classCount = new HashMap<PClass, Integer>();
        Map<PClass, Integer> maxClass = new HashMap<PClass, Integer>();
        for (String playerFile : playerFiles) {
            if (!playerFile.contains("backup")) {
                YamlConfiguration yc = new YamlConfiguration();
                try {
                    yc.load(new File(folder.getAbsolutePath() + "/" + playerFile));
                    total++;
                    PClass pClass = PClass.valueOf(yc.getString("class"));
                    classCount.put(pClass, (classCount.get(pClass)==null ? 0 : classCount.get(pClass)) + 1);
                    if (yc.getInt("level", 1) > max) {
                        max = yc.getInt("level", 1);
                    }
                    if (maxClass.get(pClass) == null || yc.getInt("level", 1) > maxClass.get(pClass)) {
                        maxClass.put(pClass, yc.getInt("level", 1));
                    }
                    levelTotal += yc.getInt("level", 1);
                } catch (IOException e) {
                } catch (InvalidConfigurationException e) {
                }
            }
        }
        int avgLevel = (int)((double)levelTotal)/total;
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------{" + ChatColor.GOLD + "Stats" + ChatColor.DARK_PURPLE + "}------------------");
        sender.sendMessage(ChatColor.GOLD + "Total players: " + ChatColor.DARK_PURPLE + total);
        sender.sendMessage(ChatColor.GOLD + "Max level: " + ChatColor.DARK_PURPLE + max);
        sender.sendMessage(ChatColor.GOLD + "Average level: " + ChatColor.DARK_PURPLE + avgLevel);
        for (PClass pClass : classCount.keySet()) {
            sender.sendMessage(ChatColor.GOLD + "Total " + pClass.toString().toLowerCase() + " players: " + ChatColor.DARK_PURPLE + classCount.get(pClass) + ChatColor.GOLD + "     Highest level: " + ChatColor.DARK_PURPLE + maxClass.get(pClass));
        }
        sender.sendMessage(ChatColor.DARK_PURPLE + "-------------------------------------------");
    }

    @EventHandler
    public void onDismount(EntityDismountEvent e) {
        if (e.getDismounted().getCustomName() != null && e.getDismounted().getCustomName().equals("temphorse")) {
            e.getDismounted().remove();
        }
    }

    public void damage(LivingEntity p, double dmg, LivingEntity source, boolean ignoreArmor) {
        if (p.isDead() || dmg == 0) {return;}
        TownBlock tb = TownyUniverse.getTownBlock(source.getLocation());
        try {
            if (tb != null && tb.hasTown() && !tb.getTown().isPVP()) {
                return;
            }
        } catch (NotRegisteredException e) {
        }
        SPlayer splayer = playerManager.getSPlayer(source.getUniqueId());
        if (splayer != null && p instanceof Player && splayer.hasParty() && splayer.getParty().inParty((Player)p)) {
            return;
        }
        //dmg = dmg * Math.max(0,(ignoreArmor ? 1 : ((1-p.getAttribute(Attribute.GENERIC_ARMOR).getValue()*4/100f)*(1-getProtection(p))*(1-getResistance(p)))));
        if (ignoreArmor) {
            if (p.getHealth() - dmg < 0) {
                p.setHealth(1);
                p.damage(20, source);
            } else {
                p.setHealth(p.getHealth() - dmg);
                p.damage(1, source);
            }
        } else {
            p.damage(dmg, source);
        }
    }

    private double getResistance(LivingEntity p) {
        return (p.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) == null ? 0 : Math.min(0.8, 0.2*(1+p.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier())));
    }

    public double getProtection(LivingEntity e) {
        double epf = 0;
        for (ItemStack i : e.getEquipment().getArmorContents()) {
            if (i!=null && i.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 0) {
                epf += ((6 + i.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) * i.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL)) * 0.75 / 3);
            }
        }
        epf = Math.min(25, epf);
        epf *= ((new Random().nextInt(100)+1)/100d);
        epf = Math.min(20, epf);
        return epf*4/100d;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getHand()== EquipmentSlot.OFF_HAND) {
            return;
        }
        if (e.getItem() != null && e.getItem().getType() == Material.MAGMA_CREAM && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (e.getItem() != null && e.getItem().getType() == Material.MAGMA_CREAM) {
                SPlayer sp = playerManager.getSPlayer(e.getPlayer().getUniqueId());
                if (sp != null && e.getItem().getItemMeta().hasDisplayName()) {
                    Ability ab = Abilities.get(ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()));
                    if (ab != null && !sp.hasCooldown(ab) && sp.hasAbility(ab)) {
                        TownBlock tb = TownyUniverse.getTownBlock(e.getPlayer().getLocation());
                        try {
                            if (tb != null && tb.hasTown() && !tb.getTown().isPVP()) {
                                e.getPlayer().sendMessage(ChatColor.RED + "You can't use this ability here.");
                                return;
                            }
                        } catch (NotRegisteredException ex) {
                        }
                        if (!wg.canBuild(e.getPlayer(), e.getPlayer().getLocation())) {
                            e.getPlayer().sendMessage(ChatColor.RED + "You can't use this ability here.");
                            return;
                        }
                        if (ab.action(e.getPlayer())) {
                            sp.addCooldown(ab);
                            ItemStack item = e.getItem();
                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    if (sp.getCooldown(ab) > 0) {
                                        item.setAmount(sp.getCooldown(ab)+1);
                                    } else {
                                        item.setAmount(1);
                                        this.cancel();
                                    }
                                }

                            }.runTaskTimer(OlympiaRPG.INSTANCE, 0, 20);
                        }
                    } else {
                        TitleManagerAPI api =(TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
                        api.sendActionbar(e.getPlayer(), ChatColor.DARK_RED + "" + ChatColor.BOLD + "You have a cooldown.");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        SPlayer sp = playerManager.getSPlayer(e.getEntity().getUniqueId());
        boolean pvp = false;
        if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            if (((EntityDamageByEntityEvent) e.getEntity().getLastDamageCause()).getDamager() instanceof Player) {
                pvp = true;
            }
        }
        if (sp != null) {
            playerManager.bumpDeaths(sp.uuid, pvp);
        }
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent e) {
        boolean spawner = false;
        if (e.getEntity().getMetadata("spawner").size() > 0) {
            List<MetadataValue> meta = e.getEntity().getMetadata("spawner");
            for (MetadataValue v : meta) {
                if (v.getOwningPlugin() == this && v.value() instanceof Boolean && (boolean)v.value() == true) {
                    spawner = true;
                }
            }
        }
        if (e.getEntity().getKiller() != null) {
            SPlayer sp = playerManager.getSPlayer(e.getEntity().getKiller().getUniqueId());
            double mod = 1;
            if (e.getEntity() instanceof Monster || e.getEntity() instanceof Slime || e.getEntity() instanceof Ghast || e.getEntity() instanceof Guardian) {
                for (AttributeModifier m : e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
                    if (m.getOperation() == AttributeModifier.Operation.MULTIPLY_SCALAR_1) {
                        mod *= m.getAmount();
                    }
                }
                //mod=10;
                mod*=2;
            }
            mod *= 2;
            if (spawner) {
                mod /= 4;
            }
            sp.giveXP((int)(e.getDroppedExp()*mod));
            if (sp.hasParty()) {
                for (UUID uuid : sp.getParty().getPartyMembers()) {
                    SPlayer splayer = playerManager.getSPlayer(uuid);
                    if (splayer != null && !uuid.equals(sp.uuid) && Bukkit.getPlayer(uuid).getWorld().equals(e.getEntity().getWorld()) && Bukkit.getPlayer(uuid).getLocation().distanceSquared(e.getEntity().getKiller().getLocation()) < 625) {
                        splayer.giveXP((int)(e.getDroppedExp()*mod*0.2));
                    }
                }
            }
            playerManager.bumpKills(sp.uuid, e.getEntity() instanceof Player);
            sp.addSouls(1);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE && e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && e.getCause() != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            e.setDamage(e.getDamage() * 2);
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            //e.setDamage(e.getDamage()*2);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && (((Player)e.getDamager()).getInventory().getItemInMainHand().getType().toString().contains("SWORD") || ((Player)e.getDamager()).getInventory().getItemInMainHand().getType().toString().contains("AXE"))) {
            e.setDamage(e.getDamage());
        }
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            SPlayer sp = playerManager.getSPlayer(e.getDamager().getUniqueId());
            if (sp != null && sp.hasParty() && sp.getParty().inParty((Player)e.getEntity())) {
                e.setCancelled(true);
            }
            if (e.getEntity() instanceof Player && e.getEntity()==e.getDamager()) {
                e.setCancelled(true);
            }
            if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() == e.getEntity()) {
                e.setCancelled(true);
            }
        } else if (e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player && e.getEntity() instanceof Player) {
            SPlayer sp = playerManager.getSPlayer(((Player)((Projectile)e.getDamager()).getShooter()).getUniqueId());
            if (sp != null && sp.hasParty() && sp.getParty().inParty((Player)e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent e) {
        if (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.EATING || e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || e.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC) {
            e.setAmount(e.getAmount()*3);
        }
    }


}
