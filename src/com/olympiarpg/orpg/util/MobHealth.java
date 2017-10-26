package com.olympiarpg.orpg.util;

import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.Chunk;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MobHealth implements Listener {

    public MobHealth() {
        //REGISTER EVENT HANDLER
        OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Monster || event.getEntity() instanceof Slime || event.getEntity() instanceof Ghast || event.getEntity() instanceof Guardian) {
            double mod = 2;
            if (event.getEntity().getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) {
                event.getEntity().getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(new AttributeModifier("damage", 2, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
            }
            Block b = event.getEntity().getLocation().getBlock();
            switch (b.getBiome()) {
                case FOREST:
                case FOREST_HILLS:
                case BIRCH_FOREST:
                case BIRCH_FOREST_HILLS:
                case MUTATED_FOREST:
                case MUTATED_BIRCH_FOREST_HILLS:
                case MUTATED_BIRCH_FOREST:
                case ROOFED_FOREST:
                case MUTATED_ROOFED_FOREST:
                    mod *= 1.2;
                    break;
                case JUNGLE:
                case JUNGLE_EDGE:
                case JUNGLE_HILLS:
                case MUTATED_JUNGLE:
                case MUTATED_JUNGLE_EDGE:
                    mod *= 1.4;
                    break;
                case COLD_BEACH:
                case TAIGA_COLD_HILLS:
                case TAIGA_HILLS:
                case TAIGA:
                case TAIGA_COLD:
                case REDWOOD_TAIGA_HILLS:
                case REDWOOD_TAIGA:
                case MUTATED_REDWOOD_TAIGA:
                case MUTATED_REDWOOD_TAIGA_HILLS:
                case MUTATED_TAIGA:
                case MUTATED_TAIGA_COLD:
                case FROZEN_OCEAN:
                case FROZEN_RIVER:
                case ICE_MOUNTAINS:
                case ICE_FLATS:
                    mod *= 1.6;
                    break;
                case DESERT:
                case DESERT_HILLS:
                case MUTATED_DESERT:
                    mod *= 1.8;
                    break;
                case HELL:
                case EXTREME_HILLS:
                case EXTREME_HILLS_WITH_TREES:
                case MUTATED_EXTREME_HILLS:
                case MUTATED_EXTREME_HILLS_WITH_TREES:
                case SMALLER_EXTREME_HILLS:
                    mod *= 2;
                    break;
            }
            event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(new AttributeModifier("health", mod, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
            event.getEntity().setHealth(event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        switch (event.getSpawnReason()) {
            case SPAWNER:
            case NETHER_PORTAL:
                int count = 0;
                for (Entity e : event.getEntity().getNearbyEntities(16, 256, 16)) {
                    if (e instanceof Monster) {
                        count++;
                    }
                }
                if (count > 10) {
                    event.setCancelled(true);
                    return;
                }
            case SPAWNER_EGG:
                entity.setMetadata("spawner", new FixedMetadataValue(OlympiaRPG.INSTANCE, true));

                Entity passenger = entity.getPassenger();

                if (passenger != null) {
                    passenger.setMetadata("spawner", new FixedMetadataValue(OlympiaRPG.INSTANCE, true));
                }
                return;
            case REINFORCEMENTS:
                event.setCancelled(true);
                return;
        }
    }
}
