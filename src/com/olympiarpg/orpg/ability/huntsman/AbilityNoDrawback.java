package com.olympiarpg.orpg.ability.huntsman;

import com.olympiarpg.orpg.ability.effect.TimedEffect;
import com.olympiarpg.orpg.main.OlympiaRPG;
import com.olympiarpg.orpg.main.PassiveAbility;
import com.olympiarpg.orpg.main.SPlayer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AbilityNoDrawback extends PassiveAbility {

    public AbilityNoDrawback() {
        super("No Drawback");
    }

    protected Map<UUID, Date> cooldowns = new HashMap<UUID, Date>();

    protected boolean hasCooldown(Player p) {
        return cooldowns.containsKey(p.getUniqueId()) && cooldowns.get(p.getUniqueId()).after(new Date());
    }

    protected void addCooldown(Player p, int millis) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MILLISECOND, millis);
        cooldowns.put(p.getUniqueId(), cal.getTime());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem() != null && e.getItem().getType() == Material.BOW) {
            SPlayer sp = OlympiaRPG.INSTANCE.playerManager.getSPlayer(e.getPlayer().getUniqueId());
            if (sp.hasAbility(this) && !hasCooldown(e.getPlayer())) {
                if (e.getPlayer().getInventory().first(Material.ARROW) != -1) {
                    boolean pickUp = false;
                    if (e.getItem().getEnchantmentLevel(Enchantment.ARROW_INFINITE) != 1) {
                        int index = e.getPlayer().getInventory().first(Material.ARROW);
                        ItemStack i = e.getPlayer().getInventory().getItem(index);
                        if (i.getAmount() == 1) {
                            e.getPlayer().getInventory().setItem(index, null);
                        } else {
                            i.setAmount(i.getAmount() - 1);
                        }
                        pickUp = true;
                    }
                    e.setCancelled(true);
                    Arrow a = e.getPlayer().launchProjectile(Arrow.class);
                    a.setPickupStatus(pickUp ? Arrow.PickupStatus.ALLOWED : Arrow.PickupStatus.DISALLOWED);
                    a.setCritical(rnd.nextBoolean());
                    a.setFallDistance(e.getItem().getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK)*4);
                    a.setFireTicks(e.getItem().getEnchantmentLevel(Enchantment.ARROW_FIRE)*200);
                    new TimedEffect(10) {
                        @EventHandler
                        public void onBowDamage(EntityDamageByEntityEvent ev) {
                            if (ev.getDamager() == a) {
                                double mod = 1;
                                for (int i = 0; i < e.getItem().getEnchantmentLevel(Enchantment.ARROW_DAMAGE); i++) {
                                    mod*=1.25;
                                }
                                ev.setDamage(ev.getDamage()*mod);
                            }
                        }
                    };
                    addCooldown(e.getPlayer(), 500);
                }
            } else if (sp.hasAbility(this) && hasCooldown(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
