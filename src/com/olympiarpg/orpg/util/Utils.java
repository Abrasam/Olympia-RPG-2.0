package com.olympiarpg.orpg.util;

import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Entity> getNearbyEntities(Location loc, float x, float y, float z) {
        if(loc.getWorld() == null) {
            return new ArrayList<Entity>();
        }
        net.minecraft.server.v1_12_R1.WorldServer ww = ((CraftWorld)loc.getWorld()).getHandle();
        AxisAlignedBB aabb = new AxisAlignedBB(loc.getX(), loc.getY(), loc.getZ(), loc.getX()+0.5, loc.getY()+0.5, loc.getZ()+0.5);
        net.minecraft.server.v1_12_R1.Entity ne = null;///REMOVE AMBIGUETY
        List<net.minecraft.server.v1_12_R1.Entity> notchEntityList = ww.getEntities(ne, aabb.grow(x, y, z), null);
        List<org.bukkit.entity.Entity> bukkitEntityList = new java.util.ArrayList<org.bukkit.entity.Entity>(notchEntityList.size());

        for (net.minecraft.server.v1_12_R1.Entity e : notchEntityList) {
            bukkitEntityList.add(e.getBukkitEntity());
        }
        return bukkitEntityList;
    }

    public static boolean isPathable(Material material) {
        return
                material == Material.AIR ||
                        material == Material.SAPLING ||
                        material == Material.WATER ||
                        material == Material.STATIONARY_WATER ||
                        material == Material.POWERED_RAIL ||
                        material == Material.DETECTOR_RAIL ||
                        material == Material.LONG_GRASS ||
                        material == Material.DEAD_BUSH ||
                        material == Material.YELLOW_FLOWER ||
                        material == Material.RED_ROSE ||
                        material == Material.BROWN_MUSHROOM ||
                        material == Material.RED_MUSHROOM ||
                        material == Material.TORCH ||
                        material == Material.FIRE ||
                        material == Material.REDSTONE_WIRE ||
                        material == Material.CROPS ||
                        material == Material.SIGN_POST ||
                        material == Material.LADDER ||
                        material == Material.RAILS ||
                        material == Material.WALL_SIGN ||
                        material == Material.LEVER ||
                        material == Material.STONE_PLATE ||
                        material == Material.WOOD_PLATE ||
                        material == Material.REDSTONE_TORCH_OFF ||
                        material == Material.REDSTONE_TORCH_ON ||
                        material == Material.STONE_BUTTON ||
                        material == Material.SNOW ||
                        material == Material.SUGAR_CANE_BLOCK ||
                        material == Material.VINE ||
                        material == Material.WATER_LILY ||
                        material == Material.NETHER_STALK;
    }
}
