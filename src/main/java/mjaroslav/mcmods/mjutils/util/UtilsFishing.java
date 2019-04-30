package mjaroslav.mcmods.mjutils.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import mjaroslav.mcmods.mjutils.hook.HookConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraftforge.common.FishingHooks;
import net.minecraftforge.common.FishingHooks.FishableCategory;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

import static cpw.mods.fml.relauncher.ReflectionHelper.getPrivateValue;

public class UtilsFishing {
    private static final Map<FishableCategory, Set<WeightedRandomFishable>> FISHABLE_MAP = new HashMap<>();

    static {
        if (HookConfig.fishingCache()) {
            for (FishableCategory category : FishableCategory.values())
                FISHABLE_MAP.put(category, new HashSet<>());
            FISHABLE_MAP.get(FishableCategory.FISH).addAll(EntityFishHook.field_146036_f);
            FISHABLE_MAP.get(FishableCategory.JUNK).addAll(EntityFishHook.field_146039_d);
            FISHABLE_MAP.get(FishableCategory.TREASURE).addAll(EntityFishHook.field_146041_e);
        }
    }

    public static void add(WeightedRandomFishable fishable, FishableCategory category) {
        if (HookConfig.fishingCache())
            FISHABLE_MAP.get(category).add(fishable);
        else switch (category) { // Trying use original cache
            case FISH:
                FishingHooks.addFish(fishable);
                break;
            case JUNK:
                FishingHooks.addJunk(fishable);
            case TREASURE:
                FishingHooks.addTreasure(fishable);
        }
    }

    public static void add(ItemStack itemStack, FishableCategory category, int weight, boolean enchantable,
                           float randomDamage) {
        WeightedRandomFishable fishable = new WeightedRandomFishable(itemStack, weight);
        if (enchantable)
            fishable.func_150707_a();
        fishable.func_150709_a(randomDamage);
        add(fishable, category);
    }

    public static void remove(WeightedRandomFishable fishable, FishableCategory category) {
        remove(Predicates.not(new PredicateItemStacksEquals(fishable)), category);
    }

    public static void remove(ItemStack itemStack, FishableCategory category, boolean enchantable,
                              float randomDamage) {
        remove(Predicates.not(new PredicateItemStacksEquals(itemStack, enchantable, randomDamage)), category);
    }

    public static void remove(Block block, FishableCategory category) {
        remove(Predicates.not(new PredicateItemTypesEquals(block)), category);
    }

    public static void remove(Item item, FishableCategory category) {
        remove(Predicates.not(new PredicateItemTypesEquals(item)), category);
    }

    public static void remove(Predicate<WeightedRandomFishable> test, FishableCategory category) {
        if (HookConfig.fishingCache()) {
            Iterator<WeightedRandomFishable> iterator;
            if (category == null)
                for (FishableCategory category1 : FishableCategory.values()) {
                    iterator = FISHABLE_MAP.get(category1).iterator();
                    while (iterator.hasNext())
                        if (!test.apply(iterator.next()))
                            iterator.remove();
                }
            else {
                iterator = FISHABLE_MAP.get(category).iterator();
                while (iterator.hasNext())
                    if (!test.apply(iterator.next()))
                        iterator.remove();
            }
        } else switch (category) { // Trying use original cache
            case FISH:
                FishingHooks.removeFish(test);
                break;
            case JUNK:
                FishingHooks.removeJunk(test);
            case TREASURE:
                FishingHooks.removeTreasure(test);
        }
    }

    public static Set<WeightedRandomFishable> getCategory(FishableCategory category) {
        if (HookConfig.fishingCache())
            return FISHABLE_MAP.get(category);
        else {
            // Trying use original cache
            try {
                switch (category) {
                    case FISH:
                        return new HashSet<>(getPrivateValue(FishingHooks.class, null, "fish"));
                    case JUNK:
                        return new HashSet<>(getPrivateValue(FishingHooks.class, null, "junk"));
                    case TREASURE:
                        return new HashSet<>(getPrivateValue(FishingHooks.class, null, "treasure"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.emptySet();
    }

    public static void clear(FishableCategory category) {
        if (HookConfig.fishingCache())
            getCategory(category).clear();
        else for (WeightedRandomFishable fishable : getCategory(category)) // Trying use original cache
            remove(fishable, category);
    }

    public static void clearAll() {
        for (FishableCategory category : FishableCategory.values())
            clear(category);
    }

    private static class PredicateItemStacksEquals implements Predicate<WeightedRandomFishable> {
        private final ItemStack pattern;
        private final float randomDamage;
        private final boolean enchantable;

        private PredicateItemStacksEquals(WeightedRandomFishable fishable) {
            pattern = fishable.field_150711_b.copy();
            randomDamage = fishable.field_150712_c;
            enchantable = fishable.field_150710_d;
        }

        private PredicateItemStacksEquals(ItemStack pattern, boolean enchantable, float randomDamage) {
            this.pattern = pattern.copy();
            this.randomDamage = randomDamage;
            this.enchantable = enchantable;
        }

        @Override
        public boolean apply(WeightedRandomFishable input) {
            return UtilsInventory.itemStacksEquals(input.field_150711_b, pattern, false, true, true, false) &&
                    input.field_150710_d == enchantable && input.field_150712_c == randomDamage;
        }
    }

    private static class PredicateItemTypesEquals implements Predicate<WeightedRandomFishable> {
        private final ItemStack pattern;

        private PredicateItemTypesEquals(Block block) {
            pattern = new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE);
        }

        private PredicateItemTypesEquals(Item item) {
            pattern = new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
        }

        @Override
        public boolean apply(WeightedRandomFishable input) {
            return UtilsInventory.itemStackTypeEquals(input.field_150711_b, pattern, false);
        }
    }
}
