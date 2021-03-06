package mjaroslav.mcmods.mjutils.util;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static mjaroslav.mcmods.mjutils.util.UtilsTime.getTicksFromSmelting;

/**
 * A set of utilities to quickly add new fuel to the furnace.
 */
public class UtilsFuel {
    private static final Map<ItemStack, Integer> fuelMap = new HashMap<>();

    public static Map<ItemStack, Integer> getFuelMap() {
        return fuelMap;
    }

    /**
     * Add burning time to ItemStack in ticks.
     *
     * @param itemStack fuel ItemStack.
     * @param burnTime  ticks of burning.
     */
    public static void addFuel(ItemStack itemStack, int burnTime) {
        fuelMap.put(itemStack, burnTime);
    }

    /**
     * Add burning time to the ItemStack in the
     * number of items that will be processed in
     * the furnace.
     *
     * @param itemStack fuel ItemStack.
     * @param burnCount number of items that will
     *                  be processed in the furnace.
     *                  1F = 1 item = 200 ticks.
     */
    public static void addFuel(ItemStack itemStack, float burnCount) {
        fuelMap.put(itemStack, getTicksFromSmelting(burnCount));
    }
}
