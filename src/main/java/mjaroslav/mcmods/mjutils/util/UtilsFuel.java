package mjaroslav.mcmods.mjutils.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static mjaroslav.mcmods.mjutils.util.UtilsSmelting.getSmeltingCountFromTicks;
import static mjaroslav.mcmods.mjutils.util.UtilsSmelting.getTicksFromSmelting;

/**
 * Wrapper for fuel handler, here you can add your fuel and get the time of its
 * burning.
 *
 * @author MJaroslav
 */
public class UtilsFuel {
    private static final Map<ItemStack, Integer> fuelMap = new HashMap<>();

    /**
     * Get map of fuels and burn ticks.
     *
     * @return Map of fuels and time of its burning.
     */
    public static Map<ItemStack, Integer> getFuelMap() {
        return fuelMap;
    }

    /**
     * Add new fuel.
     *
     * @param itemStack - fuel item.
     * @param burnTime  - ticks of burn.
     */
    public static void addFuel(ItemStack itemStack, int burnTime) {
        fuelMap.put(itemStack, burnTime);
    }

    /**
     * Add new fuel.
     *
     * @param itemStack - fuel item.
     * @param burnCount - count of items to burn (1F = 200 ticks).
     */
    public static void addFuel(ItemStack itemStack, float burnCount) {
        fuelMap.put(itemStack, getTicksFromSmelting(burnCount));
    }

    /**
     * Get the possible amount of fried items from the fuel.
     *
     * @param fuel - fuel ItemStack.
     * @return The burning time of fuel in items (1F = 200 ticks).
     */
    public static float getBurnCount(ItemStack fuel) {
        return getSmeltingCountFromTicks(GameRegistry.getFuelValue(fuel));
    }
}
