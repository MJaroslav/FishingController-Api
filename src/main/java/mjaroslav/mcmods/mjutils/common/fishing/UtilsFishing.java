package mjaroslav.mcmods.mjutils.common.fishing;

import static cpw.mods.fml.relauncher.ReflectionHelper.*;

import java.util.ArrayList;

import com.google.common.base.Predicates;

import mjaroslav.mcmods.mjutils.lib.FishingConstants;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraftforge.common.FishingHooks;
import net.minecraftforge.common.FishingHooks.FishableCategory;

/**
 * Wrap-around class for FishingHooks. Use for get/add/remove/clear itemStacks
 * and categories from fishing lists.
 *
 * @author MJaroslav
 */
public class UtilsFishing {
    /**
     * Remove item from all categories.
     *
     * @param itemStack
     *            - itemStack that should be removed.
     * @param checkOnEmpty
     *            - add default itemStack if category is empty (empty list
     *            causes crash when fishing).
     */
    public static void removeItemFromAllCategories(ItemStack itemStack, boolean checkOnEmpty) {
        removeItemFromCategory(itemStack, FishableCategory.FISH, checkOnEmpty);
        removeItemFromCategory(itemStack, FishableCategory.JUNK, checkOnEmpty);
        removeItemFromCategory(itemStack, FishableCategory.TREASURE, checkOnEmpty);
    }

    /**
     * Remove item from category.
     *
     * @param itemStack
     *            - itemStack that should be removed.
     * @param category
     *            - category from which itemStack should be deleted.
     * @param checkOnEmpty
     *            - add default itemStack if category is empty (empty list
     *            causes crash).
     */
    public static void removeItemFromCategory(ItemStack itemStack, FishableCategory category, boolean checkOnEmpty) {
        switch (category) {
        case FISH: {
            FishingHooks.removeFish(Predicates.not(new FishableStackEqualTo(itemStack)));
            if (checkOnEmpty && getCategory(category).size() <= 0)
                addItemToCategory(FishingConstants.defaultFish, FishingConstants.rarityFish, category);
        }
            break;
        case JUNK: {
            FishingHooks.removeJunk(Predicates.not(new FishableStackEqualTo(itemStack)));
            if (checkOnEmpty && getCategory(category).size() <= 0)
                addItemToCategory(FishingConstants.defaultJunk, FishingConstants.rarityJunk, category);
        }
            break;
        case TREASURE: {
            FishingHooks.removeTreasure(Predicates.not(new FishableStackEqualTo(itemStack)));
            if (checkOnEmpty && getCategory(category).size() <= 0)
                addItemToCategory(FishingConstants.defaultTreasure, FishingConstants.rarityTreasure, category);
        }
            break;
        }
    }

    /**
     * Add new itemStack to category.
     *
     * @param itemStack
     *            - itemStack that should be added.
     * @param weight
     *            - rarity of itemStack (You can use
     *            {@link FishingConstants#rarityFish},
     *            {@link FishingConstants#rarityJunk} and
     *            {@link FishingConstants#rarityTreasure} for defaults. values)
     * @param category
     *            - category in which the itemStack should be added.
     */
    public static void addItemToCategory(ItemStack itemStack, int weight, FishableCategory category) {
        WeightedRandomFishable fishable = new WeightedRandomFishable(itemStack, weight);
        switch (category) {
        case FISH:
            FishingHooks.addFish(fishable);
            break;
        case JUNK:
            FishingHooks.addJunk(fishable);
            break;
        case TREASURE:
            FishingHooks.addTreasure(fishable);
        }
    }

    /**
     * Get category in list format.
     *
     * @param category
     *            - category to receive.
     * @return Copy of category.
     */
    public static ArrayList<WeightedRandomFishable> getCategory(FishableCategory category) {
        try {
            switch (category) {
            case FISH:
                return (ArrayList<WeightedRandomFishable>) ((ArrayList<WeightedRandomFishable>) getPrivateValue(
                        FishingHooks.class, new FishingHooks(), "fish")).clone();
            case JUNK:
                return (ArrayList<WeightedRandomFishable>) ((ArrayList<WeightedRandomFishable>) getPrivateValue(
                        FishingHooks.class, new FishingHooks(), "junk")).clone();
            case TREASURE:
                return (ArrayList<WeightedRandomFishable>) ((ArrayList<WeightedRandomFishable>) getPrivateValue(
                        FishingHooks.class, new FishingHooks(), "treasure")).clone();
            }
        } catch (Exception e) {
        }
        return new ArrayList<WeightedRandomFishable>();
    }

    /**
     * Remove all itemStacks from category.
     *
     * @param category
     *            - category to be cleared.
     * @param addDefaults
     *            - add default itemStack if category is empty (empty list
     *            causes crash).
     */
    public static void clearCategory(FishableCategory category, boolean addDefaults) {
        for (WeightedRandomFishable fishable : getCategory(category))
            removeItemFromCategory(fishable.field_150711_b, category, false);
        if (addDefaults)
            switch (category) {
            case FISH:
                addItemToCategory(FishingConstants.defaultFish, FishingConstants.rarityFish, category);
                break;
            case JUNK:
                addItemToCategory(FishingConstants.defaultJunk, FishingConstants.rarityJunk, category);
                break;
            case TREASURE:
                addItemToCategory(FishingConstants.defaultTreasure, FishingConstants.rarityTreasure, category);
                break;
            }
    }

    /**
     * Clear all categories.
     *
     * @param addDefaults
     *            - add default itemStack if category is empty (empty list
     *            causes crash).
     */
    public static void clearAllCategories(boolean addDefaults) {
        clearCategory(FishableCategory.FISH, addDefaults);
        clearCategory(FishableCategory.JUNK, addDefaults);
        clearCategory(FishableCategory.TREASURE, addDefaults);
    }
}