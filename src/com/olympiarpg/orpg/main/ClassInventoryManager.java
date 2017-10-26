package com.olympiarpg.orpg.main;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;

public class ClassInventoryManager implements Listener {

    public ClassInventoryManager() {
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    public void openClassInventory(Player p) {
        ItemStack asterite = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta asteriteMeta = asterite.getItemMeta();
        asteriteMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Asterite");
        asteriteMeta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE +                             "The asterite is a long range",
                ChatColor.DARK_PURPLE +                             "caster class who uses the power",
                ChatColor.DARK_PURPLE +                             "purple and astral magic to destroy",
                ChatColor.DARK_PURPLE +                             "their foes in fabulous style.",
                                                                    "",
                ChatColor.DARK_PURPLE +                             "Their initial skills are:",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Aster Blaster:" + ChatColor.GOLD + " a long range",
                ChatColor.GOLD        +                             "purple projectile, damages on impact.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Wormhole:" + ChatColor.GOLD + " a teleportation skill,",
                ChatColor.GOLD        +                             "teleports to your target location.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Stellar Burst:" + ChatColor.GOLD + " an AoE stun which",
                ChatColor.GOLD        +                             "freezes any enemy in range.",
                                                                    //"",
                ChatColor.DARK_PURPLE +                             "Their unlockable skills are:",
                ChatColor.GOLD        +                             "Forcefield, Corporeal Enlightenment,",
                ChatColor.GOLD        +                             "Smite, Astral Shield, Purple Rain,",
                ChatColor.GOLD        +                             "Arcane Aura, Empyreal Sky, Astral",
                ChatColor.GOLD        +                             "Fire and Singularity.",
                                                                    //"",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Unique weapon: ",
                ChatColor.GOLD        +                             "Astral Staff",
                ChatColor.DARK_PURPLE +                             "Read more at:",
                ChatColor.GOLD + "" + ChatColor.UNDERLINE +         "olympiarpg.com/wiki/Asterite"
        ));
        asterite.setItemMeta(asteriteMeta);
        ItemStack engineer = new ItemStack(Material.REDSTONE, 1);
        ItemMeta engineerMeta = asterite.getItemMeta();
        engineerMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Engineer");
        engineerMeta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE +                             "The engineer has many gadgets and",
                ChatColor.DARK_PURPLE +                             "guns to choose from; a tantalising",
                ChatColor.DARK_PURPLE +                             "arsenal of explosives and other",
                ChatColor.DARK_PURPLE +                             "tools with precise and deadly design.",
                                                                    "",
                ChatColor.DARK_PURPLE +                             "Their initial skills are:",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Frag Grenade:" + ChatColor.GOLD + " a medium range",
                ChatColor.GOLD        +                             "explosive projectile, goes boom.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Jet Boost:" + ChatColor.GOLD + " launch yourself up",
                ChatColor.GOLD        +                             "with an explosion, AoE damage on land.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "GPS:" + ChatColor.GOLD + " shows you if anyone's",
                ChatColor.GOLD        +                             "nearby, very helpful when raiding.",
                                                                    //"",
                ChatColor.DARK_PURPLE +                             "Their unlockable skills are:",
                ChatColor.GOLD        +                             "Repair, Mech Suit, Landmine,",
                ChatColor.GOLD        +                             "Self-Destruct, Electrocute,",
                ChatColor.GOLD        +                             "Shotguns, Spring Razor, Stun",
                ChatColor.GOLD        +                             "Grenade and Tesla Coil.",
                                                                    //"",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Unique weapons:",
                ChatColor.GOLD        +                             "Handgun and Shotgun",
                ChatColor.DARK_PURPLE +                             "Read more at:",
                ChatColor.GOLD + "" + ChatColor.UNDERLINE +         "olympiarpg.com/wiki/Engineer"
        ));
        engineer.setItemMeta(engineerMeta);
        ItemStack huntsman = new ItemStack(Material.BOW, 1);
        ItemMeta huntsmanMeta = huntsman.getItemMeta();
        huntsmanMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Huntsman");
        huntsmanMeta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE +                             "The huntsman is master of the wild,",
                ChatColor.DARK_PURPLE +                             "specialising in ranged combat the",
                ChatColor.DARK_PURPLE +                             "huntsman has many long range",
                ChatColor.DARK_PURPLE +                             "combat and mobility skills.",
                "",
                ChatColor.DARK_PURPLE +                             "Their initial skills are:",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Iron Hail:" + ChatColor.GOLD + " launch a cloud of",
                ChatColor.GOLD        +                             "arrows to bring a shower of death.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Scavenge:" + ChatColor.GOLD + " rummage in the bushes",
                ChatColor.GOLD        +                             "to forage for some food.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Cripple Shot:" + ChatColor.GOLD + " the next arrow that",
                ChatColor.GOLD        +                             "you land deals a bleed to your foe.",
                //"",
                ChatColor.DARK_PURPLE +                             "Their unlockable skills are:",
                ChatColor.GOLD        +                             "Grappling Hook, Hunter, Bear",
                ChatColor.GOLD        +                             "Trap, Frozen Arrow, Bite, No",
                ChatColor.GOLD        +                             "Drawback, Explosive Arrow,",
                ChatColor.GOLD        +                             "Ender Arrow and Arrow Storm.",
                //"",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Unique weapons:",
                ChatColor.GOLD        +                             "Bow of Burning Gold",
                ChatColor.DARK_PURPLE +                             "Read more at:",
                ChatColor.GOLD + "" + ChatColor.UNDERLINE +         "olympiarpg.com/wiki/Huntsman"
        ));
        huntsman.setItemMeta(huntsmanMeta);
        ItemStack mystic = new ItemStack(Material.SAPLING, 1);
        ItemMeta mysticMeta = huntsman.getItemMeta();
        mysticMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Mystic");
        mysticMeta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE +                             "The mystic is our resident healer,",
                ChatColor.DARK_PURPLE +                             "it's pretty pathetic at dealing",
                ChatColor.DARK_PURPLE +                             "damage but has a knack for keeping",
                ChatColor.DARK_PURPLE +                             "itself and it's allies alive.",
                "",
                ChatColor.DARK_PURPLE +                             "Their initial skills are:",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Sporepatch:" + ChatColor.GOLD + " summon some poisonous",
                ChatColor.GOLD        +                             "flora to slowly damage your enemies.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Remedy:" + ChatColor.GOLD + " stop for  asecond to",
                ChatColor.GOLD        +                             "drink a healing brew.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Lick:" + ChatColor.GOLD + " you... lick your enemy,",
                ChatColor.GOLD        +                             "they're disgusted and feel sick now.",
                //"",
                ChatColor.DARK_PURPLE +                             "Their unlockable skills are:",
                ChatColor.GOLD        +                             "Rejuvinating Downpour, Soul Mist,",
                ChatColor.GOLD        +                             "Bite, Shrooms, Entangle, Actual",
                ChatColor.GOLD        +                             "Cannibal, Thorn Bush, Fey Drify",
                ChatColor.GOLD        +                             "and Strength of the Wild.",
                //"",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Unique weapons:",
                ChatColor.GOLD        +                             "Twig",
                ChatColor.DARK_PURPLE +                             "Read more at:",
                ChatColor.GOLD + "" + ChatColor.UNDERLINE +         "olympiarpg.com/wiki/Mystic"
        ));
        mystic.setItemMeta(mysticMeta);
        ItemStack occultist = new ItemStack(Material.SLIME_BALL, 1);
        ItemMeta occultistMeta = occultist.getItemMeta();
        occultistMeta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Occultist");
        occultistMeta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE +                             "The occultist corrupts and poisons",
                ChatColor.DARK_PURPLE +                             "the area around them. This class",
                ChatColor.DARK_PURPLE +                             "has many debuff and damage over",
                ChatColor.DARK_PURPLE +                             "time skills, bleed your enemies dry.",
                "",
                ChatColor.DARK_PURPLE +                             "Their initial skills are:",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Plague:" + ChatColor.GOLD + " incapacitate a target",
                ChatColor.GOLD        +                             "with slowness and blindness.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Death's Grip:" + ChatColor.GOLD + " pull a fleeing",
                ChatColor.GOLD        +                             "target back towards you.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Sickness:" + ChatColor.GOLD + " infect a target with,",
                ChatColor.GOLD        +                             "slowness and poison for a while.",
                //"",
                ChatColor.DARK_PURPLE +                             "Their unlockable skills are:",
                ChatColor.GOLD        +                             "Cripple, Curse, Life Sap, Dark",
                ChatColor.GOLD        +                             "Form, Bone Spear, Rotflesh,",
                ChatColor.GOLD        +                             "Cursed Ground, Toxic Cloud",
                ChatColor.GOLD        +                             "and Pestilence.",
                //"",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Unique weapons:",
                ChatColor.GOLD        +                             "Polearm",
                ChatColor.DARK_PURPLE +                             "Read more at:",
                ChatColor.GOLD + "" + ChatColor.UNDERLINE +         "olympiarpg.com/wiki/Occultist"
        ));
        occultist.setItemMeta(occultistMeta);
        ItemStack shade = new ItemStack(Material.SKULL_ITEM, 1);
        ItemMeta shadeMeta = shade.getItemMeta();
        shade.setDurability((short)1);
        shadeMeta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Shade");
        shadeMeta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE +                             "The shade is the master of stealth,",
                ChatColor.DARK_PURPLE +                             "they can become invisible at will",
                ChatColor.DARK_PURPLE +                             "and have excellent mobility and",
                ChatColor.DARK_PURPLE +                             "instant damage abilities.",
                "",
                ChatColor.DARK_PURPLE +                             "Their initial skills are:",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Black Dart:" + ChatColor.GOLD + " fire a shimmering",
                ChatColor.GOLD        +                             "blade to pierce your foe's skin.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Backflip:" + ChatColor.GOLD + " spring backwards into",
                ChatColor.GOLD        +                             "the air and take no fall damage.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Dodge:" + ChatColor.GOLD + " while active, all attacks",
                ChatColor.GOLD        +                             "against you have a chance to fail.",
                //"",
                ChatColor.DARK_PURPLE +                             "Their unlockable skills are:",
                ChatColor.GOLD        +                             "Eviscerate, Double Jump, Bleed,",
                ChatColor.GOLD        +                             "Shadowstalk, Web, Backstab,",
                ChatColor.GOLD        +                             "Dash, Shadowsphere and",
                ChatColor.GOLD        +                             "Bladewraith.",
                //"",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Unique weapons:",
                ChatColor.GOLD        +                             "Dagger",
                ChatColor.DARK_PURPLE +                             "Read more at:",
                ChatColor.GOLD + "" + ChatColor.UNDERLINE +         "olympiarpg.com/wiki/Shade"
        ));
        shade.setItemMeta(shadeMeta);
        ItemStack vanguard = new ItemStack(Material.SHIELD, 1);
        ItemMeta vanguardMeta = vanguard.getItemMeta();
        vanguardMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Vanguard");
        vanguardMeta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE +                             "The vanguard is the meathead, the",
                ChatColor.DARK_PURPLE +                             "warrior, the brute force of the",
                ChatColor.DARK_PURPLE +                             "team and will indeed batter enemies",
                ChatColor.DARK_PURPLE +                             "skulls in with close range damage.",
                "",
                ChatColor.DARK_PURPLE +                             "Their initial skills are:",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Sunderstrike:" + ChatColor.GOLD + " a short, sharp blow",
                ChatColor.GOLD        +                             "dealing fairly high damage.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Charge:" + ChatColor.GOLD + " propel yourself forward into",
                ChatColor.GOLD        +                             "battle, hurting those in your way.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "War Cry:" + ChatColor.GOLD + " emit a mighty roar which",
                ChatColor.GOLD        +                             "causes opponents to feel weak.",
                //"",
                ChatColor.DARK_PURPLE +                             "Their unlockable skills are:",
                ChatColor.GOLD        +                             "Battle Standard, Melee master,",
                ChatColor.GOLD        +                             "Kick, Juggernaught, Slash,",
                ChatColor.GOLD        +                             "Blood Thirst, Mighty Blow, Enrage",
                ChatColor.GOLD        +                             "and Pain Train.",
                //"",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Unique weapons:",
                ChatColor.GOLD        +                             "Excalibur",
                ChatColor.DARK_PURPLE +                             "Read more at:",
                ChatColor.GOLD + "" + ChatColor.UNDERLINE +         "olympiarpg.com/wiki/Vanguard"
        ));
        vanguard.setItemMeta(vanguardMeta);
        ItemStack warden = new ItemStack(Material.SNOW_BALL, 1);
        ItemMeta wardenMeta = warden.getItemMeta();
        wardenMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Warden");
        wardenMeta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE +                             "The warden is a maneuverable class,",
                ChatColor.DARK_PURPLE +                             "who likes slowing enemies, freezing",
                ChatColor.DARK_PURPLE +                             "enemies, riding the wind, throwing",
                ChatColor.DARK_PURPLE +                             "ice bolts and eating ice cream.",
                "",
                ChatColor.DARK_PURPLE +                             "Their initial skills are:",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Chill Blast:" + ChatColor.GOLD + " a burst of damaging",
                ChatColor.GOLD        +                             "coldness, in a cone.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Ice Wall:" + ChatColor.GOLD + " conjure forth a chilling",
                ChatColor.GOLD        +                             "wall of ice to keep your foes away.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Encase:" + ChatColor.GOLD + " freeze an enemy in place",
                ChatColor.GOLD        +                             "for a short while.",
                //"",
                ChatColor.DARK_PURPLE +                             "Their unlockable skills are:",
                ChatColor.GOLD        +                             "Propulsion, Windwalker, Force Push,",
                ChatColor.GOLD        +                             "Riding the Winds, Snow Cannon,",
                ChatColor.GOLD        +                             "Frozen Armour, Flurry, Ice Bolt",
                ChatColor.GOLD        +                             "and Blizzard.",
                //"",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Unique weapons:",
                ChatColor.GOLD        +                             "Glacial Staff",
                ChatColor.DARK_PURPLE +                             "Read more at:",
                ChatColor.GOLD + "" + ChatColor.UNDERLINE +         "olympiarpg.com/wiki/Warden"
        ));
        warden.setItemMeta(wardenMeta);
        ItemStack warlock = new ItemStack(Material.FIREBALL, 1);
        ItemMeta warlockMeta = warlock.getItemMeta();
        warlockMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Warlock");
        warlockMeta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE +                             "Are you a pyromaniac? Because if",
                ChatColor.DARK_PURPLE +                             "so, the warlock is the class for",
                ChatColor.DARK_PURPLE +                             "you. The warlock enjoys roasting,",
                ChatColor.DARK_PURPLE +                             "mainly people, but sometimes steak.",
                "",
                ChatColor.DARK_PURPLE +                             "Their initial skills are:",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Fireball:" + ChatColor.GOLD + " a burning projectile, does",
                ChatColor.GOLD        +                             "what you'd expect...",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Dragon Wings:" + ChatColor.GOLD + " fire yourself up into",
                ChatColor.GOLD        +                             "the sky with a bust of flames.",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Soot Poff:" + ChatColor.GOLD + " blind an enemy with the",
                ChatColor.GOLD        +                             "sooy skills of a chimney sweep.",
                //"",
                ChatColor.DARK_PURPLE +                             "Their unlockable skills are:",
                ChatColor.GOLD        +                             "Blazing Feet, Nethercore, Dragon's",
                ChatColor.GOLD        +                             "Breath, Burning Trail, Ignition,",
                ChatColor.GOLD        +                             "Phoenix Heart, Fire Storm,",
                ChatColor.GOLD        +                             "Flamethrower and Pyoclasm.",
                //"",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD +       "Unique weapons:",
                ChatColor.GOLD        +                             "Staff of Embers",
                ChatColor.DARK_PURPLE +                             "Read more at:",
                ChatColor.GOLD + "" + ChatColor.UNDERLINE +         "olympiarpg.com/wiki/Warlock"
        ));
        warlock.setItemMeta(warlockMeta);
        Inventory i = Bukkit.createInventory(p, 45, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "CHOOSE YOUR CLASS");
        i.setItem(11-9, asterite);
        i.setItem(13-9, engineer);
        i.setItem(15-9, huntsman);
        i.setItem(29-9, mystic);
        i.setItem(31-9, occultist);
        i.setItem(33-9, shade);
        i.setItem(47-9, vanguard);
        i.setItem(49-9, warden);
        i.setItem(51-9, warlock);
        p.openInventory(i);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (ChatColor.stripColor(e.getInventory().getName()).equals("CHOOSE YOUR CLASS")) {
            e.setCancelled(true);
            if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                String pClass = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                if (e.getWhoClicked() instanceof Player) {
                    e.getWhoClicked().closeInventory();
                    Inventory i = Bukkit.createInventory(e.getWhoClicked(), 9, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "CHOOSE " + pClass.toUpperCase() + "?");
                    ItemStack confirm = new ItemStack(Material.EMERALD, 1);
                    ItemMeta confirmMeta = confirm.getItemMeta();
                    confirmMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "CONFIRM");
                    confirm.setItemMeta(confirmMeta);
                    ItemStack cancel = new ItemStack(Material.BARRIER, 1);
                    ItemMeta cancelMeta = cancel.getItemMeta();
                    cancelMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "CANCEL");
                    cancel.setItemMeta(cancelMeta);
                    i.setItem(3, confirm);
                    i.setItem(5, cancel);
                    e.getWhoClicked().openInventory(i);
                }
            }
        } else if (ChatColor.stripColor(e.getInventory().getName()).contains("CHOOSE ")) {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            if (e.getWhoClicked() instanceof Player) {
                if (e.getCurrentItem().getType() == Material.EMERALD) {
                    ((Player) e.getWhoClicked()).performCommand("class " + ChatColor.stripColor(e.getInventory().getName()).replace("CHOOSE ", "").replace("?", ""));
                } else if (e.getCurrentItem().getType() == Material.BARRIER) {
                    openClassInventory((Player)e.getWhoClicked());
                }
            }
        }
    }
}
